package com.hd.vbookstore.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse implements Serializable {
    public String accessToken;
    public String refreshToken;
    public String bearer;
    public Long expirationDate;
}

