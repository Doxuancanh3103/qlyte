package vt.qlkdtt.yte.service.sdo;

import lombok.Data;
import vt.qlkdtt.yte.dto.AreaProductOfferFindDTO;
import vt.qlkdtt.yte.dto.CustomerProductOfferFindDTO;
import vt.qlkdtt.yte.dto.PartnerRevenueShareSpecificDTO;
import vt.qlkdtt.yte.dto.PcsProductOfferFindDTO;

import java.util.List;

@Data
public class ProductOfferFindByIdSdo {
    private Long productOfferId;
    private Long productId;
    private String productCode;
    private String productName;
    private String productOfferCode;
    private String productOfferName;
    private String accountingCode;
    private Long telcoProductOfferId;
    private String productOfferTypeValue;
    private String productOfferTypeName;
    private Long unitPriceBase;
    private Long unitPriceMin;
    private Long unitPriceMax;
    private String vatTypeValue;
    private String vatTypeName;
    private String volLimitTypeValue;
    private String volLimitTypeName;
    private Long volBase;
    private Long volPromotion;
    private String status;
    private String staDate;
    private String endDate;
    private Long docId;
    private String docCode;
    private String docName;
    private String provisionType;
    private String glCode1;
    private String glCode2;
    private String salesItemCode;

    List<PcsProductOfferFindDTO> listCustomerType;
    List<AreaProductOfferFindDTO> listProductOfferArea;
    List<CustomerProductOfferFindDTO> listProductOfferCustomer;
    List<PartnerRevenueShareSpecificDTO> listPartnerRevenueShareSpecific;
}
