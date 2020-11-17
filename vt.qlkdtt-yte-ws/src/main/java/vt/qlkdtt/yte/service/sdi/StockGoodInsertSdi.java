package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

import java.util.Date;

@Data
public class StockGoodInsertSdi {
    private Long stockId;
    private Long goodCode;
    private String goodName;
    private Long goodQuantity;
    private float goodPrice;
    private Date createDate;
}
