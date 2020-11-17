package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalListInsertSdo {
    long id;
    String code;
    String name;
    String description;
    String createUser;
    String createDatetime;
}
