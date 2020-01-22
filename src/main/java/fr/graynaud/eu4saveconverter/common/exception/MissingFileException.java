package fr.graynaud.eu4saveconverter.common.exception;

public class MissingFileException extends RuntimeException {

    public MissingFileException() {
    }

    public MissingFileException(String message) {
        super(message);
    }

    public MissingFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingFileException(Throwable cause) {
        super(cause);
    }

    public MissingFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
