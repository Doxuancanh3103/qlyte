package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Product;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInsertSdi {
    private String productCode;
    private String productName;
    private String productGroupId;
    private String productTypeId;
    private Long serviceBccsId;
    private String effectDate;
    private String salesItemCode;
    private String glCode1;
    private String glCode2;

    private List<String> lstCustomerType;
    private List<DocumentInsertProductSdi> lstDocument;
    private List<PartnerInsertProductSdi> lstPartner;
    private List<SaleExpensesInsertProductSdi> lstSalesExpenses;

    public Product toProduct() {
        Product product = new Product();

        product.setProductCode(this.getProductCode());
        product.setProductName(this.getProductName());
        product.setProductGroupId(this.getProductGroupId());
        product.setProductType(this.getProductTypeId());
        product.setTelcoServiceId(this.getServiceBccsId());
        product.setEffectDate(DateUtil.string2Date(this.getEffectDate(), Const.DATE_FORMAT));
        product.setCreateDate(new Date());
        product.setStatus(Const.STATUS.ACTIVE);
        product.setGlCode1(this.getGlCode1());
        product.setGlCode2(this.getGlCode2());
        product.setSalesItemCode(this.getSalesItemCode());

        return product;
    }
}
