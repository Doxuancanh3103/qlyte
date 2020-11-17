package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "STOCK")
public class Stock {
    @Id
    @Column(name = "STOCK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long stockId;
    @Column(name = "STOCK_NAME")
    String stockName;
    @Column(name = "CREAT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    Date creatDate;
}
