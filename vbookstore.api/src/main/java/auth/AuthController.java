package com.hd.vbookstore.api.auth;

import com.hd.vbookstore.commons.*;
import com.hd.vbookstore.commons.exceptions.TokenRefreshException;
import com.hd.vbookstore.core.services.AuthenticationService;
import com.hd.vbookstore.core.services.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final TokenService tokenService;
   private final AuthenticationService authenticationService;

    public AuthController(TokenService tokenService, UserDetailsService userDetailsService, AuthenticationService authenticationService) {

        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody RegisterUserDto registerUserDto) {
        log.info("");
     return ResponseEntity.ok(authenticationService.signup(registerUserDto));
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginUserDto loginRequest) {
        try {

            UserDetails userDetails = authenticationService.authenticate(loginRequest);
            TokenResponse tokenResponse = tokenService.createTokenPair(userDetails);

            return ResponseEntity.ok(tokenResponse);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestBody RefreshTokenDto request) {
        try {
            TokenResponse tokenResponse =
                    tokenService.refreshToken(request.getRefreshToken());
            return ResponseEntity.ok(tokenResponse);
        } catch (TokenRefreshException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage()
            );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal UserDetails userDetails) {
        tokenService.revokeRefreshToken(userDetails.getUsername());
        return ResponseEntity.ok().build();

    }
}