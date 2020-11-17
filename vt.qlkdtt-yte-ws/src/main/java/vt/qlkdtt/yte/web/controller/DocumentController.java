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
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.DocumentService;
import vt.qlkdtt.yte.service.sdi.DocumentUpdateSdi;
import vt.qlkdtt.yte.service.sdo.DocumentInsertSdo;
import vt.qlkdtt.yte.service.sdo.DocumentSearchSdo;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "document")
@Api(value = "document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    //<editor-fold desc="Find all">
    @GetMapping(value = "findAll")
    @ApiOperation(value = "find all document")
    @ApiPageable
    public ResponseEntity<PagedResponse<DocumentSearchSdo>> findAll(@ApiIgnore @PageableDefault Pageable pageRequest) {
        try {
            return ResponseEntity.ok(PagedResponse.builder().page(documentService.findAll(pageRequest)).build());
        } catch (Exception e) {
            log.error(e.getMessage() != null ? e.getMessage() : "");
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @GetMapping(value = "findById")
    @ApiOperation(value = "Search document by id")
    public ResponseEntity<DocumentSearchSdo> findById(@RequestParam(value = "id") long id) {
        try {
            return ResponseEntity.ok(documentService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage() != null ? e.getMessage() : "");
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Search document by code or name">
    @GetMapping(value = "searchDocument")
    @ApiOperation(value = "Search document by code or name")
    public ResponseEntity<List<DocumentSearchSdo>> searchDocument(
            @RequestParam(value = "documentCodeName") String documentCodeName
    ) {
        try {
            return ResponseEntity.ok(documentService.searchDocument(documentCodeName));
        } catch (Exception e) {
            log.error(e.getMessage() != null ? e.getMessage() : "");
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Delete">
    @PostMapping(value = "delete")
    @ApiOperation(value = "Delete document")
    public ResponseEntity<Map<String, Object>> delete(
            @RequestParam(value = "id") long id) {
        try {
            return ResponseEntity.ok(documentService.changeStatus(id, Const.STATUS.INACTIVE));
        } catch (Exception e) {
            log.error(e.getMessage() != null ? e.getMessage() : "");
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Update">
    @PostMapping(value = "update")
    @ApiOperation(value = "Update document")
    public ResponseEntity<DocumentInsertSdo> update(@RequestBody DocumentUpdateSdi sdi) {
        DocumentInsertSdo result = documentService.update(sdi);

        if (result != null && !DataUtil.isNullOrZero(result.getId())) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Insert">
//    @PostMapping(value = "insert")
//    @ApiOperation(value = "Insert document")
    public ResponseEntity<DocumentInsertSdo> insert(@RequestBody DocumentUpdateSdi dataInsert) {
//        DocumentInsertSdo result = documentService.insert(dataInsert);

//        if (result != null && !DataUtil.isNullOrZero(result.getId())) {
//            return ResponseEntity.ok(result);
//        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    /////////////////////////////////////////////////////////////////////////////////////////////////
//    @GetMapping(value = "getDocumnetForProductOffer")
//    @ApiOperation(value = "find all avaliable document for mapping product offer")
//    @ApiPageable
//    public ResponseEntity<List<DocumentForProductOfferSdo>> getDocumnetForProductOffer(
//            @ApiIgnore @PageableDefault Pageable pageRequest) {
//        try {
//
//
//            return ResponseEntity.ok(PagedResponse.builder().page(documentService.getDocumnetForProductOffer(pageRequest)).build());
//        } catch (Exception e) {
//            log.error(e.getMessage() != null ? e.getMessage() : "");
//        }
//        return ResponseEntity.noContent().build();
//    }
}
