package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CampaignCreateForm {

    @JsonProperty(required = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
