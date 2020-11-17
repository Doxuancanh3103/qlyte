package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.ProductCustSegm;
import vt.qlkdtt.yte.service.ProductCustSegmService;
import vt.qlkdtt.yte.service.SubscriberService;
import vt.qlkdtt.yte.service.sdo.SubscriberFindByIdSdo;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "productCustSegm")
@Api(value = "productCustSegm")
public class ProductCustSegmController {
    @Autowired
    ProductCustSegmService productCustSegmService;

    //<editor-fold desc="Find by product offer id">
    @GetMapping(value = "findByProductOfferId")
    @ApiOperation(value = "Find by productOfferId")
    public ResponseEntity<List<ProductCustSegm>> findByProductOfferId(@RequestParam Long productOfferId) {
        List<ProductCustSegm> result = productCustSegmService.findByProductOfferId(productOfferId);

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
