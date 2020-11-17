package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

import java.util.Date;

@Data
public class GlobalListValueSdo {
    Long id;
    String code;
    String value;
    String name;
    String description;
    String staDate;
    String endDate;
    String status;
    Long telcoId;
    String telcoCode;
    String telcoValue;
    String createUser;
    String createDatetime;
    String updateUser;
    String updateDatetime;
    Long productGroupId;
    Long productId;
    Long globalListId;
    Long displayOrder;
    String productName;
    String productGroupName;
}
