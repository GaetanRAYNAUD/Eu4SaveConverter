package fr.graynaud.eu4saveconverter.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordForm extends RecaptchaV3Form {

    @JsonProperty(required = true)
    private String password;

    @JsonProperty(required = true)
    private String repeatPassword;

    @JsonProperty(required = true)
    private String key;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
