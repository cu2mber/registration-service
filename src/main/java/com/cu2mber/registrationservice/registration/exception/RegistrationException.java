package com.cu2mber.registrationservice.registration.exception;

import com.cu2mber.registrationservice.common.exception.BusinessException;
import lombok.Getter;

@Getter
public class RegistrationException extends BusinessException {

    private final RegistrationErrorCode errorCode;

    public RegistrationException(RegistrationErrorCode errorCode) {
        super(errorCode.getStatus().name(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public RegistrationException(RegistrationErrorCode errorCode, Object... args) {
        super(errorCode.getStatus().name(), errorCode.getMessage(), args);
        this.errorCode = errorCode;
    }
}
