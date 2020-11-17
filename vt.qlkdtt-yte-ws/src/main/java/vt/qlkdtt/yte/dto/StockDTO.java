package vt.qlkdtt.yte.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StockDTO {
    private Long stockId;
    private String stockName;
    private Date creatDate;
}
