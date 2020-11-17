package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "SUBSCRIBER_EXT_MEDICAL")
public class SubscriberExtMedical {
    @Id
    @Column(name = "SUBSCRIBER_ID")
    private Long subscriberId;

    @Column(name = "MEDICAL_NO")
    private String medicalNo;

    @Column(name = "MEDICAL_PERMISSION_NO")
    private String medicalPermissionNo;

    @Column(name = "MEDICAL_PERMISSION_DATE")
    private Date medicalPermissionDate;

    @Column(name = "MEDICAL_ORG_TYPE")
    private String medicalOrgType;

    @Column(name = "PRODUCT_CUST_SEGM")
    private String productCustSegm;

    @Column(name = "CUSTOMER_IDENTITY_ID")
    private String customerIdentityId;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATETIME")
    private Date createDatetime;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;
}
