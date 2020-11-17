package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

@Data
public class StockGoodTransferSdi {
    private Long fromStockId;
    private Long fromGoodCode;
    private Long toStockId;
}
