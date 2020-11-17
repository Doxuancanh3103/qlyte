package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "STOCK_GOOD")
public class StockGood {
    @Column(name = "STOCK_ID")
    Long stockId;

    @Id
    @Column(name = "GOOD_CODE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long goodCode;

    @Column(name = "GOOD_NAME")
    String goodName;

    @Column(name = "GOOD_QUANTITY")
    Long goodQuantity;

    @Column(name = "GOOD_PRICE")
    float goodPrice;

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;
}
