package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "DOCUMENT_DETAIL")
public class DocumentDetail {
    @Id
    @Column(name = "DOCUMENT_DETAIL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentDetailId;

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
}
