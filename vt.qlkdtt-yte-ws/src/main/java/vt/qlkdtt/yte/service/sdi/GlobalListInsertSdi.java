package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.domain.GlobalList;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalListInsertSdi {
    private String code; // Required
    private String name;
    private String description;
    private String status; // Required
    private String createUser; // Required

    public GlobalList toOptionSet() {
        GlobalList globalList = new GlobalList();

        globalList.setName(this.getName());
        globalList.setCode(this.getCode());
        globalList.setDescription(this.getDescription());
        globalList.setCreateUser(this.getCreateUser());
        globalList.setCreateDatetime(new Date());
        globalList.setStatus(Const.STATUS.ACTIVE);

        return globalList;
    }
}
