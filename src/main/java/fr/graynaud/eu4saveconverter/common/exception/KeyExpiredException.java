package fr.graynaud.eu4saveconverter.common.exception;

import org.springframework.security.core.AuthenticationException;

public class KeyExpiredException extends AuthenticationException {

    public KeyExpiredException(String message) {
        super(message);
    }

    public KeyExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
