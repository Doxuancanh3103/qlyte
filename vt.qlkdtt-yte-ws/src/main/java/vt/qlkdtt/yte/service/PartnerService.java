package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.domain.Partner;
import vt.qlkdtt.yte.service.sdi.PartnerSearchSdi;
import vt.qlkdtt.yte.service.sdo.PartnerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.PartnerSearchSdo;

public interface PartnerService {
    public Page<PartnerSearchSdo> searchPartner(PartnerSearchSdi partnerSearchSdi, Pageable pageable) throws Exception;

    public Partner createPartner(Partner partnerCreate);

    public Partner updatePartner(Partner partnerUpdate);

    public boolean changePartnerStatus(long partnerId, boolean isActive, String user);

    public PartnerFindByIdSdo findById(long partnerId);

    boolean isExist(Long partnerId);
}
