package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.ProductSaleExpenses;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleExpensesUpdateProductSdi {
    private Long productSaleExpId;
    private Long productId;
    private String saleChanel;
    private Long rateSalesMan;
    private Long rateBroker;
    private String staDate;
    private String endDate;
    private String status;

    public ProductSaleExpenses updateProductSaleExp(ProductSaleExpenses pse, Long productId) {
        pse.setSaleChannel(this.getSaleChanel());
        pse.setRateSalesman(this.getRateSalesMan());
        pse.setRateBroker(this.getRateBroker());
        if (!DataUtil.isNullOrEmpty(this.getStaDate())) pse.setStaDate(DateUtil.string2Date(this.getStaDate(), Const.DATE_FORMAT));
        if (!DataUtil.isNullOrEmpty(this.getEndDate())) pse.setEndDate(DateUtil.string2Date(this.getEndDate(), Const.DATE_FORMAT));
        pse.setUpdateUser(Const.ADMIN);
        pse.setUpdateDate(new Date());
        pse.setStatus(this.getStatus());

        return pse;
    }

    public ProductSaleExpenses toProductSaleExp(Long productId){
        ProductSaleExpenses pse = new ProductSaleExpenses();

        pse.setProductId(productId);
        pse.setSaleChannel(this.getSaleChanel());
        pse.setRateSalesman(this.getRateSalesMan());
        pse.setRateBroker(this.getRateBroker());
        if (!DataUtil.isNullOrEmpty(this.getStaDate())) pse.setStaDate(DateUtil.string2Date(this.getStaDate(), Const.DATE_FORMAT));
        if (!DataUtil.isNullOrEmpty(this.getEndDate())) pse.setEndDate(DateUtil.string2Date(this.getEndDate(), Const.DATE_FORMAT));
        pse.setCreateUser(Const.ADMIN);
        pse.setCreateDate(new Date());
        pse.setStatus(this.getStatus());

        return pse;
    }
}
