package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

import java.util.Date;

@Data
public class StockUpdateSdi {
    private Long stockId;
    private String stockName;
    private Date creatDate;
}
