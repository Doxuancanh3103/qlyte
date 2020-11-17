package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.GlobalListValueService;
import vt.qlkdtt.yte.service.sdi.GlobalListValueInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListValueUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListValueSdo;
import vt.qlkdtt.yte.service.sdi.GlobalListValueSdi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "global-list-values")
@Api(value = "global-list-values")
public class GlobalListValueController {
    @Autowired
    GlobalListValueService globalListValueService;

    @PostMapping(value = "create")
    public ResponseEntity<GlobalListValueSdo> create(@RequestBody GlobalListValueInsertSdi data) throws ParseException {
        GlobalListValueSdo sdo = globalListValueService.insert(data);
        return ResponseEntity.ok(sdo);
    }

    @PostMapping(value = "update")
    public ResponseEntity<Long> update(@RequestBody GlobalListValueUpdateSdi data) throws ParseException {
        int ok = globalListValueService.update(data);
        if (ok > 0) {
            return ResponseEntity.ok(data.getId());
        }
        return ResponseEntity.ok(DataUtil.safeToLong(ok));
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        boolean isRemoved = globalListValueService.delete(id);

        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(id);
    }
}
