package vt.qlkdtt.yte.security.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    String vsaTicket;//= "ST-153905-YlaYMpcAJOno6SNUTeKB-cas";
    String returnUrl = null;
    @NotBlank
    String domainCode;// = "BCCS2_SALE";
    @NotBlank
    String validateUrl;// = "http://10.58.71.130:8001/passportv3/validate";
    @NotBlank
    String loginUrl;// = "http://localhost:8080/SALE_WEB/login";
}
