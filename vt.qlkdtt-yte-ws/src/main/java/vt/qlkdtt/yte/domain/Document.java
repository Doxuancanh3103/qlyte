package vt.qlkdtt.yte.domain;

import lombok.Data;
import vt.qlkdtt.yte.service.sdo.DocumentInsertSdo;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "DOCUMENT")
public class Document {
    @Id
    @Column(name = "document_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "type")
    String type;

    @Column(name = "code")
    String code;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    String status;

    @Column(name = "path")
    String path;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "create_user")
    String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    Date createDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sign_date")
    Date signDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effect_date")
    Date effectDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expire_date")
    Date expireDate;

    @Column(name = "partner_id")
    Long partnerId;

    @Column(name = "partner_revenue_share_id")
    Long partnerRevenueShareId;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "product_offer_id")
    Long productOfferId;

    @Column(name = "update_user")
    String updateUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_datetime")
    Date updateDatetime;

    @Column(name = "source")
    String source;

    @Column(name = "source_ref_id")
    String sourceRefId;

    public DocumentInsertSdo toDocumentInsertSdo() {
        DocumentInsertSdo sdo = new DocumentInsertSdo();

        sdo.setId(this.getId());
        sdo.setCode(this.getCode());
        sdo.setName(this.getName());

        return sdo;
    }
}
