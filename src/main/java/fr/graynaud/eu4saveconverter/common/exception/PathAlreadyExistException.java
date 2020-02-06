package fr.graynaud.eu4saveconverter.common.exception;

public class PathAlreadyExistException extends RuntimeException {

    public PathAlreadyExistException() {
    }

    public PathAlreadyExistException(String message) {
        super(message);
    }

    public PathAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public PathAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
