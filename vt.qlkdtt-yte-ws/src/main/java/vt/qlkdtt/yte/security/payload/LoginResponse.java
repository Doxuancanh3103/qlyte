package vt.qlkdtt.yte.security.payload;

import lombok.Data;
import vt.qlkdtt.yte.service.sdo.UserTokenSdo;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserTokenSdo userTokenSdo;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponse(String accessToken , UserTokenSdo userTokenSdo) {
        this.accessToken = accessToken;
        this.userTokenSdo = userTokenSdo;
    }
}
