package vt.qlkdtt.yte.service.sdi;

import lombok.Data;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Customer;
import vt.qlkdtt.yte.domain.CustomerExtMedical;
import vt.qlkdtt.yte.domain.CustomerIdentity;

import java.util.Date;

@Data
public class CustomerInfoDoConnectSdi {
    private Long customerPropectiveId;
    private String customerPropectiveCode;
    private Long customerId;
    private String customerBusType;
    private String customerName;
    private String customerIdentityType;
    private String customerIdentityNo;
    private String birthDate;
    private String areaCode; //precinct area code
    private String address;
    private String tel;
    private String email;
    private String province;

    //<editor-fold desc="To customer">
    public Customer toCustomer(String provinceCode, String districtCode, String precinctCode, ContractInfoDoConnectSdi contractInfo) {
        Customer c = new Customer();

        c.setCustomerBusType(this.getCustomerBusType());
        c.setName(this.getCustomerName());

        if (!DataUtil.isStringNullOrEmpty(this.getBirthDate())) {
            c.setBirthDate(DateUtil.string2Date(this.getBirthDate(), Const.DATE_FORMAT));
        }

        c.setAreaCode(this.getAreaCode());
        c.setProvince(provinceCode);
        c.setDistrict(districtCode);
        c.setPrecinct(precinctCode);
        c.setAddress(this.getAddress());
        c.setTel(this.getTel());
        c.setEmail(this.getEmail());
        c.setRepresentName(contractInfo.getRepresentName());
        c.setRepresentIdNo(contractInfo.getRepresentIdNo());
        c.setRepresentTitle(contractInfo.getRepresentTitle());
        c.setRepresentTel(contractInfo.getRepresentTel());
        c.setRepresentEmail(contractInfo.getRepresentEmail());
        c.setCreateUser("ADMIN");
        c.setCreateDatetime(new Date());

        return c;
    }
    //</editor-fold>

    public CustomerIdentity toCustomerIdentity(Long customerId) {
        CustomerIdentity ci = new CustomerIdentity();

        ci.setCustomerId(customerId);
        ci.setIdType(this.getCustomerIdentityType());
        ci.setIdNo(this.getCustomerIdentityNo());
        ci.setCreateUser("ADMIN");
        ci.setCreateDatetime(new Date());

        return ci;
    }

    public CustomerExtMedical toCustomerExtMedical(Long customerId, Long customerIdentityId) {
        CustomerExtMedical cem = new CustomerExtMedical();

        cem.setCustomerId(customerId);
        cem.setCustomerIdentityId(customerIdentityId);
        cem.setMedicalIdentityNo(this.getCustomerIdentityNo());

        return cem;
    }
}
