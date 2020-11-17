package vt.qlkdtt.yte.dto;

import lombok.Data;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;

import java.util.Date;

@Data
public class MedSaleFeeConfigDTO {
    private String feeConfigCode;
    private String feeConfigName;
    private String feeConfigDescription;
    private Long telecomServiceId;
    private String productOfferCode;
    private String staffCode;
    private Long channelTypeId;
    private String provinceCode;
    private Long saleLevel;
    private Long feeValue;
    private Integer feeType;
    private String startDate;
    private String endDate;
    private String status;
    private Date createDate;

    public MedSaleFeeConfig convertToMedSaleFeeConfig() {
        MedSaleFeeConfig medSaleFeeConfig = new MedSaleFeeConfig();
        medSaleFeeConfig.setFeeConfigCode(this.feeConfigCode);

        return medSaleFeeConfig;
    }
}
