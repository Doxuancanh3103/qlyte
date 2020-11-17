package vt.qlkdtt.yte.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StockGoodDTO {
    Long stockId;
    Long goodCode;
    String goodName;
    Long goodQuantity;
    float goodPrice;
    Date createDate;
}
