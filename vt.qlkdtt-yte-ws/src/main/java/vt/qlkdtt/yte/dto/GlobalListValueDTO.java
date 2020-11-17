package vt.qlkdtt.yte.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GlobalListValueDTO {
    Long id;
    String value;
    String name;
    String description;
    Long productCustSegmId;
}
