package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.ProductOfferSearchSdi;
import vt.qlkdtt.yte.service.sdo.ProOfferProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferSearchSdo;

import java.util.List;

public interface ProductOfferRepoCustom {
    Page<ProductOfferSearchSdo> searchProductOffer(ProductOfferSearchSdi sdi, Pageable pageRequest);

    List<ProOfferProductSearchConnectSdo> searchProductOfferConnect(Long productId, String provisionType);

    ProductOfferFindByIdSdo findProductOfferById(long productOfferId);

    boolean changeStatus(long id, String status);

    void mappingInsertProductOfferWithCustomerType(List<String> listCustomerType, long productOfferId);

    boolean isExist(String productOfferCode);

    boolean isExist(long productOfferId);

    boolean isExist(String productOfferCode, long productOfferId);
}
