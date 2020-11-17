package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "CUSTOMER_ORDER_CONFIG")
public class CustomerOrderConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ORDER_CONFIG_ID")
    private Long customerOrderConfigId;

    @Column(name = "ORDER_TYPE_ID")
    private String orderTypeId;

    @Column(name = "ACTION_TYPE_ID")
    private String actionTypeId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "LIMIT_TIME")
    private Long limitTime;

    @Column(name = "EXTEND_TIME")
    private Long extendTime;
}
