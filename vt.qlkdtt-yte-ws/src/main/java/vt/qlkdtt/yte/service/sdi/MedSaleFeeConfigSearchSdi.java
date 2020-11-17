package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

@Data
public class MedSaleFeeConfigSearchSdi {
    private Long telecomServiceId;
    private String channelTypeId;
    private String productOfferCode;
    private String createUser;
    private String status;
    private String fromDate;
    private String toDate;
}
