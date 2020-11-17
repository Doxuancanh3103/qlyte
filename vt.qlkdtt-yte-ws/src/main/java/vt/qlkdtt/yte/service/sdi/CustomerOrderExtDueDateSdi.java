package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderExtDueDateSdi {
    private long customerOrderId;
    private String extendDueDate;
    private String reason;
}
