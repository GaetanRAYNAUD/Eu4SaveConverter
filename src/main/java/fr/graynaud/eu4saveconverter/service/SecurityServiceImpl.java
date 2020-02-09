package fr.graynaud.eu4saveconverter.service;

import fr.graynaud.eu4saveconverter.config.properties.WebProperties;
import fr.graynaud.eu4saveconverter.model.Role;
import fr.graynaud.eu4saveconverter.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private static final String BEARER = "Bearer";

    private static final String ISSUER = "graynaud.fr";

    private final WebProperties webProperties;

    private final UserService userService;

    private final PublicKey publicKey;

    public SecurityServiceImpl(WebProperties webProperties, UserService userService) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.webProperties = webProperties;
        this.userService = userService;
        this.publicKey = KeyFactory.getInstance("RSA")
                                   .generatePublic(new X509EncodedKeySpec(Base64.getDecoder()
                                                                                .decode(this.webProperties.getPublicKey()
                                                                                                          .getBytes())));
    }

    @Override
    public void authenticateFromRequest(HttpServletRequest request) {
        final Optional<String> token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));

        if (token.isPresent() && token.get().startsWith(BEARER) && token.get().length() > BEARER.length()) {
            authenticateFromToken(getClaims(token.get()));
        } else {
            throw new AuthenticationCredentialsNotFoundException("Missing authorization !");
        }
    }

    @Override
    public Pair<Long, String> generateTokenForUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Date now = new Date();
        Date expirationDate = DateUtils.addMinutes(now, this.webProperties.getExpirationToken());

        byte[] encoded = Base64.getDecoder()
                               .decode(this.webProperties.getPrivateKey().getBytes(StandardCharsets.UTF_8));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) kf.generatePrivate(keySpec);

        JwtBuilder builder = Jwts.builder()
                                 .setSubject(user.getLogin())
                                 .setIssuedAt(now)
                                 .setExpiration(expirationDate)
                                 .setIssuer(ISSUER)
                                 .signWith(rsaPrivateKey, SignatureAlgorithm.RS256);

        return new MutablePair<>(expirationDate.getTime(), builder.compact());
    }

    @Override
    public void authenticateFromUser(User user) {
        SecurityContextHolder.getContext().setAuthentication(getAuthorizationFromUser(user));
    }

    @Override
    public User getCurrentUser() {
        return userService.findOne(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    private Claims getClaims(String bearerToken) {
        if (bearerToken.startsWith(BEARER)) {
            bearerToken = bearerToken.substring(BEARER.length() + 1);
        }

        return Jwts.parser().requireIssuer(ISSUER).setSigningKey(this.publicKey).parseClaimsJws(bearerToken).getBody();
    }

    private void authenticateFromToken(Claims claims) {
        SecurityContextHolder.getContext().setAuthentication(getAuthorizationFromToken(claims));
    }

    private Authentication getAuthorizationFromToken(Claims claims) {
        Optional<User> optionalUser = this.userService.getByLogin(claims.getSubject());

        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException(
                    "Someone tried to connect with valid token for an invalid user: " + claims.getSubject() + " !");
        }

        return getAuthorizationFromUser(optionalUser.get());
    }

    private Authentication getAuthorizationFromUser(User user) {
        return new UsernamePasswordAuthenticationToken(user.getId(), null, user
                .getRoles()
                .stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
    }
}
