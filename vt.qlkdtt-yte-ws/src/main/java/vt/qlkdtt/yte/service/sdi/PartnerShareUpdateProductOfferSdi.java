package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.domain.DocumentMapping;
import vt.qlkdtt.yte.domain.PartnerRevenueShared;

import java.util.Date;

@Data
public class PartnerShareUpdateProductOfferSdi {
    Long partnerRevenueSharedId;
    Long partnerId;
    Long partnerPercent;
    Long documentMappingId;
    Long documentId;
    String shareType;
    String description;

    public PartnerRevenueShared toPrs(Long productOfferId){
        PartnerRevenueShared prs = new PartnerRevenueShared();

        prs.setProductOfferId(productOfferId);
        prs.setPartnerId(this.getPartnerId());
        prs.setPartnerPercent(this.getPartnerPercent());
        prs.setViettelPercent(100 - this.getPartnerPercent());
        prs.setStatus(Const.STATUS.ACTIVE);
        prs.setShareType(this.getShareType());
        prs.setCreateUser(Const.ADMIN);
        prs.setCreateDatetime(new Date());

        return prs;
    }

    public PartnerRevenueShared updatePrs(PartnerRevenueShared prs){
        prs.setPartnerId(this.getPartnerId());
        prs.setPartnerPercent(this.getPartnerPercent());
        prs.setViettelPercent(100 - this.getPartnerPercent());
        prs.setStatus(Const.STATUS.ACTIVE);
        prs.setShareType(this.getShareType());
        prs.setUpdateUser(Const.ADMIN);
        prs.setUpdateDatetime(new Date());

        return prs;
    }

    public DocumentMapping toDocumentMapping(long partnerRevenueSharedId) {
        DocumentMapping dm = new DocumentMapping();

        dm.setDocumentId(this.getDocumentId());
        dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PARTNER_REVENUE_SHARE);
        dm.setMappingObjectId(partnerRevenueSharedId);
        dm.setStatus(Const.STATUS.ACTIVE);
        dm.setCreateUser("ADMIN");
        dm.setCreateDatetime(new Date());

        return dm;
    }
    public DocumentMapping updateDocumentMapping(DocumentMapping dm) {
        dm.setDocumentId(this.getDocumentId());
        dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PARTNER_REVENUE_SHARE);
        dm.setStatus(Const.STATUS.ACTIVE);
        dm.setUpdateUser("ADMIN");
        dm.setUpdateDatetime(new Date());

        return dm;
    }

}
