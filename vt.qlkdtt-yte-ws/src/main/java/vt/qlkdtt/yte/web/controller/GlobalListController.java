package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.coong.web.response.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.GlobalList;
import vt.qlkdtt.yte.service.GlobalListService;
import vt.qlkdtt.yte.service.sdi.GlobalListInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListInsertSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListSearchSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListUpdateSdo;
import org.springframework.data.domain.Pageable;
import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "global-list")
@Api(value = "global-list")
public class GlobalListController {
    @Autowired
    GlobalListService globalListService;

    @GetMapping(value = "/")
    @ApiOperation("Get the global list items")
    public ResponseEntity<PagedResponse<GlobalListSdo>> get(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "valueName", required = false) String valueName,
            @RequestParam(value = "valueCode", required = false) String valueCode,
            @ApiIgnore  @PageableDefault Pageable pageRequest
    ) throws ParseException {
        Page<GlobalListSdo> lstGlobalListSdos = globalListService.get(keyword, pageRequest);

        if (lstGlobalListSdos != null) {
            return ResponseEntity.ok(PagedResponse.builder().page(lstGlobalListSdos).build());
        }
        return ResponseEntity.noContent().build();
    }

    //<editor-fold desc="Find by code">
    @GetMapping(value = "findByCode")
    @ApiOperation(value = "find global-list by code")
    public ResponseEntity<List<GlobalListSearchSdo>> findByCode(
            @RequestParam(value = "code", required = false) String code) {
        List<GlobalListSearchSdo> lstGlobalListSearchSdos = globalListService.findByCode(code, null, null);

        if (lstGlobalListSearchSdos != null) {
            return ResponseEntity.ok(lstGlobalListSearchSdos);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Find by code and product">
    @GetMapping(value = "findByCodeAndProduct")
    @ApiOperation(value = "find global-list by code and productId")
    public ResponseEntity<List<GlobalListSearchSdo>> findByCodeAndProduct(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "productId", required = false) Long productId
    ) {
        List<GlobalListSearchSdo> lstGlobalListSearchSdos = globalListService.findByCode(code, productId, null);

        if (lstGlobalListSearchSdos != null) {
            return ResponseEntity.ok(lstGlobalListSearchSdos);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Find by code and product">
    @GetMapping(value = "findByCodeAndProductGroup")
    @ApiOperation(value = "find global-list by code and product group")
    public ResponseEntity<List<GlobalListSearchSdo>> findByCodeAndProductGroup(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "productId", required = false) String productGroupId
    ) {
        List<GlobalListSearchSdo> lstGlobalListSearchSdos = globalListService.findByCode(code, null, productGroupId);

        if (lstGlobalListSearchSdos != null) {
            return ResponseEntity.ok(lstGlobalListSearchSdos);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "create")
    @ApiOperation(value = "Create a Global list")
    public ResponseEntity<GlobalListInsertSdo> create(@RequestBody GlobalListInsertSdi data) {
        GlobalListInsertSdo rs = globalListService.insert(data);
        if (rs != null && !DataUtil.isNullOrZero(rs.getId())) {
            return ResponseEntity.ok(rs);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "update")
    @ApiOperation(value = "Update a Global list")
    public ResponseEntity<Long> update(@RequestBody GlobalListUpdateSdi data) {
        int rs = globalListService.update(data);
        if (rs > 0) {
            return ResponseEntity.ok(data.getId());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        boolean isRemoved = globalListService.delete(id);

        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(id);
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
//    @GetMapping(value = "findById")
//    @ApiOperation(value = "Find option set by id")
//    public ResponseEntity<GlobalListSearchSdo> findById(@RequestParam(value = "id") long id) {
//        try {
//            GlobalListSearchSdo customerType = globalListService.findById(id);
//            return ResponseEntity.ok(customerType);
//        } catch (Exception e) {
//            log.error(e.getMessage() != null ? e.getMessage() : "");
//        }
//        return ResponseEntity.noContent().build();
//    }
    //</editor-fold>

    //<editor-fold desc="Delete">
//    @DeleteMapping(value = "delete")
//    @ApiOperation(value = "Delete option set")
//    public ResponseEntity<Map<String, Object>> delete(@RequestParam(value = "id") long id) {
//        try {
//            Map<String, Object> result = globalListService.changeStatus(id, Const.STATUS.INACTIVE);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            log.error(e.getMessage() != null ? e.getMessage() : "");
//        }
//        return ResponseEntity.noContent().build();
//    }
    //</editor-fold>

    //<editor-fold desc="Update">
//    @PutMapping(value = "update")
//    @ApiOperation(value = "Update option set")
//    public ResponseEntity<Map<String, Object>> update(
//            @RequestParam(value = "id") long id,
//            @RequestParam(value = "name") String name,
//            @RequestParam(value = "description") String description,
//            @RequestParam(value = "updateUser") String updateUser
//    ){
//        try {
//            GlobalListUpdateSdi sdi = new GlobalListUpdateSdi(id, name, description, updateUser);
//
//            Map<String, Object> result = globalListService.update(sdi);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            log.error(e.getMessage() != null ? e.getMessage() : "");
//        }
//        return ResponseEntity.noContent().build();
//    }
    //</editor-fold>

    //<editor-fold desc="Inset">
//    @PostMapping(value = "insert")
//    @ApiOperation(value = "Insert option set")
//    public ResponseEntity<GlobalListInsertSdo> update(
//            @RequestParam(value = "code") String code,
//            @RequestParam(value = "name") String name,
//            @RequestParam(value = "description") String description,
//            @RequestParam(value = "createUser") String createUser
//    ){
//        try {
//            GlobalListInsertSdi sdi = new GlobalListInsertSdi(code, name, description, createUser);
//
//            return ResponseEntity.ok(globalListService.insert(sdi));
//        } catch (Exception e) {
//            log.error(e.getMessage() != null ? e.getMessage() : "");
//        }
//        return ResponseEntity.noContent().build();
//    }
    //</editor-fold>
}
