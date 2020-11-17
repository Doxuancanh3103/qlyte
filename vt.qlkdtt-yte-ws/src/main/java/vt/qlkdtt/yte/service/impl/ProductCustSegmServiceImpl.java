package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.ProductCustSegm;
import vt.qlkdtt.yte.repository.ProductCustSegmRepo;
import vt.qlkdtt.yte.service.ProductCustSegmService;
import vt.qlkdtt.yte.service.exception.AppException;

import java.util.List;

@Service
@Transactional
public class ProductCustSegmServiceImpl implements ProductCustSegmService {
    @Autowired
    ProductCustSegmRepo productCustSegmRepo;

    @Override
    public ProductCustSegm findByValue(String globalListValue) {
        return productCustSegmRepo.findByValue(globalListValue);
    }

    @Override
    public ProductCustSegm findByValueAndProduct(String globalListValue, Long productId) {
        return productCustSegmRepo.findByValueAndProduct(globalListValue, productId);
    }

    @Override
    public ProductCustSegm findByValueAndProductOffer(String globalListValue, Long productOfferId) {
        return productCustSegmRepo.findByValueAndProductOffer(globalListValue, productOfferId);
    }

    @Override
    public List<ProductCustSegm> findByProductOfferId(Long productOfferId) {
        if (DataUtil.isNullOrZero(productOfferId)) {
            throw new AppException("API-PCS001", "Product offer id is required");
        }

        return productCustSegmRepo.findByProductOfferId(productOfferId);
    }

    @Override
    public void changeStatusByProductId(String status, long productId) {
        productCustSegmRepo.changeStatusByProductId(status, productId);
    }

    @Override
    public void changeStatusByProductOfferId(String status, long productOfferId) {
        productCustSegmRepo.changeStatusByProductOfferId(status, productOfferId);
    }
}
