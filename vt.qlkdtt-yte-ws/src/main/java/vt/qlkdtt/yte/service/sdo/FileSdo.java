package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSdo {
    long documentId;
    Long documentDetailId;
    String fileName;
}
