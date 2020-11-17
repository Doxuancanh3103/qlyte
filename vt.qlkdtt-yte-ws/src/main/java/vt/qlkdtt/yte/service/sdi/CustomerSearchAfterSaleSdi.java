package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSearchAfterSaleSdi {
    Long productId;
    String customerName;
    String accountNo;
    String accountServiceNo;
    String customerIdentityNo;

    String productGroup;
    String productType;
    String province;
    String district;
    String customerBusType;
    String medicalNo;
    String tel;
    String fromDate;
    String toDate;
}
