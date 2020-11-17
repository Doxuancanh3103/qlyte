package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.coong.swagger.ApiPageable;
import me.coong.web.response.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.ProductService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.ProductInsertSdi;
import vt.qlkdtt.yte.service.sdi.ProductSearchSdi;
import vt.qlkdtt.yte.service.sdi.ProductUpdateSdi;
import vt.qlkdtt.yte.service.sdi.UpdateStatusSdi;
import vt.qlkdtt.yte.service.sdo.ProductFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductInsertSdo;
import vt.qlkdtt.yte.service.sdo.ProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductSearchSdo;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "product")
@Api(value = "product")
public class ProductController {
    @Autowired
    ProductService productService;

    //<editor-fold desc="Search product by Group name, product code, product name">
    @GetMapping(value = "searchProduct")
    @ApiOperation(value = "Find product by Group name or Product code or Product name")
    @ApiPageable
    public ResponseEntity<PagedResponse<ProductSearchSdo>> searchProduct(
            @RequestParam(value = "productGroupId", required = false) String productGroupId,
            @RequestParam(value = "productNameCode", required = false) String productNameCode,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @ApiIgnore @PageableDefault Pageable pageRequest
    ) {
        try {
            ProductSearchSdi productSearchSdi = new ProductSearchSdi(productGroupId, productNameCode, status, fromDate, toDate);
            return ResponseEntity.ok(PagedResponse.builder().page(productService.searchProduct(productSearchSdi, pageRequest)).build());
        } catch (Exception e) {
            log.error(e.getMessage() != null ? e.getMessage() : "");
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Search product connect">
    @GetMapping(value = "searchProductConnect")
    @ApiOperation(value = "Search product in doConnect")
    public ResponseEntity<List<ProductSearchConnectSdo>> searchProductConnect(
            @RequestParam(value = "productNameCode", required = false) String productNameCode
    ) {
        List<ProductSearchConnectSdo> result = productService.searchProductConnect(productNameCode);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Search product by id">
    @GetMapping(value = "findById")
    @ApiOperation(value = "Find product by id")
    public ResponseEntity<ProductFindByIdSdo> findById(
            @RequestParam(value = "productId") long productId
    ) {
        ProductFindByIdSdo productFindByIdSdo = productService.searchProductById(productId);

        if (productFindByIdSdo != null && !DataUtil.isNullOrZero(productFindByIdSdo.getProductId())) {
            return ResponseEntity.ok(productFindByIdSdo);
        } else {
            throw new AppException("API-PR001", "productId " + productId + " not exist");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @PostMapping(value = "changeProductStatus")
    @ApiOperation(value = "Change product status")
    public ResponseEntity<Boolean> changeProductStatus(@RequestBody UpdateStatusSdi updateStatusSdi) {
        return ResponseEntity.ok(productService.changeStatus(updateStatusSdi.getId(), updateStatusSdi.getStatus()));
    }
    //</editor-fold>

    //<editor-fold desc="Create product">
    @PostMapping(value = "createProduct")
    @ApiOperation(value = "Create product")
    public ResponseEntity<ProductInsertSdo> createProduct(@RequestBody ProductInsertSdi dataInsert) {
        ProductInsertSdo productInsertSdo = productService.insert(dataInsert);
        if (productInsertSdo != null && !DataUtil.isNullOrZero(productInsertSdo.getProductId())) {
            return ResponseEntity.ok(productInsertSdo);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Update product">
    @PostMapping(value = "updateProduct")
    @ApiOperation(value = "Update product")
    public ResponseEntity<ProductInsertSdo> updateProduct(@RequestBody ProductUpdateSdi dataUpdate) {
        ProductInsertSdo productInsertSdo = productService.update(dataUpdate);

        if (productInsertSdo != null && !DataUtil.isNullOrZero(productInsertSdo.getProductId())) {
            return ResponseEntity.ok(productInsertSdo);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
