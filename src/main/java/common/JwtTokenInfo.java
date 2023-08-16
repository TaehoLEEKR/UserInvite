package common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class JwtTokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;

}
