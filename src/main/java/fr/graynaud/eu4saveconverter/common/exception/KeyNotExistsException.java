package fr.graynaud.eu4saveconverter.common.exception;

import org.springframework.security.core.AuthenticationException;

public class KeyNotExistsException extends AuthenticationException {

    public KeyNotExistsException(String message) {
        super(message);
    }

    public KeyNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
