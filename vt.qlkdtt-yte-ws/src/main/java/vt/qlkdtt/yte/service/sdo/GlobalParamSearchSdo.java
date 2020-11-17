package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalParamSearchSdo {
    private Long globalParamId;
    private String type;
    private String code;
    private String name;
    private String value;
    private String description;
    private String productGroupId;
    private Long productId;
    private String status;
}
