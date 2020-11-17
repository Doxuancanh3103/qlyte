package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProductSearchSdi {
    private String productGroupId;
    private String productNameCode;
    private String status;
    private String fromDate;
    private String toDate;
}
