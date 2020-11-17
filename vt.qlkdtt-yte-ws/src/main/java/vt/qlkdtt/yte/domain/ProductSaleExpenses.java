package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PRODUCT_SALE_EXPENSES")
public class ProductSaleExpenses {
    @Id
    @Column(name = "PRODUCT_SALE_EXP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productSaleExpId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "SALE_CHANNEL")
    private String saleChannel;

    @Column(name = "RATE_SALESMAN")
    private Long rateSalesman;

    @Column(name = "RATE_BROKER")
    private Long rateBroker;

    @Column(name = "STA_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date staDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "STATUS")
    private String status;
}
