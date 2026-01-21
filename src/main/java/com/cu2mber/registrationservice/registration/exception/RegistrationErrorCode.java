package com.cu2mber.registrationservice.registration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RegistrationErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 내역입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다.")
    ;

    private HttpStatus status;
    private String message;

}
