package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerPropective;

import java.util.Date;

@Data
public class CustomerPropectUpdateProductOffer {
    Long customerPropectiveId;
    String provinceCode;
    String customerBusType;
    String districtCode;
    String customerName;
    String customerIdentityType;
    String customerIdentityNo;
    String medicalNo;
    String staDate;
    String dueDate;
    String status;

    public CustomerPropective toCustomerPropective(Long productOfferId, Long productId) {
        CustomerPropective cp = new CustomerPropective();

        cp.setProductOfferId(productOfferId);
        cp.setProductId(productId);
        cp.setCustomerBusType(this.getCustomerBusType());
        cp.setIdentityType(this.getCustomerIdentityType());
        cp.setIdentityNo(this.getCustomerIdentityNo());
        cp.setProvince(this.getProvinceCode());
        cp.setDistrict(this.getDistrictCode());
        cp.setCustomerName(this.getCustomerName());
        cp.setMedicalNo(this.getMedicalNo());

        if (!DataUtil.isNullOrEmpty(this.getStaDate())) {
            cp.setStaDatetime(DateUtil.string2Date(this.getStaDate(), Const.DATE_FORMAT));
        }

        if (!DataUtil.isNullOrEmpty(this.getDueDate())) {
            cp.setDueDate(DateUtil.string2Date(this.getDueDate(), Const.DATE_FORMAT));
        }

        cp.setStatus(this.getStatus());
        cp.setCreateUser("ADMIN");
        cp.setCreateDatetime(new Date());

        return cp;
    }

    public CustomerPropective updateCustomerPropective(CustomerPropective cp, Long productId) {
        cp.setIdentityType(this.getCustomerIdentityType());
        cp.setIdentityNo(this.getCustomerIdentityNo());
        cp.setProvince(this.getProvinceCode());
        cp.setDistrict(this.getDistrictCode());
        cp.setCustomerName(this.getCustomerName());
        cp.setProductId(productId);

        if (!DataUtil.isNullOrEmpty(this.getStaDate())) {
            cp.setStaDatetime(DateUtil.string2Date(this.getStaDate(), Const.DATE_FORMAT));
        }
        if (!DataUtil.isNullOrEmpty(this.getDueDate())) {
            cp.setDueDate(DateUtil.string2Date(this.getDueDate(), Const.DATE_FORMAT));
        }

        cp.setStatus(this.getStatus());
        cp.setUpdateUser("ADMIN");
        cp.setUpdateDatetime(new Date());

        return cp;
    }
}
