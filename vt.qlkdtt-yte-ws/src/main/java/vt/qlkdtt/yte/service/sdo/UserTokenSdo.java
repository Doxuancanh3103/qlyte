package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import viettel.passport.client.RoleToken;
import vt.qlkdtt.yte.security.jwt.GrantedAuthorityPassport;
import vt.qlkdtt.yte.security.user.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTokenSdo {
    private CustomUserDetails customUserDetails;
    private ArrayList<RoleToken> rolesList = new ArrayList();
    private List<String> vsaAllowedURL = new ArrayList<>();
    private GrantedAuthorityPassport parentMenu = new GrantedAuthorityPassport();
    private List<GrantedAuthority> componentList =  new ArrayList();
}
