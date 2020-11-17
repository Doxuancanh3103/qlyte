package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderSearchSdo {
    private Long customerOrderId;
    private String orderType;
    private Long productId;
    private String productName;
    private String productCode;
    private String provinceName;
    private String provinceCode;
    private Long customerAccountId;
    private String accountNo;
    private String accountServiceNo;
    private Long customerId;
    private String customerName;
    private String orderContactName;
    private String orderDate;
    private String status;
    private String asigneeCode;
    private String reporterCode;
    private String dueDate;
    private String extDueDate;
    private String orderActionTypeId;
    private Long limitTime;
    private Long extendTime;
}
