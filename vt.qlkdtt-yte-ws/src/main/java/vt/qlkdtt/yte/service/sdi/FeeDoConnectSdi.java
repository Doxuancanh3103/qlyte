package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

@Data
public class FeeDoConnectSdi {
    private String feeType;
    private String chargeUpDate;
    private Long quantity;
    private Long price;
    private Long vat;
}
