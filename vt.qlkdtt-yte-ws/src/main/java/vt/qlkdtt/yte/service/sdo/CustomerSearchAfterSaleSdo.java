package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class CustomerSearchAfterSaleSdo {
    private Long customerId;
    private String province;
    private String provinceName;
    private String district;
    private String districtName;
    private String customerBusType;
    private String customerName;
    private String customerIdentityNo;
    private String tel;
    private String effectDate;
    private String expireDate;
    private String accountServiceNo;
}
