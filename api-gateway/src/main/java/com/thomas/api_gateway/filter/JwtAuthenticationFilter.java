package com.thomas.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    //rutas que no requieren jwt
    private static final List<String> OPEN_ENDPOINTS = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "actuator/health",
            "v3/api-docs",
            "swagger-ui"
    );

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //si es una ruta abierta, dejo pasarlo sin hacer una validacion
        if(isOpenEndpoint(path)){
            return chain.filter(exchange);
        }

        // verifico que tiene header Authorization
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return onError(exchange, "Header Authorization ausente", HttpStatus.UNAUTHORIZED);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // ell header es Bearer <token>
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Formato de token inválido", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7); // recorta "Bearer "

        try {
            Claims claims = validateToken(token);

            // aqui se extrae userId y roles del JWT
            // y los inyecta como headers internos para los microservicios
            String userId = claims.getSubject();
            String roles  = claims.get("roles", String.class);

            // Mutamos el request para agregar los headers
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Roles", roles)
                    .header("X-Token-Valid", "true")
                    .build();

            // los mcsv leen X-User-Id directamente
            // sin necesidad de validar JWT ellos mismos
            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (ExpiredJwtException e) {
            log.warn("Token expirado para ruta {}: {}", path, e.getMessage());
            return onError(exchange, "Token expirado", HttpStatus.UNAUTHORIZED);

        } catch (SignatureException e) {
            log.warn("Firma inválida en token para ruta {}", path);
            return onError(exchange, "Token inválido", HttpStatus.UNAUTHORIZED);

        } catch (MalformedJwtException e) {
            log.warn("Token malformado para ruta {}", path);
            return onError(exchange, "Token malformado", HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            log.error("Error inesperado validando token", e);
            return onError(exchange, "Error de autenticación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isOpenEndpoint(String path) {
        return OPEN_ENDPOINTS.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
