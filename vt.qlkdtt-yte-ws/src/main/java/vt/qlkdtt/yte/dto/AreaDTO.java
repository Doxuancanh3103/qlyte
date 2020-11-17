package vt.qlkdtt.yte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTO {
    String areaCode;
    String name;
    String fullName;
    String code;
    String parentCode;
}
