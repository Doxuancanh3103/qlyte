package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CUSTOMER_ORDER")
public class CustomerOrder {
    @Id
    @Column(name = "customer_order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long customerOrderId;
    @Column(name = "customer_id")
    Long customerId;
    @Column(name = "customer_account_id")
    Long customerAccountId;
    @Column(name = "product_id")
    Long productId;
    @Column(name = "subscriber_id")
    Long subscriberId;
    @Column(name = "province")
    String province;
    @Column(name = "order_type")
    String orderType;
    @Column(name = "order_action_type_id")
    String orderActionTypeId;
    @Column(name = "order_contact_name")
    String orderContactName;
    @Column(name = "order_contact_tel")
    String orderContactTel;
    @Column(name = "order_contact_email")
    String orderContactEmail;
    @Column(name = "order_contact_address")
    String orderContactAddress;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_datetime")
    Date orderDatetime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_datetime")
    Date dueDatetime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ext_due_datetime")
    Date extDueDatetime;
    @Column(name = "status")
    String status;
    @Column(name = "assignee_code")
    String assigneeCode;
    @Column(name = "reporter_code")
    String reporterCode;
    @Column(name = "shop_code")
    String shopCode;
    @Column(name = "create_user")
    String createUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    Date createDatetime;
    @Column(name = "update_user")
    String updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_datetime")
    Date updateDatetime;
}
