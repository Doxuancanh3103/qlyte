package vt.qlkdtt.yte.service.sdo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerSearchSdo implements Serializable {
    long partnerId;
    String code;
    String name;
    String tin;
    String idType;
    String idNo;
    String representName;
    String listProductCode;
    String status;
}
