package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class MedSaleFeeConfigSearchSdo {
    private String feeConfigCode;
    private String feeConfigName;
    private String feeConfigDescription;
    private Long telecomServiceId;
    private String productOfferCode;
    private String staffCode;
    private String channelTypeId;
    private String provinceCode;
    private Long saleLevel;
    private Long feeValue;
    private Integer feeType;
    private String startDate;
    private String endDate;
    private String status;
    private String createUser;
    private String updateUser;
    private String updateDate;
}
