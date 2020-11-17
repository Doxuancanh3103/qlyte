package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerPropective;

import java.util.Date;

@Data
public class CustomerPropectiveInsertSdi {
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

    public CustomerPropective toCustomerPropective() {
        CustomerPropective cp = new CustomerPropective();

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
        cp.setProductId(this.getProductId());
        cp.setMedicalNo(this.getMedicalNo());
        cp.setMedicalPermissionNo(this.getMedicalPermissionNo());

        if (!DataUtil.isNullOrEmpty(this.getMedicalPermissionDate())) {
            cp.setMedicalPermissionDate(DateUtil.string2Date(this.getMedicalPermissionDate(), Const.DATE_FORMAT));
        }

        cp.setAccountNo(this.getAccountNo());
        cp.setAccountServiceNo(this.getAccountServiceNo());
        cp.setCreateDatetime(new Date());
        cp.setCreateUser("ADMIN");
        cp.setStatus(Const.STATUS.ACTIVE);
        cp.setProvisionningStatus(Const.PROVISIONING_STATUS.WAIT_FOR_CONNECT);

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

    public CustomerPropective toCustomerPropective(CustomerPropective customerPropective) {
        CustomerPropective cp = new CustomerPropective();

        cp.setCustomerPropectiveCode(customerPropective.getCustomerPropectiveCode());
        cp.setCustomerId(customerPropective.getCustomerId());
        cp.setProductId(this.getProductId());
        cp.setCustomerName(this.getCustomerName());
        cp.setIdentityType(this.getIdentityType());
        cp.setIdentityNo(this.getIdentityNo());
        cp.setMedicalNo(this.getMedicalNo());
        cp.setProvince(this.getAreaCodeProvince());
        cp.setDistrict(this.getAreaCodeDistrict());
        cp.setPrecinct(this.getAreaCodePrecinct());
        cp.setAddress(this.getAddress());
        cp.setCreateUser(Const.ADMIN);
        cp.setCreateDatetime(new Date());
        cp.setStatus(customerPropective.getStatus());
        cp.setProvisionningStatus(Const.PROVISIONING_STATUS.WAIT_FOR_CONNECT);
        cp.setCustomerBusType(this.getCustomerBusTypeValue());
        cp.setCustomerType(this.getCustomerBusTypeValue());

        cp.setMedicalPermissionNo(this.getMedicalPermissionNo());

        if (!DataUtil.isNullOrEmpty(this.getMedicalPermissionDate())) {
            cp.setMedicalPermissionDate(DateUtil.string2Date(this.getMedicalPermissionDate(), Const.DATE_FORMAT));
        }

        if (!DataUtil.isNullOrEmpty(this.getBirthDate())) {
            cp.setBirthDate(DateUtil.string2Date(this.getBirthDate(), Const.DATE_FORMAT));
        }

        cp.setTel(this.getTel());
        cp.setEmail(this.getEmail());
        cp.setAccountNo(this.getAccountNo());
        cp.setAccountServiceNo(this.getAccountServiceNo());

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
