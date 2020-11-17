package vt.qlkdtt.yte.service.sdi;

import lombok.Data;

@Data
public class DocumentDoConnectSdi {
    private Long documentId;
    private String documentName;
    private String documentCode;
    private String documentType;
}
