package fr.graynaud.eu4saveconverter.common.exception;

public class LoginNotMatchRegexException extends RuntimeException {

    public LoginNotMatchRegexException() {
    }

    public LoginNotMatchRegexException(String message) {
        super(message);
    }

    public LoginNotMatchRegexException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginNotMatchRegexException(Throwable cause) {
        super(cause);
    }

    public LoginNotMatchRegexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
