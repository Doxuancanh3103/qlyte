package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

@Data
public class CustomerUpdateSdi {
    private Long customerId;
    private String orderType;
    private String customerBusType;
    private String customerType;
    private String customerName;
    private String medicalPermissionNo;
    private String customerIdentityType;
    private String customerIdentityNo;
    private String birthDate;
    private String tel;
    private String email;
    private String address;
    private String areaCode; //Area code precinct
    private String representName;
    private String representIdNo;
    private String representTitle;
    private String representTel;
    private String representEmail;
}
