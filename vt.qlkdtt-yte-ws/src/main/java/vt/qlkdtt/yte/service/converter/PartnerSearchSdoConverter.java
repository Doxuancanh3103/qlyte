package vt.qlkdtt.yte.service.converter;

import vt.qlkdtt.yte.domain.Partner;
import vt.qlkdtt.yte.service.sdo.PartnerSearchSdo;

import java.util.function.Function;

public class PartnerSearchSdoConverter implements Function<Partner, PartnerSearchSdo> {
    @Override
    public PartnerSearchSdo apply(Partner partner) {
        return PartnerSearchSdo.builder()
                .partnerId(partner.getPartnerId())
                .code(partner.getPartnerCode())
                .name(partner.getName())
                .tin(partner.getTin())
                .idType(partner.getRepresentativeIdType())
                .idNo(partner.getRepresentativeIdNo())
                .representName(partner.getRepresentativeName())
                .status(partner.getStatus())
                .listProductCode(partner.getListProductCode())
                .build();
    }
}
