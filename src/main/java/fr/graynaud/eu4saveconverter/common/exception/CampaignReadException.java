package fr.graynaud.eu4saveconverter.common.exception;

public class CampaignReadException extends RuntimeException {

    public CampaignReadException() {
    }

    public CampaignReadException(String message) {
        super(message);
    }

    public CampaignReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampaignReadException(Throwable cause) {
        super(cause);
    }

    public CampaignReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
