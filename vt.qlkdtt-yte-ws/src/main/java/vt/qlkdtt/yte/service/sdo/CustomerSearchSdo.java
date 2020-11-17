package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class CustomerSearchSdo {
    private Long customerId;
    private Long customerPropectiveId;
    private String customerPropectiveCode;
    private String customerBusType;
    private String customerType;
    private String customerName;
    private String customerIdentityNo;
    private String customerIdentityType;
    private String birthDate;
    private String province;
    private String district;
    private String precinct;
    private String address;
    private String tel;
    private String email;
    private String representName;
    private String representIdType;
    private String representIdNo;
    private String representTitle;
    private String representTel;
    private String representEmail;
    private Long countSubs;
    private String salesManCode;
    private String brokersPartnerCode;
    private String medicalOrgType;
    private String accountNo;
    private String accountServiceNo;
    private String medicalNo;
    private Long productId;
    private String productCode;
    private String medicalPermissionNo;
    private String medicalPermissionDate;

//    private String paymentMethod;
//    private String billNotificationMethod;
//    private String billNotificationAddress;
//    private String billAddress;



}
