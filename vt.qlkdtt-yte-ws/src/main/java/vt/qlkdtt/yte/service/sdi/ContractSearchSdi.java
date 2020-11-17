package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractSearchSdi {
    private Long productGroupId;
    private Long productId;
    private String province;
    private String district;
    private String fromDate;
    private String toDate;
    private String contractNo;
    private String customerName;
    private String accountNo;
    private String accountServiceNo;
    private Long customerId;
}
