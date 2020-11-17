package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDetailSearchSdo {
    //CUSTOMER_ORDER
    String orderType;
    long customerOrderId;
    String orderContactName;
    String orderContactTel;
    String orderContactEmail;
    String orderActionTypeId;
    String assigneeCode;
    String orderDate;
    String dueDate;
    String status;

    //CUSTOMER
    long customerId;
    String customerBusType;
    String customerType;
    String customerName;
//    String medicalPermissionNo;
    String customerIdentityNo;
    String birthDate;
    String tel;
    String email;
    String province;
    String district;
    String precinct;
    String address;
//    String representName;
//    String representIdNo;
//    String representTitle;
//    String representTel;
//    String representEmail;

    //CUSTOMER_CONTRACT
    long contractId;
    String contractNo;
    String signDate;
    String contractRepresentName;
    String contractRepresentIdNo;
    String contractRepresentTitle;
    String contractRepresentTel;
    String contractRepresentEmail;

    //CUSTOMER_ACCOUNT
    long customerAccountId;
    String billNotificationMethod;
    String billAddress;
    String billNotificationAddress;
    String paymentMethod;

    //SUBSCRIBER
    long subscriberId;
    long productId;
    String productName;
    long partneId;
    String partnerName;
    String salePerson;
    String saleChanel;
    long productOfferId;
    String productOfferName;
    String projectCode;
    String promotionName;
    long priceSale;
    long priceMin;
    long priceBase;
    long volBase;
    long volPromotion;
    String accountNo;
    String accountServiceNo;
    String medicalNo;
    String medicalPermissionNo;
    String brokersPartnerCode;
    String productCustSegmId;
    Long priceMax;
}
