package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateSdi {
    private long productId;
    private String productCode;
    private String productName;
    private String productGroupId;
    private String productTypeId;
    private Long serviceBccsId;
    private String glCode1;
    private String glCode2;
    private String salesItemCode;

    private List<String> lstCustomerType;
    private List<DocumentUpdateProductSdi> lstDocument;
    private List<PartnerUpdateProductSdi> lstPartner;
    private List<SaleExpensesUpdateProductSdi> lstSalesExpenses;
}
