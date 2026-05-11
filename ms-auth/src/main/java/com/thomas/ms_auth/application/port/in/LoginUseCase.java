package com.thomas.ms_auth.application.port.in;

import com.thomas.ms_auth.application.command.LoginCommand;
import com.thomas.ms_auth.application.result.AuthResult;

public interface LoginUseCase {
    AuthResult login(LoginCommand command);
}
