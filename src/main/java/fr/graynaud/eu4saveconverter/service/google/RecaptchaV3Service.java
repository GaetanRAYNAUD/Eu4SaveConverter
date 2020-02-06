package fr.graynaud.eu4saveconverter.service.google;

public interface RecaptchaV3Service {
    void validateToken(String token, String login, RecaptchaV3Action action);
}
