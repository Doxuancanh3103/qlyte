package vt.qlkdtt.yte.web.controller;

import com.google.common.base.CharMatcher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.GlobalParam;
import vt.qlkdtt.yte.domain.Product;
import vt.qlkdtt.yte.dto.AreaFullDTO;
import vt.qlkdtt.yte.service.AreaService;
import vt.qlkdtt.yte.service.GlobalParamService;
import vt.qlkdtt.yte.service.ProductService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.*;
import vt.qlkdtt.yte.service.sdo.GlobalParamInsertSdo;
import vt.qlkdtt.yte.service.sdo.ProductFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductInsertSdo;
//import vt.qlkdtt.yte.service.sdi.GenerateAccountSdi;
//import vt.qlkdtt.yte.service.sdi.GenerateAccountServiceSdi;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "global-param")
@Api(value = "global-param")
public class GlobalParamControler {
    @Autowired
    GlobalParamService globalParamService;

    @Autowired
    ProductService productService;

    @Autowired
    AreaService areaService;

    //<editor-fold desc="Find by code">
    @GetMapping(value = "findByCode")
    @ApiOperation(value = "findByCode")
    public ResponseEntity<List<GlobalParam>> findByCode(
            @RequestParam(value = "code", required = false) String code) {
        List<GlobalParam> lstGlobalParam = globalParamService.findByCode(code);

        if (lstGlobalParam != null && !lstGlobalParam.isEmpty()) {
            return ResponseEntity.ok(lstGlobalParam);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Get value by code">
    @GetMapping(value = "getValueByCode")
    @ApiOperation(value = "getValueByCode")
    public ResponseEntity<String> getValueByCode(
            @RequestParam(value = "code", required = false) String code) {
        List<GlobalParam> lstGlobalParam = globalParamService.findByCode(code);

        if (lstGlobalParam != null && !lstGlobalParam.isEmpty()) {
            GlobalParam globalParam = lstGlobalParam.get(0);
            return ResponseEntity.ok(DataUtil.safeToString(globalParam.getValue()));
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @PostMapping(value = "changeStatus")
    @ApiOperation(value = "Change status")
    public ResponseEntity<Boolean> changeStatus(@RequestBody UpdateStatusSdi updateStatusSdi) {
        return ResponseEntity.ok(globalParamService.changeStatus(updateStatusSdi.getId(), updateStatusSdi.getStatus()));
    }
    //</editor-fold>

    //<editor-fold desc="Create global param">
    @PostMapping(value = "createGlobalParam")
    @ApiOperation(value = "Create global param")
    public ResponseEntity<GlobalParamInsertSdo> createGlobalParam(@RequestBody GlobalParamInsertSdi dataInsert) {
        GlobalParamInsertSdo result = globalParamService.createGlobalParam(dataInsert);

        if (result != null && !DataUtil.isNullOrZero(result.getGlobalParamId())) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Update global param">
    @PostMapping(value = "updateGlobalParam")
    @ApiOperation(value = "Update global param")
    public ResponseEntity<GlobalParamInsertSdo> updateGlobalParam(@RequestBody GlobalParamUpdateSdi dataUpdate) {
        GlobalParamInsertSdo result = globalParamService.updateGlobalParam(dataUpdate);

        if (result != null && !DataUtil.isNullOrZero(result.getGlobalParamId())) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Generate account">
    @PostMapping(value = "generateAccount")
    @ApiOperation(value = "generateAccount")
    public ResponseEntity<String> generateAccount(@RequestBody GenerateAccountSdi generateAccountSdi) throws Exception {
        String accountNo = globalParamService.generateAccount(generateAccountSdi);

        return ResponseEntity.ok(accountNo.toLowerCase());
    }
    //</editor-fold>

    //<editor-fold desc="Generate account service">
    @PostMapping(value = "generateAccountService")
    @ApiOperation(value = "generateAccountService")
    public ResponseEntity<String> generateAccountService(@RequestBody GenerateAccountSdi generateAccountSdi) throws Exception {
        String accountService = globalParamService.generateAccountService(generateAccountSdi);

        return ResponseEntity.ok(accountService.toLowerCase());
    }
    //</editor-fold>
}
