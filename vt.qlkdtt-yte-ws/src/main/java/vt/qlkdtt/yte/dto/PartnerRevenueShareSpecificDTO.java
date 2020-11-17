package vt.qlkdtt.yte.dto;

import lombok.Data;

@Data
public class PartnerRevenueShareSpecificDTO {
    Long partnerRevenueSharedId;
    Long partnerId;
    String partnerName;
    String tin;
    Long partnerPercent;
    Long documentId;
    String documentCode;
    String shareTypeValue;
    String shareTypeName;
}
