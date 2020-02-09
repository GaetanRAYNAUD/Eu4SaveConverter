package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PathForm {

    @JsonProperty(required = true)
    private String path;

    public PathForm(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
