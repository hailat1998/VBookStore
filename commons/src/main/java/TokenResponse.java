package com.hd.vbookstore.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    public String accessToken;
    public String refreshToken;
    public String bearer;
    public Long expirationDate;
}

