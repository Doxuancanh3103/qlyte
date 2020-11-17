package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CUSTOMER_CONTRACT")
public class CustomerContract {
    @Id
    @Column(name = "CONTRACT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long contractId;
    @Column(name = "CUSTOMER_ACCOUNT_ID")
    Long customerAccountId;

    @Column(name = "CUSTOMER_ID")
    Long customerId;

    @Column(name = "PRODUCT_OFFER_ID")
    Long productOfferId;

    @Column(name = "PARTNER_ID")
    Long partnerId;

    @Column(name = "CONTRACT_NO")
    String contractNo;

    @Column(name = "SIGN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    Date signDate;

    @Column(name = "EFFECT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    Date effectDate;

    @Column(name = "DESCRIPTION")
    String description;

    @Column(name = "STATUS")
    String status;

    @Column(name = "REPRESENT_NAME")
    String representName;

    @Column(name = "REPRESENT_ID_NO")
    String representIdNo;

    @Column(name = "REPRESENT_ID_TYPE")
    String representIdType;

    @Column(name = "REPRESENT_TITLE")
    String representTitle;

    @Column(name = "REPRESENT_TEL")
    String representTel;

    @Column(name = "REPRESENT_EMAIL")
    String representEmail;

    @Column(name = "CREATE_USER")
    String createUser;

    @Column(name = "CREATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDatetime;

    @Column(name = "UPDATE_USER")
    String updateUser;

    @Column(name = "UPDATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    Date updateDatetime;

    @Column(name = "TELCO_CONTRACT_ID")
    Long telcoContractId;

    @Column(name = "TELCO_CUSTOMER_ID")
    Long telcoCustomerId;

    @Column(name = "EXPIRE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    Date expireDate;

}
