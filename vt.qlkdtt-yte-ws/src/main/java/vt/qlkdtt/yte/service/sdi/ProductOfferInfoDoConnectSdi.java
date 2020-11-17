package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Subscriber;
import vt.qlkdtt.yte.domain.SubscriberExtMedical;

import java.util.Date;

@Data
public class ProductOfferInfoDoConnectSdi {
    private Long productId;
    private Long partnerId;
    private String salePerson;
    private String saleChanel;
    private Long productOfferId;
    private String projectCode;
    private String promotionCode;
    private Long priceSale;
    private Long priceMin;
    private Long priceMax;
//    private Long priceBase;
    private Long volBase;
    private Long volPromotion;
    private String accountBccs;
    private String accountServiceNo;
    private String productOfferType;
    private String provisionType;
    private String medicalOrgType;
    private String productCustSegmId;
    private String telcoPayType;
    private String brokersPartnerCode;
    private String medicalNo;
    private String medicalPermissionNo;
    private String medicalPermissionDate;

    public Subscriber toSubscriber(Long customerId, Long accountId, Long contractId, String paymentMethod) {
        Subscriber s = new Subscriber();

        s.setCustomerId(customerId);
        s.setCustomerAccountId(accountId);
        s.setCustomerContractId(contractId);
        s.setProductId(this.getProductId());
        s.setPartnerId(this.getPartnerId());
        s.setStaffCode(this.getSalePerson());
        s.setSalesChannelType(this.getSaleChanel());
        s.setProductOfferId(this.getProductOfferId());
        s.setProjectCode(this.getProjectCode());
        s.setPromotionCode(this.getPromotionCode());
        s.setPriceSale(this.getPriceSale());
        s.setPriceMin(this.getPriceMin());
        s.setPriceMax(this.getPriceMax());
//        s.setPriceBase(this.getPriceBase());
        s.setVolBase(this.getVolBase());
        s.setVolPromotion(this.getVolPromotion());
        s.setStatus(Const.STATUS.ACTIVE);
        s.setAccountNo(this.getAccountBccs());
        s.setAppAccountNo(this.getAccountServiceNo());
        s.setCreateUser("ADMIN");
        s.setCreateDatetime(new Date());
        s.setProductOfferType(this.getProductOfferType());
        s.setProductCustSegmId(this.getProductCustSegmId());
        s.setProvisionType(this.getProvisionType());
        s.setMedicalOrgType(this.getMedicalOrgType());
        s.setTelcoPayType(this.getTelcoPayType());
        s.setBrokersPartnerCode(this.getBrokersPartnerCode());

        return s;
    }

    public SubscriberExtMedical toSubscriberExtMedical(Long subscriberId){
        SubscriberExtMedical sem = new SubscriberExtMedical();

        sem.setSubscriberId(subscriberId);
        sem.setMedicalNo(this.getMedicalNo());
        sem.setMedicalPermissionNo(this.getMedicalPermissionNo());

        if (!DataUtil.isNullOrEmpty(this.getMedicalPermissionDate())) {
            sem.setMedicalPermissionDate(DateUtil.string2Date(this.getMedicalPermissionDate(), Const.DATE_FORMAT));
        }

        sem.setMedicalOrgType(this.getMedicalOrgType());
        sem.setProductCustSegm(this.getProductCustSegmId());
        sem.setCreateUser(Const.ADMIN);
        sem.setCreateDatetime(new Date());

        return sem;
    }
}
