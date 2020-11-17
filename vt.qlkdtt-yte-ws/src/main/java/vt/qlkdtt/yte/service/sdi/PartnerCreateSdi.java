package vt.qlkdtt.yte.service.sdi;

import lombok.Builder;
import lombok.Data;
import vt.qlkdtt.yte.domain.Partner;

import java.util.Date;

@Data
@Builder
public class PartnerCreateSdi {
    String address;
    String email;
    String fax;

    // validate
    String partnerCode;
    String name;
    String tin;
    String province;
    String district;
    String precinct;
    String tel;

    String representName;
    String representTitle;
    String representIdType;
    String representIdNo;
    String representTel;
    String representEmail;

    public Partner toPartner() {
        Partner partner = new Partner();

        partner.setPartnerCode(this.partnerCode);
        partner.setName(this.name);
        partner.setTin(this.tin);
        partner.setProvince(this.province);
        partner.setDistrict(this.district);
        partner.setPrecinct(this.precinct);
        partner.setAddress(this.address);
        partner.setTel(this.tel);
        partner.setEmail(this.email);
        partner.setCreateUser("admin");
        partner.setCreateDate(new Date());
        partner.setLastUpDateUser("admin");
        partner.setLastUpDateDate(new Date());
        partner.setRepresentativeName(this.representName);
        partner.setRepresentativeTitle(this.representTitle);
        partner.setRepresentativeIdType(this.representIdType);
        partner.setRepresentativeIdNo(this.representIdNo);
        partner.setRepresentativeTel(this.representTel);
        partner.setRepresentativeEmail(this.representEmail);
        partner.setFax(this.fax);
        partner.setStatus("1");

        return partner;
    }

}
