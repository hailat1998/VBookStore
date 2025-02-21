package com.hd.vbookstore.core.services;

import com.hd.vbookstore.commons.TokenResponse;
import com.hd.vbookstore.commons.exceptions.TokenRefreshException;
import com.hd.vbookstore.data.RefreshTokenRepository;
import com.hd.vbookstore.domain.RefreshToken;
import com.hd.vbookstore.security.configs.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TokenService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenService(JwtTokenUtil jwtTokenUtil,
                        UserDetailsService userDetailsService,
                        RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public TokenResponse createTokenPair(UserDetails userDetails) {
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        saveRefreshToken(userDetails.getUsername(), refreshToken);

        return new TokenResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtTokenUtil.getExpirationFromToken(accessToken).getTime()
        );
    }

    private void saveRefreshToken(String username, String refreshToken) {
        RefreshToken token = RefreshToken.builder()
                .username(username)
                .token(refreshToken)
                .expiryDate(new Date(System.currentTimeMillis() +
                        jwtTokenUtil.getRefreshTokenExpiration()))
                .build();
        refreshTokenRepository.save(token);
    }

    public TokenResponse refreshToken(String refreshToken)
            throws TokenRefreshException {
        if (!jwtTokenUtil.validateToken(refreshToken)) {
            throw new TokenRefreshException("Invalid refresh token");
        }

        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        RefreshToken storedToken = refreshTokenRepository
                .findByUsernameAndToken(username, refreshToken)
                .orElseThrow(() -> new TokenRefreshException("Refresh token not found"));

        if (storedToken.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(storedToken);
            throw new TokenRefreshException("Refresh token expired");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return createTokenPair(userDetails);
    }

    public void revokeRefreshToken(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }
}
