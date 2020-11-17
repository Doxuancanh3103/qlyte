package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.domain.Partner;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerSdo {
    long partnerId;
    String partnerCode;

    public PartnerSdo toPartnerSdo(Partner partner) {
        return PartnerSdo.builder()
                .partnerId(partner.getPartnerId())
                .partnerCode(partner.getPartnerCode())
                .build();
    }
}
