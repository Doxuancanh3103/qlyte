package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CUSTOMER_IDENTITY")
public class CustomerIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_IDENTITY_ID")
    private Long customerIdentityId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "ID_TYPE")
    private String idType;

    @Column(name = "ID_NO")
    private String idNo;

    @Column(name = "ID_ISSUE_PLACE")
    private String idIssuePlace;

    @Column(name = "ID_ISSUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date idIssueDate;

    @Column(name = "ID_EXPIRE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date idExpireDate;

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
}
