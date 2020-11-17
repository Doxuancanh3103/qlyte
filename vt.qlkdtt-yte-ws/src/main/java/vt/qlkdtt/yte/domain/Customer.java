package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long customerId;
    @Column(name = "CUSTOMER_TYPE")
    String customerType;
    @Column(name = "CUSTOMER_BUS_TYPE")
    String customerBusType;
    @Column(name = "NAME")
    String name;
    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    Date birthDate;
    @Column(name = "SEX")
    String sex;
    @Column(name = "NATIONALITY")
    String nationality;
    @Column(name = "AREA_CODE")
    String areaCode;
    @Column(name = "PROVINCE")
    String province;
    @Column(name = "DISTRICT")
    String district;
    @Column(name = "PRECINCT")
    String precinct;
    @Column(name = "STREET_BLOCK")
    String streetBlock;
    @Column(name = "STREET_NAME")
    String streetName;
    @Column(name = "HOME")
    String home;
    @Column(name = "ADDRESS")
    String address;
    @Column(name = "TEL")
    String tel;
    @Column(name = "EMAIL")
    String email;
    @Column(name = "STATUS")
    String status;
    @Column(name = "REPRESENT_NAME")
    String representName;
    @Column(name = "REPRESENT_ID_NO")
    String representIdNo;
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
    @Column(name = "TELCO_CUST_ID")
    Long telcoCustId;
}
