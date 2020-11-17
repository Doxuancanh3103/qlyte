package vt.qlkdtt.yte.service.sdo;

import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.dto.DocumentDTO;
import vt.qlkdtt.yte.dto.GlobalListValueDTO;
import vt.qlkdtt.yte.dto.PartnerProductFindDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductFindByIdSdo {
    Long productId;
    String productCode;
    String productName;
    Long productGroupId;
    Long productTypeId;
    Long serviceBccsId;
    String productBccsCode;
    String status;
    private String glCode1;
    private String glCode2;
    private String salesItemCode;

    List<GlobalListValueDTO> customerType;
    List<DocumentDTO> documents;
    List<PartnerProductFindDTO> partners;
    List<SalesExpProductFindSdo> saleExps;
}
