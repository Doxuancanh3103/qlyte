package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdo.ProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductSearchSdo;
import vt.qlkdtt.yte.service.sdi.ProductInsertSdi;
import vt.qlkdtt.yte.service.sdi.ProductSearchSdi;
import vt.qlkdtt.yte.service.sdi.ProductUpdateSdi;
import vt.qlkdtt.yte.service.sdo.ProductFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductInsertSdo;

import java.util.List;

public interface ProductService {
    Page<ProductSearchSdo> searchProduct(ProductSearchSdi productSearchSdi, Pageable pageable);

    List<ProductSearchConnectSdo> searchProductConnect(String productNameCode);

    ProductFindByIdSdo searchProductById(long productId);

    boolean changeStatus(long productId, String status);

    ProductInsertSdo insert(ProductInsertSdi sdi);

    ProductInsertSdo update(ProductUpdateSdi sdi);

    boolean isExist(long productId);
}
