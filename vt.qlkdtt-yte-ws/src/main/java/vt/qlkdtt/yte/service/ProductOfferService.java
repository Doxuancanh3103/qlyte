package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.ProductOfferSearchSdi;
import vt.qlkdtt.yte.service.sdo.ProOfferProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferInsertSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferSearchSdo;
import vt.qlkdtt.yte.service.sdi.ProductOfferInsertSdi;
import vt.qlkdtt.yte.service.sdi.ProductOfferUpdateSdi;

import java.util.List;

public interface ProductOfferService {
    Page<ProductOfferSearchSdo> searchProductOffer(ProductOfferSearchSdi sdi, Pageable pageRequest);

    List<ProOfferProductSearchConnectSdo> searchProductOfferConnect(Long productId, String provisionType);

    ProductOfferFindByIdSdo findById(long productOfferId);

    boolean changeStatus(long id, String status);

    ProductOfferInsertSdo createProductOffer(ProductOfferInsertSdi dataInsert);

    ProductOfferInsertSdo updateProductOffer(ProductOfferUpdateSdi dataUpdate);
}
