package vt.qlkdtt.yte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Row")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDataRowDTO {
    @XmlElement(name = "USER_ID")
    String userId;
    @XmlElement(name = "USER_RIGHT")
    String userRight;
    @XmlElement(name = "USER_NAME")
    String userName;
    @XmlElement(name = "PASSWORD")
    String password;
    @XmlElement(name = "STATUS")
    String status;
    @XmlElement(name = "EMAIL")
    String email;
    @XmlElement(name = "CELLPHONE")
    String cellphone;
    @XmlElement(name = "GENDER")
    String gender;
    @XmlElement(name = "LAST_CHANGE_PASSWORD")
    String lastChangePassword;
    @XmlElement(name = "LAST_BLOCK_DATE")
    String lastBlockDate;
    @XmlElement(name = "LOGIN_FAILURE_COUNT")
    String loginFailureCount;
    @XmlElement(name = "LAST_LOGIN_FAILURE")
    String lastLoginFailure;
    @XmlElement(name = "IDENTITY_CARD")
    String identityCard;
    @XmlElement(name = "FULL_NAME")
    String fullName;
    @XmlElement(name = "USER_TYPE_ID")
    String userTypeId;
    @XmlElement(name = "CREATE_DATE")
    String createDate;
    @XmlElement(name = "STAFF_CODE")
    String staffCode;
    @XmlElement(name = "MANAGER_ID")
    String managerId;
    @XmlElement(name = "LOCATION_ID")
    String locationId;
    @XmlElement(name = "PASSWORDCHANGED")
    String passwordChanged;
    @XmlElement(name = "LAST_LOGIN")
    String lastLogin;
    @XmlElement(name = "PROFILE_ID")
    String profileId;
    @XmlElement(name = "LAST_RESET_PASSWORD")
    String lastResetPassword;
    @XmlElement(name = "IP")
    String ip;
    @XmlElement(name = "DEPT_ID")
    String deptId;
    @XmlElement(name = "DEPT_LEVEL")
    String deptLevel;
    @XmlElement(name = "POS_ID")
    String posId;
    @XmlElement(name = "DEPT_NAME")
    String deptName;
    @XmlElement(name = "CHECK_VALID_TIME")
    String checkValidTime;
    @XmlElement(name = "START_TIME_TO_CHANGE_PASSWORD")
    String startTimeToChangePassword;
    @XmlElement(name = "IP_LAN")
    String ipLan;
    @XmlElement(name = "LAST_UNLOCK")
    String lastUnlock;
    @XmlElement(name = "CHECK_IP")
    String checkIp;
    @XmlElement(name = "CHECK_IP_LAN")
    String checkIpLan;
    @XmlElement(name = "LAST_LOCK")
    String lastLock;
    @XmlElement(name = "TEMP_LOCK_COUNT")
    String tempLockCount;
    @XmlElement(name = "USE_SALT")
    String useSalt;
    @XmlElement(name = "TEMP_PAZZ")
    String tempPazz;
    @XmlElement(name = "CAUSE_LOCK_USER")
    String causeLockUser;
    @XmlElement(name = "LOGIN_FAIL_ALLOW")
    String loginFailAllow;
    @XmlElement(name = "TEMPORARY_LOCK_TIME")
    String temporaryLockTime;
    @XmlElement(name = "MAX_TMP_LOCK_ADAY")
    String maxTmpLockAday;
    @XmlElement(name = "PASSWORD_VALID_TIME")
    String passwordValidTime;
    @XmlElement(name = "USER_VALID_TIME")
    String userValidTime;
    @XmlElement(name = "ALLOW_MULTI_IP_LOGIN")
    String allowMultiIpLogin;
    @XmlElement(name = "ALLOW_IP")
    String allowIp;
    @XmlElement(name = "ALLOW_LOGIN_TIME_START")
    String allowLoginTimeStart;
    @XmlElement(name = "ALLOW_LOGIN_TIME_END")
    String allowLoginTimeEnd;
    @XmlElement(name = "ID")
    String id;
    @XmlElement(name = "NAME")
    String name;
    @XmlElement(name = "NEED_CHANGE_PASSWORD")
    String needChangePassword;
    @XmlElement(name = "TIME_TO_CHANGE_PASSWORD")
    String timeToChangePassword;
    @XmlElement(name = "TIME_TO_PASSWORD_EXPIRE")
    String timeToPasswordExpire;
}
