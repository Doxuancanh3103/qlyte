package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.domain.ProductCustSegm;

import java.util.List;

public interface ProductCustSegmRepoCustom{
    ProductCustSegm findByValue(String globalListValue);

    ProductCustSegm findByValueAndProduct(String globalListValue, Long productId);

    ProductCustSegm findByValueAndProductOffer(String globalListValue, Long productOfferId);

    List<ProductCustSegm> findByProductOfferId(Long productOfferId);

    void changeStatusByProductId(String status, long productId);

    void changeStatusByProductOfferId(String status, long productOfferId);
}
