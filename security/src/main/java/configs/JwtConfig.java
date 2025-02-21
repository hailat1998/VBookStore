package com.hd.vbookstore.security.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(secret, accessTokenExpiration, refreshTokenExpiration);
    }
}
