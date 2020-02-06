package fr.graynaud.eu4saveconverter.controller;

public class ErrorObject {
    private ErrorCode error;

    public ErrorObject() {
    }

    public ErrorObject(ErrorCode error) {
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }

    public void setError(ErrorCode error) {
        this.error = error;
    }
}
