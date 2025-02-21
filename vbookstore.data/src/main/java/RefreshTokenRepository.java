package com.hd.vbookstore.data;

import com.hd.vbookstore.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsernameAndToken(String username, String token);
    void deleteByUsername(String username);
}
