package fr.graynaud.eu4saveconverter.controller.authentication;

import fr.graynaud.eu4saveconverter.controller.ErrorObject;
import fr.graynaud.eu4saveconverter.controller.dto.*;
import fr.graynaud.eu4saveconverter.model.User;
import fr.graynaud.eu4saveconverter.service.SecurityService;
import fr.graynaud.eu4saveconverter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(value = "/public/api/authentication")
@Tag(name = "Authentication", description = "Authentication process")
public class PublicAuthenticationController {

    private final SecurityService securityService;

    private final UserService userService;

    public PublicAuthenticationController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @Operation(summary = "Sign up a user with it's new password",
               description = "This call can be protected by a RecaptchaV3 system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The user is successfully created"),
            @ApiResponse(responseCode = "400",
                         description = "The process failed because passwords does not matches one another or the regex",
                         content = @Content(schema = @Schema(implementation = ErrorObject.class))),
            @ApiResponse(responseCode = "401",
                         description = "The process failed, because the RecaptchaV3 is not validated",
                         content = @Content(schema = @Schema(implementation = ErrorObject.class))),
            @ApiResponse(responseCode = "403",
                         description = "The process failed because a user with the same login already exists",
                         content = @Content(schema = @Schema(implementation = ErrorObject.class)))
    })
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenForm> signUp(@RequestBody SignUpForm signUpForm) {
        userService.signUp(signUpForm.getLogin(), signUpForm.getPassword(), signUpForm.getRepeatPassword(),
                           signUpForm.getToken());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Login a user based on it's login/password",
               description = "This call can be protected by a RecaptchaV3 system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user is successfully logged"),
            @ApiResponse(responseCode = "401",
                         description = "The login process failed, because of invalid credentials or RecaptchaV3 not validated",
                         content = @Content(schema = @Schema(implementation = ErrorObject.class)))
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenForm> login(@RequestBody AuthenticationForm authenticationForm) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = userService.login(authenticationForm.getLogin(), authenticationForm.getPassword(),
                                      authenticationForm.getToken());

        return new ResponseEntity<>(new TokenForm(securityService.generateTokenForUser(user)), HttpStatus.OK);
    }

    @Operation(summary = "Reset the password of a user to the newly provided one",
               description = "This call can be protected by a RecaptchaV3 system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The password has been reset"),
            @ApiResponse(responseCode = "400",
                         description = "The process failed because passwords does not matches one another or the regex",
                         content = @Content(schema = @Schema(implementation = ErrorObject.class))),
            @ApiResponse(responseCode = "401",
                         description = "The process failed, because the RecaptchaV3 is not validated",
                         content = @Content(schema = @Schema(implementation = ErrorObject.class))),
            @ApiResponse(responseCode = "403",
                         description = "The process failed because the key does not exists or is expired",
                         content = @Content(schema = @Schema(implementation = ErrorObject.class)))
    })
    @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetPassword(@RequestBody ChangePasswordForm form) {
        userService.changePassword(form.getKey(), form.getPassword(), form.getRepeatPassword(), form.getToken());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/ask-reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkForm> askResetPassword(@RequestBody ResetPasswordForm form) {
        return new ResponseEntity<>(new LinkForm(userService.generateResetPasswordLink(form.getLogin(), form.getToken())), HttpStatus.OK);
    }
}
