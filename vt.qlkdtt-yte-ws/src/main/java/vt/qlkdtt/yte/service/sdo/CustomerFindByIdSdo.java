package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

import java.util.List;

@Data
public class CustomerFindByIdSdo {
    private Long customerId;
    private String customerBusType;
    private String customerType;
    private String customerName;
    private String customerIdentityType;
    private String customerIdentityNo;
    private String birthday;
    private String tel;
    private String email;
    private String address;
    private String areaCode;
    private String province;
    private String district;
    private String precinct;
    private String representName;
    private String representIdNo;
    private String representTitle;
    private String representTel;
    private String representEmail;

    private List<ContractCustomerFindByIdSdo> listContract;
    private List<DocumentCustomerFindByIdSdo> listDocument;
}
