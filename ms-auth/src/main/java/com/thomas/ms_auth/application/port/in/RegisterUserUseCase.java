package com.thomas.ms_auth.application.port.in;

import com.thomas.ms_auth.application.command.RegisterUserCommand;
import com.thomas.ms_auth.application.result.RegisterUserResult;

public interface RegisterUserUseCase {
    RegisterUserResult register(RegisterUserCommand command);
}
