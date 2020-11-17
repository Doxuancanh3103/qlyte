package vt.qlkdtt.yte.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInsertSdo {
    long id;
    String code;
    String name;
}
