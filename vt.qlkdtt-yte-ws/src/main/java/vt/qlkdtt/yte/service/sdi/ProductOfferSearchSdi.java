package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
public class ProductOfferSearchSdi {
    Long productGroupId;
    String productNameCode;
    String status;
    String provinceCode;
    String districtCode;
    String fromDate;
    String toDate;
}
