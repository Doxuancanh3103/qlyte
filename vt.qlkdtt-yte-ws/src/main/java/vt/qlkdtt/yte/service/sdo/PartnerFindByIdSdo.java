package vt.qlkdtt.yte.service.sdo;

import lombok.Data;
import lombok.NoArgsConstructor;
import vt.qlkdtt.yte.dto.RevenueSharedDTO;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PartnerFindByIdSdo implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;

    String address;
    String email;
    String fax;
    String status;

    // validate
    long partnerId;
    String partnerCode;
    String name;
    String tin;
    String province;
    String district;
    String precinct;
    String tel;

    String representName;
    String representTitle;
    String representIdNo;
    String representTel;
    String representEmail;
    String representIdType;

    List<RevenueSharedDTO> lstRevenueShared;
    List<RevenueSharedDTO> lstRevenueSharedSpecial;

}
