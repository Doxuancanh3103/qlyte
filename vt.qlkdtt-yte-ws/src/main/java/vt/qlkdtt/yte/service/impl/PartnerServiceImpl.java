package vt.qlkdtt.yte.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.Partner;
import vt.qlkdtt.yte.repository.PartnerRepo;
import vt.qlkdtt.yte.service.PartnerService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.PartnerSearchSdi;
import vt.qlkdtt.yte.service.sdo.PartnerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.PartnerSearchSdo;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {
    @Autowired
    PartnerRepo partnerRepo;

    @Override
    @Transactional
    public Page<PartnerSearchSdo> searchPartner(PartnerSearchSdi partnerSearchSdi, Pageable pageable) {
        return partnerRepo.searchPartnerPage(partnerSearchSdi, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Partner createPartner(Partner partnerCreate) {
        // validate
        validateCreateUpdate(partnerCreate);

        if (partnerRepo.isExist(partnerCreate.getPartnerCode(), 0L)) {
            // Code da ton tai
            throw new AppException("API-PA012", "Ma partnerCode da ton tai");
        }

        // create
        Partner partner = partnerRepo.save(partnerCreate);

        return partner;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Partner updatePartner(Partner partnerUpdate) {
        // validate
        validateCreateUpdate(partnerUpdate);

        Partner partner2Update = new Partner();
        partner2Update.setPartnerId(partnerUpdate.getPartnerId());

        List<Partner> list = partnerRepo.searchPartnerFull(partner2Update);
        if (list != null && !list.isEmpty()) {
            partner2Update = list.get(0);
        } else {
            throw new AppException("API-PA019", "Id khong ton tai");
            // Id khong ton tai
        }

        if (partnerRepo.isExist(partnerUpdate.getPartnerCode(), partnerUpdate.getPartnerId())) {
            throw new AppException("API-PA020", "Code moi da ton tai tren he thong");
            // Code moi da ton tai
        }

        // update
        Partner partner = partnerRepo.save(toPartnerUpdate(partnerUpdate, partner2Update));

        return partner;
    }

    public void validateCreateUpdate(Partner partnerCreate) {
        if (partnerCreate == null) {
            throw new AppException("API-PA013", "Bat buoc truyen du lieu");
            // tra ra ma loi
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getPartnerCode())) {
            throw new AppException("API-PA001", "Bat buoc nhap partnerCode");
            // Ma doi tac
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getName())) {
            throw new AppException("API-PA002", "Bat buoc nhap name");
            // Ten doi tac
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getTin())) {
            throw new AppException("API-PA003", "Bat buoc nhap tin");
            // MST
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getProvince())) {
            throw new AppException("API-PA004", "Bat buoc nhap province");
            // Tinh
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getDistrict())) {
            throw new AppException("API-PA005", "Bat buoc nhap district");
            // Quan
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getPrecinct())) {
            throw new AppException("API-PA006", "Bat buoc nhap precinct");
            // Xa
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getTel())) {
            throw new AppException("API-PA007", "Bat buoc nhap tel");
            // Dien thoai
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getRepresentativeName())) {
            throw new AppException("API-PA008", "Bat buoc nhap representName");
            // Nguoi dai dien
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getRepresentativeIdNo())) {
            throw new AppException("API-PA009", "Bat buoc nhap representIdNo");
            // NDD CMND
        } else if (DataUtil.isStringNullOrEmpty(partnerCreate.getRepresentativeTitle())) {
            throw new AppException("API-PA010", "Bat buoc nhap representTitle");
            // NDD Chuc vu
        }

        boolean isExistPartnerCode = false;
        if (DataUtil.isNullOrZero(partnerCreate.getPartnerId())) {
            isExistPartnerCode = partnerRepo.isExist(partnerCreate.getPartnerCode());
        } else {
            isExistPartnerCode = partnerRepo.isExist(partnerCreate.getPartnerCode(), partnerCreate.getPartnerId());
        }
        if (isExistPartnerCode) {
            throw new AppException("API-PA021", "Partner code already exist");
        }

        boolean isExistTIN = false;
        if (DataUtil.isNullOrZero(partnerCreate.getPartnerId())) {
            isExistTIN = partnerRepo.isExistTin(partnerCreate.getTin());
        } else {
            isExistTIN = partnerRepo.isExistTin(partnerCreate.getTin(), partnerCreate.getPartnerId());
        }
        if (isExistTIN) {
            throw new AppException("API-PA022", "Tin already exist");
        }

        //PROVINCE , DISTRICT , PRECINCT
        if (partnerCreate.getProvince().length() > 50) {
            throw new AppException("API-PA016", "Maxlength truong province la 50");
        }
        if (partnerCreate.getDistrict().length() > 50) {
            throw new AppException("API-PA017", "Maxlength truong district la 50");
        }
        if (partnerCreate.getPrecinct().length() > 50) {
            throw new AppException("API-PA018", "Maxlength truong precinct la 50");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean changePartnerStatus(long partnerId, boolean isActive, String user) {
        log.info("SDI updatePartner ... {}", partnerId, isActive, user);
        // validate
        if (DataUtil.isStringNullOrEmpty(user)) {
            // Nguoi thuc hien
        }

        Partner partnerStatus = new Partner();
        partnerStatus.setPartnerId(partnerId);

        List<Partner> list = partnerRepo.searchPartnerFull(partnerStatus);
        if (list != null && !list.isEmpty()) {
            partnerStatus = list.get(0);
        } else {
            // Id khong ton tai
        }

        String updateStatus = isActive ? "1" : "0";
        if (partnerStatus.getStatus().equals(updateStatus)) {
            // Trang thai khong thay doi
            throw new AppException("API-PA014", "Trang thai khong thay doi");
        }
        partnerStatus.setStatus(updateStatus);

        // update
        Partner partner = partnerRepo.save(partnerStatus);
        log.info("SDO updatePartner ... {}", partner);

        return true;
    }

    @Override
    public PartnerFindByIdSdo findById(long partnerId) {
        if (DataUtil.isNullOrZero(partnerId)) {
            throw new AppException("API-PA015", "Bat buoc truyen partnerId ");
        }

        PartnerFindByIdSdo partnerFindByIdSdo = partnerRepo.findById(partnerId);
        return partnerFindByIdSdo;
    }

    @Override
    public boolean isExist(Long partnerId) {
        return partnerRepo.isExist(partnerId);
    }

    public Partner toPartnerUpdate(Partner updatePartner, Partner oldPartner) {

        oldPartner.setPartnerId(updatePartner.getPartnerId());
        oldPartner.setPartnerCode(updatePartner.getPartnerCode());
        oldPartner.setName(updatePartner.getName());
        oldPartner.setTin(updatePartner.getTin());
        oldPartner.setProvince(updatePartner.getProvince());
        oldPartner.setDistrict(updatePartner.getDistrict());
        oldPartner.setPrecinct(updatePartner.getPrecinct());
        oldPartner.setAddress(updatePartner.getAddress());
        oldPartner.setTel(updatePartner.getTel());
        oldPartner.setEmail(updatePartner.getEmail());
        oldPartner.setLastUpDateDate(new Date());
        oldPartner.setLastUpDateUser(updatePartner.getLastUpDateUser());
        oldPartner.setRepresentativeName(updatePartner.getRepresentativeName());
        oldPartner.setRepresentativeTitle(updatePartner.getRepresentativeTitle());
        oldPartner.setRepresentativeIdNo(updatePartner.getRepresentativeIdNo());
        oldPartner.setRepresentativeTel(updatePartner.getRepresentativeTel());
        oldPartner.setRepresentativeEmail(updatePartner.getRepresentativeEmail());
        oldPartner.setFax(updatePartner.getFax());
        oldPartner.setRepresentativeIdType(updatePartner.getRepresentativeIdType());

        return oldPartner;
    }

}
