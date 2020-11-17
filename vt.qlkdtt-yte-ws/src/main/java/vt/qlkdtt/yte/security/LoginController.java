package vt.qlkdtt.yte.security;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import viettel.passport.client.ObjectToken;
import viettel.passport.client.UserToken;
import vt.qlkdtt.yte.security.jwt.CustomPrincipal;
import vt.qlkdtt.yte.security.jwt.GrantedAuthorityPassport;
import vt.qlkdtt.yte.security.jwt.JwtTokenProvider;
import vt.qlkdtt.yte.security.payload.LoginRequest;
import vt.qlkdtt.yte.security.payload.LoginResponse;
import vt.qlkdtt.yte.security.user.CustomUserDetails;
import vt.qlkdtt.yte.security.user.User;
import vt.qlkdtt.yte.service.LoginService;
import vt.qlkdtt.yte.service.sdo.UserTokenSdo;

import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {
    //    @Value("${login.loginUrl}")
    private String loginUrl;

    //    @Value("${login.validateUrl}")
    private String validateUrl;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) throws IOException, ParserConfigurationException {
        // Xác thực thông tin người dùng Request lên
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest,
                        null
                )
        );
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken(customPrincipal.getCustomUserDetails());
        return new LoginResponse(jwt,getDataTest());
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping(value = "getAllUserData")
    @ApiOperation(value = "getAllUserData")
    public ResponseEntity<?> getAllUserData() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserTokenSdo userTokenSdo = new UserTokenSdo();
//        if (authentication != null) {
//            if (authentication.getPrincipal() != null) {
//                try {
//                    CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
//                    if (customPrincipal != null) {
//                        userTokenSdo.setCustomUserDetails((customPrincipal.getCustomUserDetails()));
//                        userTokenSdo.setParentMenu(customPrincipal.getParentMenu());
//                        userTokenSdo.setRolesList(customPrincipal.getRolesList());
//                        userTokenSdo.setVsaAllowedURL(customPrincipal.getVsaAllowedURL());
//                    }
//                } catch (Exception ex) {
//                    log.error("authentication.getPrincipal() is not CustomPrincipal");
//                }
//            }
//
//            if (authentication.getAuthorities() != null) {
//                try {
//                    userTokenSdo.setComponentList((List<GrantedAuthority>) authentication.getAuthorities());
//                } catch (Exception ex) {
//                    log.error("authentication.getAuthorities() is not GrantedAuthorityPassport");
//                }
//            }
//        }
        return ResponseEntity.ok(getDataTest());
    }

    public UserTokenSdo getDataTest() {
        UserTokenSdo userTokenSdo = new UserTokenSdo();

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

        try {

            if (principal != null) {
                userTokenSdo.setCustomUserDetails((principal.getCustomUserDetails()));
                userTokenSdo.setParentMenu(principal.getParentMenu());
                userTokenSdo.setRolesList(principal.getRolesList());
                userTokenSdo.setVsaAllowedURL(principal.getVsaAllowedURL());
            }

            userTokenSdo.setComponentList(grantedAuths);
        } catch (Exception ex) {
            log.error("authentication.getPrincipal() is not CustomPrincipal");
        }

        return userTokenSdo;
    }
}
