package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalListValueSdi {
    private Long id = null;
    private String code = null;
    private String value;
    private String name = null;
    private String description = null;
    private String staDate = null;
    private String endDate = null;
    private String status = "1";
    private Long telcoId = null;
    private String telcoCode = null;
    private String telcoValue = null;
    private String createUser;
    private Date createDatetime = null;
    private String updateUser = null;
    private Date updateDatetime = null;
    private Long productGroupId = null;
    private Long productId = null;
    private Long globalListId;
    private Long displayOrder;
}
