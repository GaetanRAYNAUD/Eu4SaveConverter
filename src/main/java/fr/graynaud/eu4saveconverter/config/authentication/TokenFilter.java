package fr.graynaud.eu4saveconverter.config.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.graynaud.eu4saveconverter.controller.ErrorCode;
import fr.graynaud.eu4saveconverter.controller.ErrorObject;
import fr.graynaud.eu4saveconverter.service.SecurityService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ConditionalOnMissingBean(TokenFilter.class)
public class TokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final SecurityService securityService;

    public TokenFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            securityService.authenticateFromRequest(request);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            LOGGER.warn("Someone tried to use an expired jwt token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(new ErrorObject(ErrorCode.NOT_AUTHENTICATED)));
            response.flushBuffer();
        } catch (JwtException e) {
            LOGGER.warn("Someone tried to use an invalid jwt token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(new ErrorObject(ErrorCode.NOT_AUTHENTICATED)));
            response.flushBuffer();
        } catch (IllegalArgumentException | AuthenticationCredentialsNotFoundException e) {
            LOGGER.warn("Someone tried to use an invalid jwt token or auth mode: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(OBJECT_MAPPER.writeValueAsString(new ErrorObject(ErrorCode.NOT_AUTHENTICATED)));
            response.flushBuffer();
        }
    }
}
