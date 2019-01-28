package com.example.security.authentication;

public class InternalAuthenticationServiceException extends
        AuthenticationServiceException {

    public InternalAuthenticationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalAuthenticationServiceException(String message) {
        super(message);
    }
}
