package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Document;

import java.util.Date;

@Data
public class DocumentInsertProductSdi {
    private Long documentId;
    private String code;
    private String name;
    private String signDate;
    private String effectDate;

    public Document toDocument() {
        Document document = new Document();

        if (!DataUtil.isNullOrZero(documentId)) {
            document.setId(documentId);
        }
        document.setCode(code);
        document.setName(name);

        if (!DataUtil.isNullOrEmpty(this.getSignDate())) {
            document.setSignDate(DateUtil.string2Date(this.getSignDate(), Const.DATE_FORMAT));
        }

        if (!DataUtil.isNullOrEmpty(this.getEffectDate())) {
            document.setEffectDate(DateUtil.string2Date(this.getEffectDate(), Const.DATE_FORMAT));
        }


        document.setCreateUser("ADMIN");
        document.setCreateDatetime(new Date());
        document.setStatus(Const.STATUS.ACTIVE);

        return document;
    }
}
