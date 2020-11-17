package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class CustomerPropectiveSearchByIdSdo {
    private Long customerPropectiveId;
    private String customerPropectiveCode;
    private String customerBusTypeValue;
    private String customerName;
    private String customerIdentityType;
    private String customerIdentityNo;
    private String birthDate;
    private String areaCodeProvince;
    private String areaCodeDistrict;
    private String areaCodePrecinct;
    private String address;
    private String tel;
    private String email;
    private String status;
    private Long productId;
    private String productName;
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
    private String provioningStatus;
}
