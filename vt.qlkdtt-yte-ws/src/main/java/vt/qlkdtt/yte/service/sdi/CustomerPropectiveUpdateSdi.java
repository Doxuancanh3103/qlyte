package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerPropective;

import java.util.Date;

@Data
public class CustomerPropectiveUpdateSdi {
    private Long customerPropectiveId;
    private String customerBusTypeValue;
    private String customerName;
    private String identityType;
    private String identityNo;
    private String birthDate;
    private String areaCodeProvince;
    private String areaCodeDistrict;
    private String areaCodePrecinct;
    private String address;
    private String tel;
    private String email;
    private String status;
    private Long productId;
    private String medicalNo;
    private String medicalPermissionNo;
    private String medicalPermissionDate;
    private String accountNo;
    private String accountServiceNo;
    private String representName;
    private String representIdType;
    private String representIdNo;
    private String representTel;
    private String representEmail;
    private String salesManCode;
    private String brokersPartnerCode;
    private String medicalOrgType;

    public CustomerPropective updateCustomerPropective(CustomerPropective cp) {
        cp.setCustomerBusType(this.getCustomerBusTypeValue());
        cp.setCustomerName(this.getCustomerName());
        cp.setIdentityType(this.getIdentityType());
        cp.setIdentityNo(this.getIdentityNo());

        if (!DataUtil.isNullOrEmpty(this.getBirthDate())) {
            cp.setBirthDate(DateUtil.string2Date(this.getBirthDate(), Const.DATE_FORMAT));
        }

        cp.setProvince(this.getAreaCodeProvince());
        cp.setDistrict(this.getAreaCodeDistrict());
        cp.setPrecinct(this.getAreaCodePrecinct());
        cp.setAddress(this.getAddress());
        cp.setTel(this.getTel());
        cp.setEmail(this.getEmail());
        cp.setStatus(this.getStatus());
        cp.setProductId(this.getProductId());
        cp.setMedicalNo(this.getMedicalNo());
        cp.setMedicalPermissionNo(this.getMedicalPermissionNo());

        if (!DataUtil.isNullOrEmpty(this.getMedicalPermissionDate())) {
            cp.setMedicalPermissionDate(DateUtil.string2Date(this.getMedicalPermissionDate(), Const.DATE_FORMAT));
        }

        cp.setAccountNo(this.getAccountNo());
        cp.setAccountServiceNo(this.getAccountServiceNo());
        cp.setUpdateDatetime(new Date());
        cp.setUpdateUser("ADMIN");
        cp.setStatus(this.getStatus());
        cp.setRepresentName(this.getRepresentName());
        cp.setRepresentIdType(this.getRepresentIdType());
        cp.setRepresentIdNo(this.getRepresentIdNo());
        cp.setRepresentTel(this.getRepresentTel());
        cp.setRepresentEmail(this.getRepresentEmail());
        cp.setSalesManCode(this.getSalesManCode());
        cp.setBrokersPartnerCode(this.getBrokersPartnerCode());
        cp.setMedicalOrgType(this.getMedicalOrgType());

        return cp;
    }
}
