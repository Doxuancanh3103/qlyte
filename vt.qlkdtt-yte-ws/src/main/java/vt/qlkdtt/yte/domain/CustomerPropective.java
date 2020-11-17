package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CUSTOMER_PROPECTIVE")
public class CustomerPropective {
    @Id
    @Column(name = "PROPECTIVE_CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long propectiveCustomerId;

    @Column(name = "CUSTOMER_PROPECTIVE_CODE")
    private String customerPropectiveCode;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_OFFER_ID")
    private Long productOfferId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "IDENTITY_TYPE")
    private String identityType;

    @Column(name = "IDENTITY_NO")
    private String identityNo;

    @Column(name = "MEDICAL_NO")
    private String medicalNo;

    @Column(name = "MEDICAL_PERMISSION_NO")
    private String medicalPermissionNo;

    @Column(name = "MEDICAL_PERMISSION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date medicalPermissionDate;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "PRECINCT")
    private String precinct;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "STA_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date staDatetime;

    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Column(name = "EXT_DUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date extDueDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDatetime;

    @Column(name = "PROVISIONING_STATUS")
    private String provisionningStatus;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CUSTOMER_BUS_TYPE")
    private String customerBusType;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;

    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REPRESENT_NAME")
    private String representName;

    @Column(name = "REPRESENT_ID_TYPE")
    private String representIdType;

    @Column(name = "REPRESENT_ID_NO")
    private String representIdNo;

    @Column(name = "REPRESENT_TITLE")
    private String representTitle;

    @Column(name = "REPRESENT_TEL")
    private String representTel;

    @Column(name = "REPRESENT_EMAIL")
    private String representEmail;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "ACCOUNT_SERVICE_NO")
    private String accountServiceNo;

    @Column(name = "SALES_MAN_CODE")
    private String salesManCode;

    @Column(name = "BROKERS_PARTNER_CODE")
    private String brokersPartnerCode;

    @Column(name = "MEDICAL_ORG_TYPE")
    private String medicalOrgType;
}
