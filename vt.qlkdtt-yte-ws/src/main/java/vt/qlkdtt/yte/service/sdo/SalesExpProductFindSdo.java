package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class SalesExpProductFindSdo {
    private Long productSaleExpId;
    private String saleChanel;
    private Long rateSalesMan;
    private Long rateBroker;
    private String staDate;
    private String endDate;
    private String status;
}
