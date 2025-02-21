package com.hd.vbookstore.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "refresh_tokens")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Date expiryDate;
}