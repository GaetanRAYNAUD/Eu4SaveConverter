package fr.graynaud.eu4saveconverter.config.properties;

public class AuthProperties {

    private Integer resetExpiration;

    private String resetPath;

    private String recaptchaKey;

    private String recaptchaVerificationUrl;

    private Double recaptchaThreshold;

    private Boolean activateRecaptcha;

    public String getRecaptchaKey() {
        return recaptchaKey;
    }

    public void setRecaptchaKey(String recaptchaKey) {
        this.recaptchaKey = recaptchaKey;
    }

    public String getRecaptchaVerificationUrl() {
        return recaptchaVerificationUrl;
    }

    public void setRecaptchaVerificationUrl(String recaptchaVerificationUrl) {
        this.recaptchaVerificationUrl = recaptchaVerificationUrl;
    }

    public Double getRecaptchaThreshold() {
        return recaptchaThreshold;
    }

    public void setRecaptchaThreshold(Double recaptchaThreshold) {
        this.recaptchaThreshold = recaptchaThreshold;
    }

    public Integer getResetExpiration() {
        return resetExpiration;
    }

    public void setResetExpiration(Integer resetExpiration) {
        this.resetExpiration = resetExpiration;
    }

    public String getResetPath() {
        return resetPath;
    }

    public void setResetPath(String resetPath) {
        this.resetPath = resetPath;
    }

    public Boolean getActivateRecaptcha() {
        return activateRecaptcha;
    }

    public void setActivateRecaptcha(Boolean activateRecaptcha) {
        this.activateRecaptcha = activateRecaptcha == null ? Boolean.TRUE : activateRecaptcha;
    }

}
