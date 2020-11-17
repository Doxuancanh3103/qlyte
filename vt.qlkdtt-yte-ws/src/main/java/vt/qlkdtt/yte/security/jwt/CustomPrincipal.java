package vt.qlkdtt.yte.security.jwt;

import lombok.Getter;
import lombok.Setter;
import viettel.passport.client.RoleToken;
import vt.qlkdtt.yte.security.user.CustomUserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomPrincipal implements Serializable {
    private CustomUserDetails customUserDetails = new CustomUserDetails();
    private List<String> vsaAllowedURL = new ArrayList<>();
    private GrantedAuthorityPassport parentMenu = new GrantedAuthorityPassport();
    private ArrayList<RoleToken> rolesList = new ArrayList();
}
