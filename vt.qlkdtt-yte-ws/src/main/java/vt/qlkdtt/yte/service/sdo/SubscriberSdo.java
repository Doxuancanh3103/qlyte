package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberSdo {
    private long subscriberId;
    private long productId;
    private String productName;
    private long productOffferId;
    private String productOfferName;
    private String accountNo;
    private String effectDate;
    private String dueDate;
    private String status;
    private long priceSale;
    private long volBase;
    private long volPromotion;
    private String projectCode;
    private String promotionName;
    private String productCode;
    private String productOfferCode;
    private String accountServiceNo;
}
