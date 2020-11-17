package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.coong.swagger.ApiPageable;
import me.coong.web.response.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.ProductOfferService;
import vt.qlkdtt.yte.service.sdi.ProductOfferInsertSdi;
import vt.qlkdtt.yte.service.sdi.ProductOfferSearchSdi;
import vt.qlkdtt.yte.service.sdi.ProductOfferUpdateSdi;
import vt.qlkdtt.yte.service.sdi.UpdateStatusSdi;
import vt.qlkdtt.yte.service.sdo.ProOfferProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferInsertSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferSearchSdo;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "product-offer")
@Api(value = "product-offer")
public class ProductOfferController {
    @Autowired
    ProductOfferService productOfferService;

    //<editor-fold desc="Search product offer">
    @GetMapping(value = "searchProductOffer")
    @ApiOperation(value = "Search product offer")
    @ApiPageable
    public ResponseEntity<PagedResponse<ProductOfferSearchSdo>> searchProductOffer(
            @RequestParam(value = "productGroupId", required = false) Long productGroupId,
            @RequestParam(value = "productNameCode", required = false) String productNameCode,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "provinceId", required = false) String provinceCode,
            @RequestParam(value = "districtId", required = false) String districtCode,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @ApiIgnore @PageableDefault Pageable pageRequest
    ) {
        ProductOfferSearchSdi sdi = new ProductOfferSearchSdi(productGroupId, productNameCode, status, provinceCode, districtCode, fromDate, toDate);

        Page<ProductOfferSearchSdo> rs = productOfferService.searchProductOffer(sdi, pageRequest);
        if (rs != null) {
            return ResponseEntity.ok(PagedResponse.builder().page(rs).build());
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Search product offer connect">
    @GetMapping(value = "searchProductOfferConnect")
    @ApiOperation(value = "Search product offer connect")
    public ResponseEntity<List<ProOfferProductSearchConnectSdo>> searchProductOfferConnect(
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "provisionType") String provisionType
    ) {
        List<ProOfferProductSearchConnectSdo> rs = productOfferService.searchProductOfferConnect(productId, provisionType);
        if (rs != null) {
            return ResponseEntity.ok(rs);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @GetMapping(value = "findById")
    @ApiOperation(value = "Find product offer by id")
    public ResponseEntity<ProductOfferFindByIdSdo> findById(
            @RequestParam(value = "productOfferId") long productOfferId
    ) {
        ProductOfferFindByIdSdo rs = productOfferService.findById(productOfferId);

        if (rs != null && !DataUtil.isNullOrZero(rs.getProductOfferId())) {
            return ResponseEntity.ok(rs);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @PostMapping(value = "changeProductOfferStatus")
    @ApiOperation(value = "Change product offer status")
    public ResponseEntity<Boolean> changeProductStatus(@RequestBody UpdateStatusSdi updateStatusSdi) {
        return ResponseEntity.ok(productOfferService.changeStatus(updateStatusSdi.getId(), updateStatusSdi.getStatus()));
    }
    //</editor-fold>

    //<editor-fold desc="Create product offer">
    @PostMapping(value = "createProductOffer")
    @ApiOperation(value = "Create product offer")
    public ResponseEntity<ProductOfferInsertSdo> createProductOffer(@RequestBody ProductOfferInsertSdi dataInsert) {
        ProductOfferInsertSdo productOffer = productOfferService.createProductOffer(dataInsert);

        if (productOffer != null && !DataUtil.isNullOrZero(productOffer.getProductOfferId())) {
            return ResponseEntity.ok(productOffer);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Update product offer">
    @PostMapping(value = "updateProductOffer")
    @ApiOperation(value = "Update product offer")
    public ResponseEntity<ProductOfferInsertSdo> updateProductOffer(@RequestBody ProductOfferUpdateSdi dataUpdate) {
        ProductOfferInsertSdo productOffer = productOfferService.updateProductOffer(dataUpdate);

        if (productOffer != null && !DataUtil.isNullOrZero(productOffer.getProductOfferId())) {
            return ResponseEntity.ok(productOffer);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
