package vt.qlkdtt.yte.service;

import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.domain.ProductCustSegm;

import java.util.List;

@Service
public interface ProductCustSegmService {
    ProductCustSegm findByValue(String globalListValue);

    ProductCustSegm findByValueAndProduct(String globalListValue, Long productId);

    ProductCustSegm findByValueAndProductOffer(String globalListValue, Long productOfferId);

    List<ProductCustSegm> findByProductOfferId(Long productOfferId);

    void changeStatusByProductId(String status, long productId);

    void changeStatusByProductOfferId(String status, long productOfferId);
}
