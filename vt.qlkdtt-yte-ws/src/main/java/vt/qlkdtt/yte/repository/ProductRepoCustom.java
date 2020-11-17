package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.ProductInsertSdi;
import vt.qlkdtt.yte.service.sdi.ProductUpdateSdi;
import vt.qlkdtt.yte.service.sdo.ProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductSearchSdo;
import vt.qlkdtt.yte.service.sdi.ProductSearchSdi;
import vt.qlkdtt.yte.service.sdo.ProductFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductInsertSdo;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ProductRepoCustom {
    Page<ProductSearchSdo> searchProduct(ProductSearchSdi productSearchSdi, Pageable pageable);

    List<ProductSearchConnectSdo> searchProductConnect(String productNameCode);

    ProductFindByIdSdo searchProductById(long productId);

    boolean changeStatus(long productId, String status);

    void mappingInsertProductWithCustomerType(long productId, List<String> customerTypes);

    boolean isExist(String productCode);

    boolean isExist(long productId);

    boolean isExist(Long productId, String productCode);
}
