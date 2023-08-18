package com.example.userinviteassgin.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AuthTokensGenerator {
    @Value("${jwt.token.validTime}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    private static final String BEARER_TYPE = "Bearer";

    @Value("${jwt.token.expireTime}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private final JwtTokenProvider jwtTokenProvider;

    public JwtToken generate(Long userId, List<String> roles) {

        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = userId.toString();
        String jwtAccessToken = jwtTokenProvider.createToken(subject,roles ,accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.createToken(subject,roles,refreshTokenExpiredAt);

        return JwtToken.of(
                jwtAccessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }
}
