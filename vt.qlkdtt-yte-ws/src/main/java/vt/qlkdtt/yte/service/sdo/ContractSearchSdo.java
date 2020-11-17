package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractSearchSdo {
    private Long contractId;
    private String contractNo;
    private String customerName;
    private String province;
    private String district;
    private String signDate;
    private String status;
    private String representName;
}
