package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class CustomerPropectiveSearchSdo {
    private Long customerPropectiveId;
    private String customerBusTypeName;
    private String customerName;
    private String identityNo;
    private String birthDate;
    private String provinceName;
    private String districtName;
    private String precinctName;
    private String status;
    private String medicalNo;
    private String accountNo;
    private String accountServiceNo;
    private String provisioningStatus;
    private String provisioningStatusName;
}
