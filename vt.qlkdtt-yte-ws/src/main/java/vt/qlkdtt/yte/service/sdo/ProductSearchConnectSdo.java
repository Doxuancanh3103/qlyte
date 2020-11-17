package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchConnectSdo {
    Long productId;
    String productName;
    String productCode;

    List<PartnerProductSearchConnectSdo> listPartner;
}
