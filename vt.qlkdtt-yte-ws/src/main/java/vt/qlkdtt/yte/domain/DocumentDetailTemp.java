package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "DOCUMENT_DETAIL_TEMP")
public class DocumentDetailTemp {
    @Id
    @Column(name = "DOCUMENT_DETAIL_TEMP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentDetailTempId;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Column(name = "PATH")
    private String path;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    public DocumentDetail toDocumentDetail(){
        DocumentDetail documentDetail = new DocumentDetail();

        documentDetail.setDocumentId(this.getDocumentId());
        documentDetail.setPath(this.getPath());
        documentDetail.setFileName(this.getFileName());
        documentDetail.setCreateUser(this.getCreateUser());
        documentDetail.setCreateDatetime(this.getCreateDatetime());

        return documentDetail;
    }
}
