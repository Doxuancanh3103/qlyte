package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.ProductOffer;

import java.util.Date;
import java.util.List;

@Data
public class ProductOfferUpdateSdi {
    private Long productOfferId;
    private Long productId;
    private String productOfferCode;
    private String productOfferName;
    private String accountingCode;
    private String telcoProductOfferId;
    private String productOfferType;
    private Long unitPriceBase;
    private Long unitPriceMin;
    private Long unitPriceMax;
    private String vatType;
    private String volLimitType;
    private Long volBase;
    private Long volPromotion;
    private String status;
    private String staDate;
    private String endDate;
    private Long documentId;
    private String provisionType;
    private String glCode1;
    private String glCode2;
    private String salesItemCode;

    List<String> listCustomerType;
    List<ProductOfferAreaUpdateProductOffer> listProductOfferArea;
    List<CustomerPropectUpdateProductOffer> listProductOfferCustomer;
    List<PartnerShareUpdateProductOfferSdi> listPartnerRevenueShareSpecific;

    public ProductOffer updateProductOffer(ProductOffer po) {
        po.setProductOfferId(this.getProductOfferId());
        po.setProductId(this.getProductId());
        po.setProductOfferCode(this.getProductOfferCode());
        po.setProductOfferName(this.getProductOfferName());
        po.setAccountingCode(this.getAccountingCode());
        po.setTelcoProductOfferId(this.getTelcoProductOfferId());
        po.setProductOfferType(this.getProductOfferType());
        po.setUnitPriceBase(this.getUnitPriceBase());
        po.setUnitPriceMin(this.getUnitPriceMin());
        po.setUnitPriceMax(this.getUnitPriceMax());
        po.setVatType(this.getVatType());
        po.setVolLimitationType(this.getVolLimitType());
        po.setVolBase(this.getVolBase());
        po.setVolPromotion(this.getVolPromotion());
        po.setStatus(this.getStatus());
        po.setStaDate(DateUtil.string2Date(this.getStaDate(), Const.DATE_FORMAT));
        po.setEndDate(DateUtil.string2Date(this.getEndDate(), Const.DATE_FORMAT));
        po.setUpdateUser("ADMIN");
        po.setUpdateDatetime(new Date());
        po.setProvisionType(this.getProvisionType());
        po.setGlCode1(this.getGlCode1());
        po.setGlCode2(this.getGlCode2());
        po.setSalesItemCode(this.getSalesItemCode());

        return po;
    }
}
