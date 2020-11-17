package vt.qlkdtt.yte.service.sdi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartnerChangeStatusSdi {
    boolean isActive;
    // validate
    long partnerId;
    String user;
}
