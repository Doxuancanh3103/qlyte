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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.DocumentRequired;
import vt.qlkdtt.yte.dto.AreaDTO;
import vt.qlkdtt.yte.dto.AreaFullDTO;
import vt.qlkdtt.yte.service.AreaService;
import vt.qlkdtt.yte.service.DocumentRequiredService;
import vt.qlkdtt.yte.service.sdi.ContractSearchSdi;
import vt.qlkdtt.yte.service.sdo.ContractSearchSdo;
import vt.qlkdtt.yte.service.sdo.DocumentRequiredSearchSdo;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "document-required")
@Api(value = "Document required")
public class DocumentRequiredController {
    @Autowired
    DocumentRequiredService documentRequiredService;

    @GetMapping(value = "searchDocumentRequired")
    @ApiOperation(value = "Search document required")
    public ResponseEntity<List<DocumentRequiredSearchSdo>> searchDocumentRequired(
            @RequestParam(required = false) String orderType,
            @RequestParam(required = false) String actionType,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String productGroupId
    ) throws Exception {
        List<DocumentRequiredSearchSdo> result = documentRequiredService.searchDocumentRequired(orderType, actionType, productId, productGroupId);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }
}
