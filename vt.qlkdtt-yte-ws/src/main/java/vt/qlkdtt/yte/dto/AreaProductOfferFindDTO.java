package vt.qlkdtt.yte.dto;

import lombok.Data;

@Data
public class AreaProductOfferFindDTO {
    Long productOfferAreaId;
    String areaCode;
    String provinceCode;
    String provinceName;
    String districtCode;
    String districtName;
    String status;
    String staDate;
    String endDate;
    String areaCodeProvince;
    String areaCodeDistrict;
    String areaCodePrecinct;
}
