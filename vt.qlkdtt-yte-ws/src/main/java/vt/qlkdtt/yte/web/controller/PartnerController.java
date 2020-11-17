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
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.Partner;
import vt.qlkdtt.yte.service.PartnerService;
import vt.qlkdtt.yte.service.sdi.PartnerChangeStatusSdi;
import vt.qlkdtt.yte.service.sdi.PartnerCreateSdi;
import vt.qlkdtt.yte.service.sdi.PartnerUpdateSdi;
import vt.qlkdtt.yte.service.sdi.PartnerSearchSdi;
import vt.qlkdtt.yte.service.sdo.PartnerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.PartnerSdo;
import vt.qlkdtt.yte.service.sdo.PartnerSearchSdo;

@Slf4j
@RestController
@RequestMapping(value = "partner")
@Api(value = "Partner")
public class PartnerController {
    @Autowired
    PartnerService partnerService;

    //<editor-fold desc="Search partner">
    @GetMapping(value = "searchPartner")
    @ApiOperation(value = "search partner by idNo and name")
    @ApiPageable
    public ResponseEntity<PagedResponse<PartnerSearchSdo>> searchPartner(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "idType", required = false) String idType,
            @RequestParam(value = "idNo", required = false) String idNo,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @ApiIgnore
            @PageableDefault
                    Pageable pageRequest) {
        try {
            PartnerSearchSdi partnerSearchSdi = new PartnerSearchSdi();
            partnerSearchSdi.setCode(code);
            partnerSearchSdi.setName(name);
            partnerSearchSdi.setIdType(idType);
            partnerSearchSdi.setIdNo(idNo);
            partnerSearchSdi.setStatus(status);
            partnerSearchSdi.setFromDate(fromDate);
            partnerSearchSdi.setToDate(toDate);

            return ResponseEntity.ok(PagedResponse.builder().page(partnerService.searchPartner(partnerSearchSdi, pageRequest)).build());
        } catch (Exception ex) {
            log.error(ex.getMessage() != null ? ex.getMessage() : "");
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Create partner">
    @PostMapping(value = "createPartner")
    @ApiOperation(value = "create new partner")
    public ResponseEntity<PartnerSdo> createPartner(
            @RequestBody PartnerCreateSdi partnerCreateSdi
    ) {
        Partner partner = partnerService.createPartner(partnerCreateSdi.toPartner());
        if (partner != null && !DataUtil.isNullOrZero(partner.getPartnerId())) {
            PartnerSdo partnerSdo = new PartnerSdo();
            return ResponseEntity.ok(partnerSdo.toPartnerSdo(partner));
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Update partner">
    @PostMapping(value = "updatePartner")
    @ApiOperation(value = "update partner")
    public ResponseEntity<PartnerSdo> updatePartner(
            @RequestBody PartnerUpdateSdi partnerUpdateSdi
    ) {
        Partner partner = partnerService.updatePartner(partnerUpdateSdi.toPartner());

        if (partner != null && !DataUtil.isNullOrZero(partner.getPartnerId())) {
            PartnerSdo partnerSdo = new PartnerSdo();
            return ResponseEntity.ok(partnerSdo.toPartnerSdo(partner));
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Change partner status">
    @PostMapping(value = "changePartnerStatus")
    @ApiOperation(value = "change partner status")
    public ResponseEntity<Boolean> changePartnerStatus(
            @RequestBody PartnerChangeStatusSdi partnerChangeStatusSdi
    ) {
        log.info("REQ ...  {}", partnerChangeStatusSdi);
        boolean result = partnerService.changePartnerStatus(partnerChangeStatusSdi.getPartnerId(), partnerChangeStatusSdi.isActive(), partnerChangeStatusSdi.getUser());
        log.info("RES ... {}", result);
        return ResponseEntity.ok(result);
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @GetMapping(value = "findById")
    @ApiOperation(value = "find partner by id")
    public ResponseEntity<PartnerFindByIdSdo> findById(
            @RequestParam(value = "partnerId", required = true) long partnerId) {
        PartnerFindByIdSdo partnerFindByIdSdo = partnerService.findById(partnerId);
        if (partnerFindByIdSdo != null && !DataUtil.isNullOrZero(partnerFindByIdSdo.getPartnerId())) {
            return ResponseEntity.ok(partnerFindByIdSdo);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
