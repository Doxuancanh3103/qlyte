package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class SubscriberFindByIdSdo {
    private Long subscriberId;
    private Long contractId;
    private String contractNo;
    private String signDate;
    private String billNotificationMethod;
    private String billNotificationAddress;
    private String billAddress;
    private String representName;
    private String representIdNo;
    private String representTitle;
    private String representTel;
    private String representEmail;
    private Long productId;
    private String productName;
    private Long partnerId;
    private String partnerName;
    private String salePerson;
    private String saleChanelValue;
    private String saleChanelName;
    private Long productOfferId;
    private String productOfferName;
    private String projectCode;
    private String promotionName;
    private Long priceSale;
    private Long priceMin;
    private Long priceMax;
    private Long priceBase;
    private Long volBase;
    private Long volPromotion;
    private String accountNo;
    private String accountServiceNo;
    private String productCustSegmId;
    private String paymentMethod;
    private String medicalNo;
    private String brokersPartnerCode;
    private String medicalPermissionNo;
}
