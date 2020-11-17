package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.domain.GlobalParam;

import java.util.Date;

@Data
public class GlobalParamInsertSdi {
    private String type;
    private String code;
    private String name;
    private String value;
    private String description;
    private String productGroupId;
    private Long productId;
    private String status;

    public GlobalParam toGlobalParam() {
        GlobalParam result = new GlobalParam();

        result.setType(this.type);
        result.setCode(this.code);
        result.setName(this.name);
        result.setValue(this.getValue());
        result.setDescription(this.getDescription());
        result.setProductGroupId(this.getProductGroupId());
        result.setProductId(this.getProductId());
        result.setStatus(this.getStatus());
        result.setCreateDate(new Date());
        result.setCreateUser(Const.ADMIN);

        return result;
    }
}
