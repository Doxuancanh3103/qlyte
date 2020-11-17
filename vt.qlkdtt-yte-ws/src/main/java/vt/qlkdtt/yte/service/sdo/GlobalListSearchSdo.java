package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalListSearchSdo {
    Long id;
    String code;
    String name;
    String value;
    String description;
    Long productId;

    public GlobalListUpdateSdo toGlobalListUpdateSdo() {
        GlobalListUpdateSdo sdo = new GlobalListUpdateSdo();
        sdo.setId(this.getId());
        sdo.setCode(this.getCode());
        sdo.setName(this.getName());
        sdo.setDescription(this.getDescription());
        return sdo;
    }
}
