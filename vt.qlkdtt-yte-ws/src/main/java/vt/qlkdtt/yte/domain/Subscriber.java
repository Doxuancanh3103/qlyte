package vt.qlkdtt.yte.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "SUBSCRIBER")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUBSCRIBER_ID")
    private Long subscriberId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_ACCOUNT_ID")
    private Long customerAccountId;

    @Column(name = "CUSTOMER_CONTRACT_ID")
    private Long customerContractId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PARTNER_ID")
    private Long partnerId;

    @Column(name = "STAFF_CODE")
    private String staffCode;

    @Column(name = "SHOP_CODE")
    private String shopCode;

    @Column(name = "SALES_CHANNEL_TYPE")
    private String salesChannelType;

    @Column(name = "PRODUCT_OFFER_ID")
    private Long productOfferId;

    @Column(name = "PROJECT_CODE")
    private String projectCode;

    @Column(name = "PROMOTION_CODE")
    private String promotionCode;

    @Column(name = "PRICE_SALES")
    private Long priceSale;

    @Column(name = "PRICE_MIN")
    private Long priceMin;

    @Column(name = "PRICE_MAX")
    private Long priceMax;

    @Column(name = "PRICE_BASE")
    private Long priceBase;

    @Column(name = "VOL_BASE")
    private Long volBase;

    @Column(name = "VOL_PROMOTION")
    private Long volPromotion;

    @Column(name = "STA_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date staDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "ACT_STATUS")
    private String actStatus;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "APP_ACCOUNT_NO")
    private String appAccountNo;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDatetime;

    @Column(name = "TELCO_SUBSCRIBER_ID")
    private Long telcoSubscriberId;

    @Column(name = "TELCO_SUB_TYPE")
    private String telcoSubType;

    @Column(name = "TELCO_PRODUCT_ID")
    private Long telcoProductId;

    @Column(name = "TELCO_PROMOTION_CODE")
    private Long telcoPromotionCode;

    @Column(name = "TELCO_PRODUCT_OFFER_ID")
    private Long telcoProductOfferId;

    @Column(name = "TELCO_REGIS_TYPE")
    private String telcoRegisType;

    @Column(name = "TELCO_PAY_TYPE")
    private String telcoPayType;

    @Column(name = "PRODUCT_CUST_SEGM_ID")
    private String productCustSegmId;

    @Column(name = "PRODUCT_OFFER_TYPE")
    private String productOfferType;

    @Column(name = "PROVISON_TYPE")
    private String provisionType;

    @Column(name = "MEDICAL_ORG_TYPE")
    private String medicalOrgType;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "EFFECTIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Column(name = "EXPIRE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;

    @Column(name = "BROKERS_PARTNER_CODE")
    private String brokersPartnerCode;
}
