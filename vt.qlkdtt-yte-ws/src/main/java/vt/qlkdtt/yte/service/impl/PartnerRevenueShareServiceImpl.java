package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.repository.PartnerRevenueSharedRepo;
import vt.qlkdtt.yte.service.PartnerRevenueShareService;

@Service
@Transactional
public class PartnerRevenueShareServiceImpl implements PartnerRevenueShareService {
    @Autowired
    PartnerRevenueSharedRepo partnerRevenueSharedRepo;

    @Override
    public void changeStatusByProductId(String status, long productId) {
        partnerRevenueSharedRepo.changeStatusByProductId(status, productId);
    }

    @Override
    public void changeStatusByProductOfferId(String status, long productOfferId) {
        partnerRevenueSharedRepo.changeStatusByProductOfferId(status, productOfferId);
    }

    @Override
    public boolean isExist(long prsId) {
        return partnerRevenueSharedRepo.isExist(prsId);
    }

}
