package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.ProductOfferArea;

import java.util.Date;

@Data
public class ProductOfferAreaUpdateProductOffer {
    Long productOfferAreaId;
    String areaCodeProvince;
    String areaCodeDistrict;
    String areaCodePrecinct;
    String status;

    public ProductOfferArea toProductOfferArea(Long productOfferId){
        ProductOfferArea poa = new ProductOfferArea();

        poa.setProductOfferAreaId(this.getProductOfferAreaId());

        String areaCode = null;

        if (!DataUtil.isNullOrEmpty(this.getAreaCodePrecinct())) {
            areaCode = this.getAreaCodePrecinct();
        } else if (!DataUtil.isNullOrEmpty(this.getAreaCodeDistrict())) {
            areaCode = this.getAreaCodeDistrict();
        } else {
            areaCode = this.getAreaCodeProvince();
        }
        poa.setAreaCode(areaCode);
        poa.setStatus(this.getStatus());
        poa.setProductOfferId(productOfferId);
        poa.setCreateUser("ADMIN");
        poa.setCreateDatetime(new Date());

        return poa;
    }

    public ProductOfferArea updateProductOfferArea(ProductOfferArea poa) {
        poa.setAreaCode(DataUtil.isNullOrEmpty(this.getAreaCodeDistrict()) ? this.getAreaCodeProvince() : this.getAreaCodeDistrict());
        poa.setStatus(this.getStatus());
        poa.setUpdateUser("ADMIN");
        poa.setUpdateDatetime(new Date());

        return poa;
    }
}
