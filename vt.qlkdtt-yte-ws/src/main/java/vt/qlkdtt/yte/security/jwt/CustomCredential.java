package vt.qlkdtt.yte.security.jwt;

import lombok.Getter;
import lombok.Setter;
import viettel.passport.client.RoleToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomCredential implements Serializable {
    private List<String> vsaAllowedURL = new ArrayList<>();
    private GrantedAuthorityPassport parentMenu = new GrantedAuthorityPassport();
    private ArrayList<RoleToken> rolesList = new ArrayList();
}
