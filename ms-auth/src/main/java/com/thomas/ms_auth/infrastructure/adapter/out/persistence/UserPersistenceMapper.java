package com.thomas.ms_auth.infrastructure.adapter.out.persistence;

import com.thomas.ms_auth.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}