package vt.qlkdtt.yte.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RevenueSharedDTO {
    long partnerRevenueSharedId;
    String productCode;
    String productName;
    long partnerPercent;
    String shareType;
    String status;
    String staDate;
    String endDate;
}
