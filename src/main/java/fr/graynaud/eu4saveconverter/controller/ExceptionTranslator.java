package fr.graynaud.eu4saveconverter.controller;

import fr.graynaud.eu4saveconverter.common.exception.*;
import fr.graynaud.eu4saveconverter.controller.dto.LinkForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionTranslator.class);

    private static final String AUTH_PREFIX = "[AUTH] - ";

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleRecaptchaV3Exception(RecaptchaV3Exception e) {
        LOGGER.error("[RECAPTCHA] - " + e.getMessage());

        return new ResponseEntity<>(new ErrorObject(ErrorCode.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleBadCredentialsException(BadCredentialsException e) {
        LOGGER.error(AUTH_PREFIX + e.getMessage());

        return new ResponseEntity<>(new ErrorObject(ErrorCode.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<LinkForm> handleResetPasswordException(ResetPasswordException e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(AUTH_PREFIX + e.getMessage());
        }

        return new ResponseEntity<>(new LinkForm(""), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleKeyNotExistsException(KeyNotExistsException e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(AUTH_PREFIX + e.getMessage());
        }

        return new ResponseEntity<>(new ErrorObject(ErrorCode.KEY_NOT_FOUND), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleKeyExpiredException(KeyExpiredException e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(AUTH_PREFIX + e.getMessage());
        }

        return new ResponseEntity<>(new ErrorObject(ErrorCode.KEY_EXPIRED), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handlePasswordsNotMatchException(PasswordsNotMatchException e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(AUTH_PREFIX + "Someone tried to register with passwords that does not matches");
        }

        return new ResponseEntity<>(new ErrorObject(ErrorCode.PASSWORDS_NOT_MATCH), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handlePasswordNotMatchRegexException(PasswordNotMatchRegexException e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(AUTH_PREFIX + "Someone tried to register with a password that does not match the regex");
        }

        return new ResponseEntity<>(new ErrorObject(ErrorCode.PASSWORD_NOT_MATCH_REGEX), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleLoginNotMatchRegexException(LoginNotMatchRegexException e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(AUTH_PREFIX + "Someone tried to register with a login that does not match the regex");
        }

        return new ResponseEntity<>(new ErrorObject(ErrorCode.LOGIN_NOT_MATCH_REGEX), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleLoginAlreadyExistException(LoginAlreadyExistException e) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(AUTH_PREFIX + e.getMessage());
        }

        return new ResponseEntity<>(new ErrorObject(ErrorCode.LOGIN_ALREADY_EXIST), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException e) {
        LOGGER.error(e.getMessage(), e);

        return new ResponseEntity<>(new ErrorObject(ErrorCode.NOT_AUTHENTICATED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handlePathAlreadyExistException(PathAlreadyExistException e) {
        return new ResponseEntity<>(new ErrorObject(ErrorCode.PATH_ALREADY_EXIST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleCampaignReadException(CampaignReadException e) {
        LOGGER.error("An error occurred while read campaign from file: {} !", e.getMessage(), e);

        return new ResponseEntity<>(new ErrorObject(ErrorCode.DEFAULT_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);

        return new ResponseEntity<>(new ErrorObject(ErrorCode.DEFAULT_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
