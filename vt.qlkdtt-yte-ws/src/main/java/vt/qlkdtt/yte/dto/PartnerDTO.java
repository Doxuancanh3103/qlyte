package vt.qlkdtt.yte.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PartnerDTO {
    long partnerId;
    String partnerCode;
    String name;
    String tin;
    String province;
    String district;
    String precinct;
    String areaCode;
    String streetBlock;
    String street;
    String home;
    String address;
    String tel;
    String email;
    String bankCode;
    String branchBankCode;
    String description;
    String status;
    Date createDate;
    String createUser;
    Date lastUpDateDate;
    String lastUpDateUser;
    String pop;
    String idNo;
    String representativeName;
    String representativeTitle;
    String representativeIdNo;
    String representativeIdType;
    Date representativeIssueDate;
    String representativeIssuePlace;
    String representativeTel;
    String representativeEmail;
    Date representativeExpireDate;
    String partnerType;
    String accountNo;
    String accountOwner;
    String listProductCode;

}
