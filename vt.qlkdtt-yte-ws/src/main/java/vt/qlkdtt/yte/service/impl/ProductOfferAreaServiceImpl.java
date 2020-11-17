package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.repository.ProductOfferAreaRepo;
import vt.qlkdtt.yte.service.ProductOfferAreaService;

@Service
@Transactional
public class ProductOfferAreaServiceImpl implements ProductOfferAreaService {
    @Autowired
    ProductOfferAreaRepo productOfferAreaRepo;

    @Override
    public void changeStatusByProductOffer(String status, long productOfferId) {
        productOfferAreaRepo.changeStatusByProductOffer(status, productOfferId);
    }

    @Override
    public boolean isExist(long productOfferAreaId) {
        return productOfferAreaRepo.isExist(productOfferAreaId);
    }

    @Override
    public boolean isNationWide(Long productOfferId) {
        return productOfferAreaRepo.isNationWide(productOfferId);
    }
}
