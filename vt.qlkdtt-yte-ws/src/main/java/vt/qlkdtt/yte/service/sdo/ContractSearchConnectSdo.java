package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractSearchConnectSdo {
    private Long contractId;
    private String contractNo;
    private Long customerId;
    private String customerName;
    private String signDate;
    private String status;
    private String billNotificationMethod;
    private String billNotificationAddress;
    private String billAddress;
    private String representName;
    private String representTitle;
    private String representIdType;
    private String representIdNo;
    private String representEmail;
    private String representTel;
    private String paymentMethod;
    private String effectDate;
    private String areaCodeProvince;
    private String provinceName;
    private String areaCodeDistrict;
    private String districtName;
}
