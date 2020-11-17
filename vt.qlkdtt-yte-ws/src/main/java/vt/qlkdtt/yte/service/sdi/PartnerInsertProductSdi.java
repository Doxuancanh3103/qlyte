package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerInsertProductSdi {
    long partnerId;
    long partnerPercent;
    String shareType;
    String description;
    Long documentId;
}