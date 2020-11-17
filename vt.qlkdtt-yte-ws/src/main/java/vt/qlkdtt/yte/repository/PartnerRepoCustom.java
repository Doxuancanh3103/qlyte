package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.domain.Partner;
import vt.qlkdtt.yte.service.sdi.PartnerSearchSdi;
import vt.qlkdtt.yte.service.sdo.PartnerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.PartnerSearchSdo;

import java.util.List;

public interface PartnerRepoCustom {
    List<Partner> searchPartnerFull(Partner partner);

    boolean isExist(String partnerCode, long partnerId);

    boolean isExist(String partnerCode);

    boolean isExistTin(String tin);

    boolean isExistTin(String tin, Long partnerId);

    PartnerFindByIdSdo findById(long partnerId);

    Page<PartnerSearchSdo> searchPartnerPage(PartnerSearchSdi partner, Pageable page);

    boolean isExist(Long partnerId);
}
