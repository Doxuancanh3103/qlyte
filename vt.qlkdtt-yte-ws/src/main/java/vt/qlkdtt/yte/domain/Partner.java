package vt.qlkdtt.yte.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PARTNER")
public class Partner {
    @Id
    @Column(name = "partner_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long partnerId;
    @Column(name = "code")
    String partnerCode;
    @Column(name = "name")
    String name;
    @Column(name = "tin")
    String tin;
    @Column(name = "province")
    String province;
    @Column(name = "district")
    String district;
    @Column(name = "precinct")
    String precinct;
    @Column(name = "area_code")
    String areaCode;
    @Column(name = "street_block")
    String streetBlock;
    @Column(name = "street_name")
    String street;
    @Column(name = "home")
    String home;
    @Column(name = "address")
    String address;
    @Column(name = "tel")
    String tel;
    @Column(name = "email")
    String email;
    @Column(name = "bank_code")
    String bankCode;
    @Column(name = "branch_bank_code")
    String branchBankCode;
    @Column(name = "description")
    String description;
    @Column(name = "status")
    String status;
    @Column(name = "create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    Date createDate;
    @Column(name = "create_user")
    @CreatedBy
    String createUser;
    @Column(name = "update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    Date lastUpDateDate;
    @Column(name = "update_user")
    @LastModifiedBy
    String lastUpDateUser;
    @Column(name = "represent_name")
    String representativeName;
    @Column(name = "represent_title")
    String representativeTitle;
    @Column(name = "represent_id_no")
    String representativeIdNo;
    @Column(name = "represent_id_type")
    String representativeIdType;
    @Column(name = "represent_issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date representativeIssueDate;
    @Column(name = "represent_issue_place")
    String representativeIssuePlace;
    @Column(name = "represent_tel")
    String representativeTel;
    @Column(name = "represent_email")
    String representativeEmail;
    @Column(name = "represent_expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date representativeExpireDate;
    @Column(name = "partner_type")
    String partnerType;
    @Column(name = "fax")
    String fax;
    @Transient
    String listProductCode;
}
