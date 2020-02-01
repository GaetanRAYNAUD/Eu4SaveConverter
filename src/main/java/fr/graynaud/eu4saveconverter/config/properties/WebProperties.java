package fr.graynaud.eu4saveconverter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
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
}
