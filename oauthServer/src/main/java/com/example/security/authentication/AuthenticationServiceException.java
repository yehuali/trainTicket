package com.example.security.authentication;

import com.example.security.core.AuthenticationException;

public class AuthenticationServiceException extends AuthenticationException {
    // ~ Constructors
    // ===================================================================================================

    /**
     * Constructs an <code>AuthenticationServiceException</code> with the specified
     * message.
     *
     * @param msg the detail message
     */
    public AuthenticationServiceException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>AuthenticationServiceException</code> with the specified
     * message and root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public AuthenticationServiceException(String msg, Throwable t) {
        super(msg, t);
    }
}
