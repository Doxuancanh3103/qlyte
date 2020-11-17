package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.SubscriberService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.ChangeSalesManCodeSdi;
import vt.qlkdtt.yte.service.sdi.UpdateBrokersPartnerCodeSdi;
import vt.qlkdtt.yte.service.sdo.ContractFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.SubscriberFindByIdSdo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "subscriber")
@Api(value = "subscriber")
public class SubscriberController {
    @Autowired
    SubscriberService subscriberService;

    //<editor-fold desc="Find by id">
    @GetMapping(value = "findById")
    @ApiOperation(value = "Search subscriber by id")
    public ResponseEntity<SubscriberFindByIdSdo> findById(@RequestParam Long subscriberId) {
        SubscriberFindByIdSdo result = subscriberService.findById(subscriberId);

        if (result != null && !DataUtil.isNullOrZero(result.getSubscriberId())) {
            return ResponseEntity.ok(result);
        } else {
            List<String> lstError = new ArrayList<>();
            lstError.add(subscriberId.toString());
            throw new AppException("API-SC001", "Subscriber id {0} not exist", lstError);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Change sales man code">
    @PostMapping(value = "changeSalesManCode")
    @ApiOperation(value = "Change sales man code")
    public ResponseEntity<Boolean> changeSalesManCode(@RequestBody ChangeSalesManCodeSdi sdi) {
        Boolean result = subscriberService.changeSalesManCode(sdi.getSalesManCode(), sdi.getSubscriberId());

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Update brokers partner code">
    @PostMapping(value = "updateBrokersPartnerCode")
    @ApiOperation(value = "Update brokers partner code")
    public ResponseEntity<Boolean> updateBrokersPartnerCode(@RequestBody UpdateBrokersPartnerCodeSdi sdi) {
        Boolean result = subscriberService.updateBrokersPartnerCode(sdi.getBrokersPartnerCode(), sdi.getSubscriberId());

        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
