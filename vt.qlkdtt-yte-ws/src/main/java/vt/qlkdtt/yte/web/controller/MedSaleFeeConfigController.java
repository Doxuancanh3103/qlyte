package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.ApiOperation;
import me.coong.swagger.ApiPageable;
import me.coong.web.response.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vt.qlkdtt.yte.service.MedSaleFeeConfigService;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigInsertSdi;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigSearchSdi;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigUpdateSdi;
import vt.qlkdtt.yte.service.sdo.MedSaleFeeConfigSearchSdo;

@RestController
public class MedSaleFeeConfigController {

    @Autowired
    MedSaleFeeConfigService medSaleFeeConfigService;
    @PostMapping(value = "/insert-med-sale-fee-config")
    @ApiOperation(value = "insert-med-sale-fee-config")
    public ResponseEntity<Object> insertMedSaleFeeConfig(@RequestBody MedSaleFeeConfigInsertSdi medSaleFeeConfigInsertSdi) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(medSaleFeeConfigService.insertMedSaleFeeConfig(medSaleFeeConfigInsertSdi));
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "update-med-sale-fee-config")
    public ResponseEntity<Object> updateMedSaleFeeConfig(@RequestBody MedSaleFeeConfigUpdateSdi medSaleFeeConfigUpdateSdi) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(medSaleFeeConfigService.updateMedSaleFeeConfig(medSaleFeeConfigUpdateSdi));
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "delete-med-sale-fee-config")
    public ResponseEntity<Object> deleteMedSaleFeeConfig(@RequestParam Long feeConfigId){
        return ResponseEntity.status(HttpStatus.OK).body(medSaleFeeConfigService.deleteMedSaleFeeConfig(feeConfigId));
    }


    @GetMapping(value = "/search")
    @ApiOperation(value = "search-med-sale-fee-config")
    @ApiPageable
    public  ResponseEntity<PagedResponse<MedSaleFeeConfigSearchSdo>> searchMedSaleFeeConfig(@RequestParam(required = false) Long telecomServiceId,
                                                                                            @RequestParam(required = false) String channelTypeId,
                                                                                            @RequestParam(required = false) String productOfferCode,
                                                                                            @RequestParam(required = false) String createUser,
                                                                                            @RequestParam(required = false) String status,
                                                                                            @RequestParam(required = false) String fromDate,
                                                                                            @RequestParam(required = false) String toDate,
                                                                                            @ApiIgnore
                                                                                            @PageableDefault
                                                                                            Pageable pageRequest){
        MedSaleFeeConfigSearchSdi medSaleFeeConfigSearchSdi = new MedSaleFeeConfigSearchSdi();
        medSaleFeeConfigSearchSdi.setTelecomServiceId(telecomServiceId);
        medSaleFeeConfigSearchSdi.setChannelTypeId(channelTypeId);
        medSaleFeeConfigSearchSdi.setProductOfferCode(productOfferCode);
        medSaleFeeConfigSearchSdi.setCreateUser(createUser);
        medSaleFeeConfigSearchSdi.setStatus(status);
        medSaleFeeConfigSearchSdi.setFromDate(fromDate);
        medSaleFeeConfigSearchSdi.setToDate(toDate);
        return ResponseEntity.ok(PagedResponse.builder().page(medSaleFeeConfigService.searchMedSaleFeeConfig(medSaleFeeConfigSearchSdi, pageRequest)).build());
    }
}
