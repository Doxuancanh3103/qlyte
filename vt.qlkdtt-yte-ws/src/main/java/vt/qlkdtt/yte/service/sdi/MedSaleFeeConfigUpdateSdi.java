package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MedSaleFeeConfigUpdateSdi {

    private Long feeConfigId;
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
        medSaleFeeConfig.setFeeConfigId(this.feeConfigId != null ? this.feeConfigId : medSaleFeeConfig.getFeeConfigId());
        medSaleFeeConfig.setFeeConfigCode(this.feeConfigCode != null ? this.feeConfigCode : medSaleFeeConfig.getFeeConfigCode());
        medSaleFeeConfig.setStatus(this.status != null ? this.status : medSaleFeeConfig.getStatus());
        medSaleFeeConfig.setFeeConfigName(this.feeConfigName != null ? this.feeConfigName : medSaleFeeConfig.getFeeConfigName());
        medSaleFeeConfig.setFeeConfigDescription(this.feeConfigDescription != null ? this.feeConfigDescription : medSaleFeeConfig.getFeeConfigDescription());
        medSaleFeeConfig.setChannelTypeId(this.channelTypeId != null ? this.channelTypeId : medSaleFeeConfig.getChannelTypeId());
        medSaleFeeConfig.setCreateUser(this.createUser != null ? this.createUser : medSaleFeeConfig.getCreateUser());
        medSaleFeeConfig.setEndDate(this.endDate !=null ? new SimpleDateFormat("yyyy-MM-dd").parse(this.endDate) : medSaleFeeConfig.getEndDate());
        medSaleFeeConfig.setFeeType(this.feeType != null ? this.feeType : medSaleFeeConfig.getFeeType());
        medSaleFeeConfig.setFeeValue(this.feeValue != null ? this.feeValue : medSaleFeeConfig.getFeeValue());
        medSaleFeeConfig.setProductOfferCode(this.productOfferCode != null ? this.productOfferCode : medSaleFeeConfig.getProductOfferCode());
        medSaleFeeConfig.setProvinceCode(this.provinceCode != null ? this.provinceCode : medSaleFeeConfig.getProvinceCode());
        medSaleFeeConfig.setSaleLevel(this.saleLevel != null ? this.saleLevel : medSaleFeeConfig.getSaleLevel());
        medSaleFeeConfig.setStaffCode(this.staffCode != null ? this.staffCode : medSaleFeeConfig.getStaffCode());
        medSaleFeeConfig.setStartDate(this.startDate != null ? new SimpleDateFormat("yyyy-MM-dd").parse(this.startDate):medSaleFeeConfig.getStartDate());
        medSaleFeeConfig.setTelecomServiceId(this.telecomServiceId != null ? this.telecomServiceId : medSaleFeeConfig.getTelecomServiceId());
        medSaleFeeConfig.setUpdateDate(new Date());
        medSaleFeeConfig.setUpdateUser(this.updateUser != null ? this.updateUser : medSaleFeeConfig.getUpdateUser());
        return medSaleFeeConfig;
    }
}
