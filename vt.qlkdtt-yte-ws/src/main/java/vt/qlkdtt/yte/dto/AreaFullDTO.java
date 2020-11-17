package vt.qlkdtt.yte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaFullDTO {
    String areaCode;
    String name;
    String fullName;
    String province;
    String district;
    String precinct;
    String parentCode;
    String code;
}
