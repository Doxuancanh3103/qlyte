package vt.qlkdtt.yte.service.sdo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchSdo {
    Long productId;
    String productCode;
    String productName;
    String productType;
    String listPartnerCode;
    String productStatus;
    Long serviceBccsId;
    String productBccsCode;
}
