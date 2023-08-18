package com.example.userinviteassgin.component;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static JwtToken of(
            String jwtAccessToken, String refreshToken, String grantType, Long expiresIn) {

        return new JwtToken(jwtAccessToken, refreshToken, grantType, expiresIn);
    }
}
