package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInsertSdi {
    String code;
    String name;
    String description;
    String signDate;
    String effectDate;
    String expireDate;
    String fileName;
    String path;
    String createUser;
    String documentId;

    public Document toDocument() {
        Document doc = new Document();

        if (!DataUtil.isNullOrZero(documentId)) {
            doc.setId(DataUtil.safeToLong(documentId));
        }
        doc.setCode(this.getCode());
        doc.setName(this.getName());
        doc.setDescription(this.getDescription());
        doc.setStatus(Const.STATUS.ACTIVE);
        if (!DataUtil.isStringNullOrEmpty(this.getSignDate())) {
            doc.setSignDate(DateUtil.string2Date(this.getSignDate(), Const.DATE_FORMAT));
        }
        if (!DataUtil.isStringNullOrEmpty(this.getEffectDate())) {
            doc.setEffectDate(DateUtil.string2Date(this.getEffectDate(), Const.DATE_FORMAT));
        }
        if (!DataUtil.isStringNullOrEmpty(this.getExpireDate())) {
            doc.setExpireDate(DateUtil.string2Date(this.getExpireDate(), Const.DATE_FORMAT));
        }
        doc.setFileName(this.getFileName());
        doc.setPath(this.getPath());
        doc.setCreateDatetime(new Date());
        doc.setCreateUser("ADMIN");

        return doc;
    }
}
