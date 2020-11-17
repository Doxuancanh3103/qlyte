package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PRODUCT_OFFER")
public class ProductOffer {
    @Id
    @Column(name = "product_offer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long productOfferId;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "product_offer_type")
    String productOfferType;

    @Column(name = "code")
    String productOfferCode;

    @Column(name = "name")
    String productOfferName;

    @Column(name = "sta_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date staDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date endDate;

    @Column(name = "status")
    String status;

    @Column(name = "version")
    String version;

    @Column(name = "unit")
    String unit;

    @Column(name = "unit_price_base")
    Long unitPriceBase;

    @Column(name = "unit_price_min")
    Long unitPriceMin;

    @Column(name = "unit_price_max")
    Long unitPriceMax;

    @Column(name = "vol_limitation_type")
    String volLimitationType;

    @Column(name = "vol_base")
    Long volBase;

    @Column(name = "vol_promotion")
    Long volPromotion;

    @Column(name = "vat_type")
    String vatType;

    @Column(name = "is_deposit")
    Long isDeposit;

    @Column(name = "deposit_amount")
    Long depositAmount;

    @Column(name = "create_user")
    String createUser;

    @Column(name = "create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

    @Column(name = "update_user")
    String updateUser;

    @Column(name = "update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDatetime;

    @Column(name = "telco_service_id")
    Long telcoServiceId;

    @Column(name = "telco_product_offer_id")
    String telcoProductOfferId;

    @Column(name = "accounting_model_code")
    String accountingModelCode;

    @Column(name = "accounting_model_name")
    String accountingModelName;

    @Column(name = "accounting_name")
    String accountingName;

    @Column(name = "accounting_code")
    String accountingCode;

    @Column(name = "demo_duration")
    Long demoDuration;

    @Column(name = "is_demo")
    Long isDemo;

    @Column(name = "PROVISION_TYPE")
    String provisionType;

    @Column(name = "GL_CODE_1")
    private String glCode1;

    @Column(name = "GL_CODE_2")
    private String glCode2;

    @Column(name = "SALES_ITEM_CODE")
    private String salesItemCode;
}
