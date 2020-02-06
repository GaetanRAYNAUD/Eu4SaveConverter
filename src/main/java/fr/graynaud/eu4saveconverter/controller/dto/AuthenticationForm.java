package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationForm extends RecaptchaV3Form {

    @JsonProperty(required = true)
    private String login;

    @JsonProperty(required = true)
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
