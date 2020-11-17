package vt.qlkdtt.yte.dto;

import lombok.Data;

@Data
public class CustomerProductOfferFindDTO {
    Long customerPropectiveId;
    String areaCodeProvince;
    String areaCodeDistrict;
    String provinceCode;
    String provinceName;
    String districtCode;
    String districtName;
    String customerName;
    String customerIdentityType;
    String customerIdentityNo;
    String medicalNo;
    String status;
    String staDate;
    String dueDate;
    String customerBusType;
}
