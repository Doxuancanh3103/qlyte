package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

@Data
public class DocumentCustomerFindByIdSdo {
    private Long documentId;
    private String documentType;
    private String documentName;
}
