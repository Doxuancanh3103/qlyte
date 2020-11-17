package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUpdateSdi {
    Long documentId;
    String code;
    String name;
    String description;
    String signDate;
    String effectDate;
    String expireDate;

    public Document updateDocument(Document doc) {
        doc.setCode(this.getCode());
        doc.setName(this.getName());
        doc.setDescription(this.getDescription());
        doc.setStatus(Const.STATUS.ACTIVE);
        doc.setSignDate(DateUtil.string2Date(this.getSignDate(), Const.DATE_FORMAT));
        doc.setEffectDate(DateUtil.string2Date(this.getEffectDate(), Const.DATE_FORMAT));
        doc.setExpireDate(DateUtil.string2Date(this.getExpireDate(), Const.DATE_FORMAT));
        doc.setUpdateDatetime(new Date());
        doc.setUpdateUser("ADMIN");

        return doc;
    }
}
