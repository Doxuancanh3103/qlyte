package vt.qlkdtt.yte.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartnerProductFindDTO {
    Long partnerRevenueSharedId;
    Long documentId;
    String documentName;
    Long partnerId;
    String partnerName;
    String tin;
    Long partnerPercent;
    String shareTypeName;
    String shareTypeValue;
    String description;
}
