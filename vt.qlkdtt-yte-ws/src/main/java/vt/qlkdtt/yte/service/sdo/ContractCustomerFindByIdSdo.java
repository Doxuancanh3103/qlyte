package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class ContractCustomerFindByIdSdo {
    private Long contractId;
    private String productName;
    private String effectDate;
    private String expireDate;
    private Long productOfferId;
    private String productOfferName;
    private String contractNo;
    private String accountNo;
    private String status;
    private String medicalPermissionNo;
    private String connectDate;
    private String subsStatus;
    private Long subscriberId;
}
