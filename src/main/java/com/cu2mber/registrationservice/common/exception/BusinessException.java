package com.cu2mber.registrationservice.common.exception;

public class BusinessException extends RuntimeException {
    protected String status;
    private final String message;

    public BusinessException(String status, String message){
        this.status = status;
        this.message = message;
    }

    public BusinessException(String status, String message, Object... args) {
        super(formattingErrorMessage(message, args));
        this.status = status;
        this.message = message;
    }

    private static String formattingErrorMessage(String message, Object... objects) {
        return message.formatted(objects);
    }
}
