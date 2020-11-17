package vt.qlkdtt.yte.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerTypeDTO {
    long id;
    String code;
    String name;
    String description;
}
