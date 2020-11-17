package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CUSTOMER_ACCOUNT")
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "ACCOUNT_SERVICE_NO")
    private String accountServiceNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FIRST_SIGN_DATE")
    private Date firstSignDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECT_DATE")
    private Date effectDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "BILL_CYCLE_ID")
    private Long billCycleId;

    @Column(name = "BILL_NOTIFICATION_METHOD")
    private String billNotificationMethod;

    @Column(name = "BILL_NOTIFICATION_ADDRESS")
    private String billNotificationAddress;

    @Column(name = "BILL_AREA_CODE")
    private String billAreaCode;

    @Column(name = "BILL_PROVINCE")
    private String billProvince;

    @Column(name = "BILL_DISTRICT")
    private String billDistrict;

    @Column(name = "BILL_PRECINCT")
    private String billPrecinct;

    @Column(name = "BILL_STREET_BLOCK")
    private String billStreetBlock;

    @Column(name = "BILL_STREET_NAME")
    private String billStreetName;

    @Column(name = "BILL_HOME")
    private String billHome;

    @Column(name = "BILL_ADDRESS")
    private String billAddress;

    @Column(name = "IS_DEPOSIT")
    private String isDeposit;

    @Column(name = "BALANCE_CORE")
    private Long balanceCore;

    @Column(name = "BALANCE_DEPOSIT")
    private Long balanceDeposit;

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
