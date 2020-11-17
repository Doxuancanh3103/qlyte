package vt.qlkdtt.yte.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DocumentDTO {
    long id;
    String code;
    String name;
    String description;
    Date signDate;
    Date effectDate;
    Date expireDate;
    String fileName;
}
