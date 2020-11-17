package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalListValueInsertSdi {
    private Long globalListId;
    private String name;
    private String description;
    private String value;
    private String createUser;
    private String status;
    private String staDate;
    private String endDate;
    private Long displayOrder;
    private Long telcoId;
    private Long productId;
    private Long productGroupId;
    private String telcoCode;
    private String telcoValue;
}
