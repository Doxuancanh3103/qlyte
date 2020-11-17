package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.domain.GlobalParam;

import java.util.Date;

@Data
public class GlobalParamUpdateSdi {
    private Long globalParamId;
    private String type;
    private String code;
    private String name;
    private String value;
    private String description;
    private String productGroupId;
    private Long productId;
    private String status;

    public GlobalParam updateGlobalParam(GlobalParam gp) {
        gp.setType(this.type);
        gp.setCode(this.code);
        gp.setName(this.name);
        gp.setValue(this.getValue());
        gp.setDescription(this.getDescription());
        gp.setProductGroupId(this.getProductGroupId());
        gp.setProductId(this.getProductId());
        gp.setStatus(this.getStatus());
        gp.setUpdateDate(new Date());
        gp.setUpdateUser(Const.ADMIN);

        return gp;
    }
}
