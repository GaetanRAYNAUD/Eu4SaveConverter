package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.model.User;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface SecurityService {

    void authenticateFromRequest(HttpServletRequest request);

    Pair<Long, String> generateTokenForUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException;

    void authenticateFromUser(User user);

    User getCurrentUser();
}
