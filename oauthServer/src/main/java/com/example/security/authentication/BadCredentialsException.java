package com.example.security.authentication;

import com.example.security.core.AuthenticationException;

public class BadCredentialsException extends AuthenticationException {
    // ~ Constructors
    // ===================================================================================================

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public BadCredentialsException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public BadCredentialsException(String msg, Throwable t) {
        super(msg, t);
    }
}

