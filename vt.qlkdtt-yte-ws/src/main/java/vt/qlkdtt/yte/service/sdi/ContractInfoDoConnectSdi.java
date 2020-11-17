package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerAccount;
import vt.qlkdtt.yte.domain.CustomerContract;

import java.util.Date;

@Data
public class ContractInfoDoConnectSdi {
    private Long contractId;
    private String contractNo;
    private String signDate;
    private String saleNotiMethod;
    private String saleNotiAddress;
    private String representName;
    private String representIdNo;
    private String representIdType;
    private String representTitle;
    private String representTel;
    private String representEmail;
    private String paymentMethod;
    private String billAddress;

    //<editor-fold desc="To customer contract">
    public CustomerContract toCustomerContract(Long customerId, Long customerAccountId, Long productOfferId, Long partnerId) {
        CustomerContract cc = new CustomerContract();

        cc.setContractId(this.getContractId());
        cc.setCustomerId(customerId);
        cc.setCustomerAccountId(customerAccountId);
        cc.setProductOfferId(productOfferId);
        cc.setPartnerId(partnerId);
        cc.setContractNo(this.getContractNo());
        cc.setStatus(Const.STATUS.ACTIVE);
        cc.setRepresentName(this.getRepresentName());
        cc.setRepresentIdNo(this.getRepresentIdNo());
        cc.setRepresentIdType(this.getRepresentIdType());
        cc.setRepresentTitle(this.getRepresentTitle());
        cc.setRepresentTel(this.getRepresentTel());
        cc.setRepresentEmail(this.getRepresentEmail());
        cc.setCreateUser("ADMIN");
        cc.setCreateDatetime(new Date());
        cc.setSignDate(DateUtil.string2Date(this.getSignDate(), Const.DATE_FORMAT));

        return cc;
    }
    //</editor-fold>

    public CustomerAccount toCustomerAccount(Long customerId, String accountNo, String accountServiceNo) {
        CustomerAccount ca = new CustomerAccount();

        ca.setCustomerId(customerId);
        ca.setStatus(Const.STATUS.ACTIVE);
        ca.setBillNotificationMethod(this.getSaleNotiMethod());
        ca.setBillNotificationAddress(this.getSaleNotiAddress());
        ca.setPaymentMethod(this.getPaymentMethod());
        ca.setBillAddress(this.getBillAddress());
        ca.setAccountNo(accountNo);
        ca.setAccountServiceNo(accountServiceNo);
        ca.setCreateUser("ADMIN");
        ca.setCreateDatetime(new Date());

        return ca;
    }
}
