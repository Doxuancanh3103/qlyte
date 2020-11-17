package vt.qlkdtt.yte.security.jwt;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import viettel.passport.client.ObjectToken;
import viettel.passport.client.ServiceTicketValidator;
import viettel.passport.client.UserToken;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.security.payload.LoginRequest;
import vt.qlkdtt.yte.security.user.CustomUserDetails;
import vt.qlkdtt.yte.security.user.User;
import vt.qlkdtt.yte.service.LoginService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdo.UserTokenSdo;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    LoginService loginService;

    @Override
    public Authentication authenticate(Authentication var1) throws AuthenticationException {
        String vsaTicket = "ST-153905-YlaYMpcAJOno6SNUTeKB-cas";
        String returnUrl = null;
        String domainCode = "BCCS2_SALE";
        String validateUrl = "http://10.58.71.130:8001/passportv3/validate";
        String loginUrl = "http://localhost:8080/SALE_WEB/login";

        LoginRequest loginRequest = (LoginRequest) var1.getPrincipal();
        vsaTicket = loginRequest.getVsaTicket();
        returnUrl = loginRequest.getReturnUrl();
        domainCode = loginRequest.getDomainCode();
        validateUrl = loginRequest.getValidateUrl();
        loginUrl = loginRequest.getLoginUrl();

        // Call restApi cua passport
//        ServiceTicketValidator stValidator = new ServiceTicketValidator();
//        stValidator.setCasValidateUrl(validateUrl);
//        stValidator.setServiceTicket(vsaTicket);
//        if (!DataUtil.isStringNullOrEmpty(returnUrl)) {
//            stValidator.setService(loginUrl + "?return=" + returnUrl);
//        } else {
//            stValidator.setService(loginUrl);
//        }
//
//        stValidator.setDomainCode(domainCode);
//        try {
//            stValidator.validate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }

        if (!vsaTicket.startsWith("ST-")) {
            throw new AppException("API-LG001", "Khong ton tai ma VSA :" + vsaTicket);
        }
//        UserToken userToken = stValidator.getUserToken();
        UserToken userToken = loginService.getUserData();

        CustomPrincipal principal = new CustomPrincipal();
        List<String> vsaAllowedURL = new ArrayList<>();
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        CustomUserDetails customUserDetails = new CustomUserDetails();

        if (userToken != null) {
            for (ObjectToken component : userToken.getComponentList()) {
                GrantedAuthority subComponent = new GrantedAuthorityPassport();
                BeanUtils.copyProperties(component, subComponent, "childObjects");

                grantedAuths.add(subComponent);
            }
            User user = new User();

            user.setAliasName(userToken.getAliasName());
            user.setBirthPlace(userToken.getBirthPlace());
            user.setCellphone(userToken.getCellphone());
            user.setDateOfBirth(userToken.getDateOfBirth());
            user.setDescription(userToken.getDescription());
            user.setEmail(userToken.getEmail());
            user.setFax(userToken.getFax());
            user.setFullName(userToken.getFullName());
            user.setGender(userToken.getGender());
            user.setIdentityCard(userToken.getIdentityCard());
            user.setIssueDateIdent(userToken.getIssueDateIdent());
            user.setIssueDatePassport(userToken.getIssueDatePassport());
            user.setIssuePlaceIdent(userToken.getIssuePlaceIdent());
            user.setIssuePlacePassport(userToken.getIssuePlacePassport());
            user.setPassportNumber(userToken.getPassportNumber());
            user.setStaffCode(userToken.getStaffCode());
            user.setStatus(userToken.getStatus());
            user.setTelephone(userToken.getTelephone());
            user.setUserName(userToken.getUserName());
            user.setUserId(userToken.getUserID());
            user.setUserRight(userToken.getUserRight());
            user.setTimeToPasswordExpire(userToken.getTimeToPasswordExpire());
            user.setLastChangePassword(userToken.getLastChangePassword());
            user.setPasswordValidTime(userToken.getPasswordValidTime());
            user.setDeptName(userToken.getDeptName());
            user.setDeptId(userToken.getDeptId());
            user.setUseSalt(userToken.getUseSalt());

            customUserDetails.setUser(user);

            principal.setCustomUserDetails(customUserDetails);

            // danh sach chuc nang duoc phep truy cap
            for (ObjectToken ot : userToken.getObjectTokens()) {
                String servletPath = ot.getObjectUrl();
                if (!("#".equals(servletPath))) {
                    vsaAllowedURL.add(servletPath.split("\\?")[0]);
                }
            }
            principal.setVsaAllowedURL(vsaAllowedURL);
        }

        // danh sach chuc nang tren menu
        GrantedAuthorityPassport newObjlv1 = new GrantedAuthorityPassport();
        for (ObjectToken obj : userToken.getParentMenu()) {
            BeanUtils.copyProperties(obj, newObjlv1, "childObjects");
            //child lvl2
            ArrayList<ObjectToken> objlv2Lst = new ArrayList<>();
            for (ObjectToken objlv2 : obj.getChildObjects()) {
                ObjectToken newObjlv2 = new ObjectToken();
                BeanUtils.copyProperties(objlv2, newObjlv2, "childObjects");

                // child lvl3
                ArrayList<ObjectToken> objlv3Lst = new ArrayList<>();
                for (ObjectToken objlv3 : objlv2.getChildObjects()) {
                    ObjectToken newObjlv3 = new ObjectToken();
                    BeanUtils.copyProperties(objlv3, newObjlv3, "childObjects");

                    //child lvl4
                    ArrayList<ObjectToken> objlv4Lst = new ArrayList<>();
                    for (ObjectToken objlv4 : objlv3.getChildObjects()) {
                        ObjectToken newObjlv4 = new ObjectToken();
                        BeanUtils.copyProperties(objlv4, newObjlv4, "childObjects");

                        objlv4Lst.add(newObjlv4);
                    }
                    newObjlv3.setChildObjects(objlv4Lst);

                    objlv3Lst.add(newObjlv3);
                }
                newObjlv2.setChildObjects(objlv3Lst);

                objlv2Lst.add(newObjlv2);
            }
            newObjlv1.setChildObjects(objlv2Lst);
        }

        principal.setParentMenu(newObjlv1);
        principal.setRolesList(userToken.getRolesList());

        return new UsernamePasswordAuthenticationToken(principal, null, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
