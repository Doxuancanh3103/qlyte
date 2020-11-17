package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUpdateProductSdi {
    long documentId;
    String code;
    String name;
    String signDate;
    String effectDate;
}
