package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.DoConnectService;
import vt.qlkdtt.yte.service.sdi.DoConnectSdi;
import vt.qlkdtt.yte.service.sdo.*;

@Slf4j
@RestController
@RequestMapping(value = "connect")
@Api(value = "Connect")
public class ConnectController {
    @Autowired
    DoConnectService doConnectService;

    //<editor-fold desc="Do connect">
    @PostMapping(value = "doConnect")
    @ApiOperation(value = "Do connect")
    public ResponseEntity<DoConnectSdo> doConnect(@RequestBody DoConnectSdi dataConnect) {
        DoConnectSdo result = doConnectService.doConnect(dataConnect);

        if (result != null && !DataUtil.isNullOrZero(result.getCustomerId())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
