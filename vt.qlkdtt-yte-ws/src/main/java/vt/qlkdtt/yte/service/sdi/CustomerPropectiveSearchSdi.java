package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPropectiveSearchSdi {
    String customerBusType;
    String customerName;
    String identityNo;
    String areaCodeProvince;
    String areaCodeDistrict;
    String areaCodePrecinct;
    String accountNo;
    String accountServiceNo;
    String status;
}
