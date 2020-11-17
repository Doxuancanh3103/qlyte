package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DocumentSdo {
    private long documentId;
    private String documentType;
    private String documentTypeName;
    private String documentName;
}