package fr.graynaud.eu4saveconverter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class WebProperties {

    private String title;

    private String version;

    private String description;

    private List<String> corsOrigin;

    private String actuatorUser;

    private String actuatorPassword;

    private String frontBaseUrl;

    private String publicKey;

    private String privateKey;

    private Integer expirationToken;

    @NestedConfigurationProperty
    private AuthProperties auth;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCorsOrigin() {
        return corsOrigin;
    }

    public void setCorsOrigin(List<String> corsOrigin) {
        this.corsOrigin = corsOrigin;
    }

    public String getActuatorUser() {
        return actuatorUser;
    }

    public void setActuatorUser(String actuatorUser) {
        this.actuatorUser = actuatorUser;
    }

    public String getActuatorPassword() {
        return actuatorPassword;
    }

    public void setActuatorPassword(String actuatorPassword) {
        this.actuatorPassword = actuatorPassword;
    }

    public String getFrontBaseUrl() {
        return frontBaseUrl;
    }

    public void setFrontBaseUrl(String frontBaseUrl) {
        this.frontBaseUrl = frontBaseUrl;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getExpirationToken() {
        return expirationToken;
    }

    public void setExpirationToken(Integer expirationToken) {
        this.expirationToken = expirationToken;
    }

    public AuthProperties getAuth() {
        return auth;
    }

    public void setAuth(AuthProperties auth) {
        this.auth = auth;
    }
}
