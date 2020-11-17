package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderSearchSdi {
    private Long productId;
    private String orderType;
    private String orderActionTypeId;
    private String orderContactName;
    private String assigneeCode;
    private String provinceCode;
    private String customerCodeName;
    private Long customerOrderId;
    private String orderFromDate;
    private String orderToDate;
    private String dueFromDate;
    private String dueToDate;
    private String status;
    private String accountNo;
    private String accountServiceNo;
}
