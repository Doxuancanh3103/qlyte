package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MedSaleFeeConfigInsertSdi {
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


    public MedSaleFeeConfig convertToMedSaleFeeConfig() throws ParseException {
        MedSaleFeeConfig medSaleFeeConfig = new MedSaleFeeConfig();
        medSaleFeeConfig.setFeeConfigCode(this.feeConfigCode);
        medSaleFeeConfig.setStatus(this.status);
        medSaleFeeConfig.setFeeConfigName(this.feeConfigName);
        medSaleFeeConfig.setFeeConfigDescription(this.feeConfigDescription);
        medSaleFeeConfig.setChannelTypeId(this.channelTypeId);
        medSaleFeeConfig.setCreateUser(this.createUser);
        medSaleFeeConfig.setEndDate(this.endDate !=null ? new SimpleDateFormat("yyyy-MM-dd").parse(this.endDate) : null);
        medSaleFeeConfig.setFeeType(this.feeType);
        medSaleFeeConfig.setFeeValue(this.feeValue);
        medSaleFeeConfig.setProductOfferCode(this.productOfferCode);
        medSaleFeeConfig.setProvinceCode(this.provinceCode);
        medSaleFeeConfig.setSaleLevel(this.saleLevel);
        medSaleFeeConfig.setStaffCode(this.staffCode);
        medSaleFeeConfig.setStartDate(this.startDate != null ? new SimpleDateFormat("yyyy-MM-dd").parse(this.startDate):null);
        medSaleFeeConfig.setTelecomServiceId(this.telecomServiceId);
        medSaleFeeConfig.setUpdateDate(new Date());
        medSaleFeeConfig.setCreateDate(new Date());
        medSaleFeeConfig.setUpdateUser(this.updateUser);
        return medSaleFeeConfig;
    }
}
