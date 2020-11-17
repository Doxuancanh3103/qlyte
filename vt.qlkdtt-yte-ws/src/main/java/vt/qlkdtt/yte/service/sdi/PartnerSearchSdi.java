package vt.qlkdtt.yte.service.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerSearchSdi {
    String code;
    String name;
    String idType;
    String idNo;
    String status;
    String fromDate;
    String toDate;
}
