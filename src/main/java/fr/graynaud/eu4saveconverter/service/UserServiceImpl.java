package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.common.Constants;
import fr.graynaud.eu4saveconverter.common.exception.*;
import fr.graynaud.eu4saveconverter.config.properties.WebProperties;
import fr.graynaud.eu4saveconverter.model.Role;
import fr.graynaud.eu4saveconverter.model.User;
import fr.graynaud.eu4saveconverter.repository.UserRepository;
import fr.graynaud.eu4saveconverter.service.google.RecaptchaV3Action;
import fr.graynaud.eu4saveconverter.service.google.RecaptchaV3Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {

    private static final int LINKS_SIZE = 30;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final String LOGIN_REGEX = "^(?:[a-z0-9!#$%&'*+=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[€@$!%*?&])[A-Za-z\\d€@$!%*?&]{8,}$";

    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final RecaptchaV3Service recaptchaV3Service;

    private final WebProperties webProperties;

    public UserServiceImpl(UserRepository repository, RecaptchaV3Service recaptchaV3Service, WebProperties webProperties) {
        super(repository);
        this.recaptchaV3Service = recaptchaV3Service;
        this.webProperties = webProperties;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return this.repository.getFirstByLogin(login);
    }

    @Override
    public Optional<User> getByResetKey(String resetKey) {
        return this.repository.getFirstByResetKey(resetKey);
    }

    @Override
    public void signUp(String login, String password, String repeatPassword, String token) {
        this.recaptchaV3Service.validateToken(token, login, RecaptchaV3Action.SIGN_UP);

        if (!password.equals(repeatPassword)) {
            throw new PasswordsNotMatchException();
        }

        if (!password.matches(PASSWORD_REGEX)) {
            throw new PasswordNotMatchRegexException();
        }

        if (!login.matches(LOGIN_REGEX)) {
            throw new LoginNotMatchRegexException();
        }

        Optional<User> optionalUser = getByLogin(login);

        if (optionalUser.isPresent()) {
            throw new LoginAlreadyExistException("Someone tried to create an account with already existing login: " + login + " !");
        }

        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(B_CRYPT_PASSWORD_ENCODER.encode(password));

        Role role = new Role();
        role.setUser(user);
        role.setName(Constants.DEFAULT_ROLE);

        user.addRole(role);

        create(user);
    }

    @Override
    public User login(String login, String password, String token) {
        this.recaptchaV3Service.validateToken(token, login, RecaptchaV3Action.LOGIN);

        Optional<User> optionalUser = getByLogin(login);

        if (optionalUser.isEmpty() || StringUtils.isBlank(optionalUser.get().getPasswordHash()) ||
            StringUtils.isBlank(password) ||
            !B_CRYPT_PASSWORD_ENCODER.matches(password, optionalUser.get().getPasswordHash())) {
            throw new BadCredentialsException(
                    "Someone tried to connect with invalid credentials for login: " + login + " !");
        }

        optionalUser.get().setLastConnectionDate(new Date());

        return update(optionalUser.get());
    }

    @Override
    public String generateResetPasswordLink(String login, String token) {
        try {
            this.recaptchaV3Service.validateToken(token, login, RecaptchaV3Action.ASK_RESET_PASSWORD);
        } catch (Exception e) {
            throw new ResetPasswordException(e); //Because here we only respond with 200
        }

        Optional<User> optionalUser = getByLogin(login);

        if (optionalUser.isEmpty()) {
            throw new ResetPasswordException("Someone ask to reset the password for an invalid login: " + login + " !");
        }

        User user = optionalUser.get();

        user.setResetKey(generateRandomKey());
        user.setResetDate(new Date());

        user = update(user);

        return UriComponentsBuilder.fromHttpUrl(this.webProperties.getFrontBaseUrl())
                                   .path(this.webProperties.getAuth().getResetPath())
                                   .queryParam("key", user.getResetKey())
                                   .toUriString();
    }

    @Override
    public void changePassword(String key, String password, String repeatPassword, String token) {
        this.recaptchaV3Service.validateToken(token, key, RecaptchaV3Action.RESET_PASSWORD);

        Optional<User> optionalUser = getByResetKey(key);

        if (optionalUser.isEmpty()) {
            throw new KeyNotExistsException(
                    "Someone tried to reset it's password with the key: " + key + " witch does not exists !");
        }

        User user = optionalUser.get();

        if (user.getResetDate()
                .before(DateUtils.addSeconds(new Date(), -this.webProperties.getAuth().getResetExpiration()))) {
            throw new KeyExpiredException(
                    "Someone tried to reset the password of a user with the key: " + key + " witch expired !");
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsNotMatchException();
        }

        if (!password.matches(PASSWORD_REGEX)) {
            throw new PasswordNotMatchRegexException();
        }

        user.setPasswordHash(B_CRYPT_PASSWORD_ENCODER.encode(password));
        user.setResetDate(null);
        user.setResetKey(null);

        update(user);
    }

    private String generateRandomKey() {
        return RandomStringUtils.random(LINKS_SIZE, 0, 0, true, true, null, SECURE_RANDOM);
    }
}
