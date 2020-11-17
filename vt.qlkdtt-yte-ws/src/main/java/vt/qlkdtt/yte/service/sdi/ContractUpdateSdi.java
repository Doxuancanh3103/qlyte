package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerAccount;
import vt.qlkdtt.yte.domain.CustomerContract;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContractUpdateSdi {
    private long contractId;
    private String contractNo;
    private String signDate;
    private String representName;
    private String representIdNo;
    private String representTitle;
    private String representTel;
    private String representEmail;

    private long accountId;
    private String billNotificationAddress;
    private String billNotificationMethod;
    private String billAddress;

    private String orderActionTypeId;

    public CustomerContract updateContract(CustomerContract contract) {
        contract.setContractNo(this.getContractNo());
        contract.setSignDate(DateUtil.string2Date(this.getSignDate(), Const.DATE_FORMAT));
        contract.setRepresentName(this.getRepresentName());
        contract.setRepresentIdNo(this.getRepresentIdNo());
        contract.setRepresentTitle(this.getRepresentTitle());
        contract.setRepresentTel(this.getRepresentTel());
        contract.setRepresentEmail(this.getRepresentEmail());
        contract.setUpdateDatetime(new Date());
        contract.setUpdateUser(Const.ADMIN);

        return contract;
    }

    public CustomerAccount updateAccount(CustomerAccount account) {
        account.setBillNotificationAddress(this.getBillNotificationAddress());
        account.setBillNotificationMethod(this.getBillNotificationMethod());
        account.setBillAddress(this.getBillAddress());
        account.setUpdateDatetime(new Date());
        account.setUpdateUser(Const.ADMIN);

        return account;
    }
}
