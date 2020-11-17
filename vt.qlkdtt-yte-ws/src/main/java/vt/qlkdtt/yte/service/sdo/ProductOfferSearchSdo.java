package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

import java.util.Date;

@Data
public class ProductOfferSearchSdo {
    Long productOfferId;
    String productCode;
    String productOfferCode;
    String productOfferName;
    String telcoProductOfferId;
    String status;
    String productOfferAreas;
    String customerTypes;
    String productOfferType;
    Long unitPriceBase;
    Long unitPriceMin;
    Long unitPriceMax;
    Long volBase;
    Long volPromotion;
    String vatType;
    String vatName;
    String staDate;
    String endDate;
}
