package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalListValueUpdateSdi {
    private Long id;
    private Long globalListId;
    private String name;
    private String description;
    private String value;
    private String updateUser;
    private String status;
    private String staDate;
    private String endDate;
    private Long telcoId;
    private String telcoCode;
    private String telcoValue;
    private Long productId;
    private Long productGroupId;
    private Long displayOrder;
}
