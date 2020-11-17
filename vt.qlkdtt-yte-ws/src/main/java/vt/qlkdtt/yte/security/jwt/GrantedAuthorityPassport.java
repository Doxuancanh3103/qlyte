package vt.qlkdtt.yte.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import viettel.passport.client.ObjectToken;

public class GrantedAuthorityPassport extends ObjectToken implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthority() {
        return getObjectCode();
    }

    @Override
    public String toString() {
        return "GrantedAuthorityPassport{" + getObjectCode() + ":" + getObjectName() + ":" + getObjectUrl() + "}";
    }
}
