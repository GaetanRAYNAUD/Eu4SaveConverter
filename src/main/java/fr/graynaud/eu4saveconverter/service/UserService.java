package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.model.User;

import java.util.Optional;

public interface UserService extends BaseService<User> {

    Optional<User> getByLogin(String login);

    Optional<User> getByResetKey(String resetKey);

    void signUp(String login, String password, String repeatPassword, String token);

    User login(String login, String password, String token);

    String generateResetPasswordLink(String login, String token);

    void changePassword(String key, String password, String repeatPassword, String token);
}
