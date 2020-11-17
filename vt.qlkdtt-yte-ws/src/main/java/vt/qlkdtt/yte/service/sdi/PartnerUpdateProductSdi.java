package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerUpdateProductSdi {
    long partnerRevenueSharedId;
    long partnerId;
    long partnerPercent;
    String shareType;
    long documentId;
    String description;
}
