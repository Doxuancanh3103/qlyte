package vt.qlkdtt.yte.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "DOCUMENT_MAPPING")
public class DocumentMapping {
    @Id
    @Column(name = "DOCUMENT_MAPPING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentMappingId;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Column(name = "OBJECT_TYPE")
    private String objectType;

    @Column(name = "MAPPING_OBJECT_ID")
    private Long mappingObjectId;

    @Column(name = "STATUS")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATETIME")
    private Date createDatetime;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;

    @Column(name = "UPDATE_USER")
    private String updateUser;
}
