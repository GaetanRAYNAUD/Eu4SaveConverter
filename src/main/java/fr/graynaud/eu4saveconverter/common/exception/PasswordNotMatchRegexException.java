package fr.graynaud.eu4saveconverter.common.exception;

public class PasswordNotMatchRegexException extends RuntimeException {

    public PasswordNotMatchRegexException() {
    }

    public PasswordNotMatchRegexException(String message) {
        super(message);
    }

    public PasswordNotMatchRegexException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMatchRegexException(Throwable cause) {
        super(cause);
    }

    public PasswordNotMatchRegexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
