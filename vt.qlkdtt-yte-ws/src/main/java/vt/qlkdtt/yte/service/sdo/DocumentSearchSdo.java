package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSearchSdo {
    private long documentId;
    private String code;
    private String name;
    private String description;
    private String signDate;
    private String effectDate;
    private String expireDate;
    private String status;
    private String path;
    private String fileName;
}
