package vt.qlkdtt.yte.security.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String password;
    private String aliasName;
    private String birthPlace;
    private String cellphone;
    private Date dateOfBirth;
    private String description;
    private String email;
    private String fax;
    private String fullName;
    private long gender;
    private String identityCard;
    private Date issueDateIdent;
    private Date issueDatePassport;
    private String issuePlaceIdent;
    private String issuePlacePassport;
    private String passportNumber;
    private String staffCode;
    private long status;
    private String telephone;
    private String userName;
    private Long userId;
    private long userRight;
    private long timeToPasswordExpire;
    private Date lastChangePassword;
    private long passwordValidTime;
    private String deptName;
    private Long deptId;
    private long useSalt;
}
