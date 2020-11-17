package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class ProOfferProductSearchConnectSdo {
    Long productOfferId;
    String productOfferCode;
    String productOfferName;
    String productOfferType;
    Long priceBase;
    Long priceMin;
    Long priceMax;
    Long volBase;
    Long volPromotion;
}
