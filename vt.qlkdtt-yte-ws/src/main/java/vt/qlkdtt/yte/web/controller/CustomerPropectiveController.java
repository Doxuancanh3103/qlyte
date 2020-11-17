package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.coong.swagger.ApiPageable;
import me.coong.web.response.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.CustomerPropectiveService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveInsertSdi;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveSearchSdi;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveUpdateSdi;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveInsertSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchSdo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "customer-propective")
@Api(value = "Customer propective")
public class CustomerPropectiveController {
    @Autowired
    CustomerPropectiveService customerPropectiveService;

    //<editor-fold desc="Search customer propective">
    @GetMapping(value = "searchCustomerPropective")
    @ApiOperation(value = "Search customer propective")
    @ApiPageable
    public ResponseEntity<PagedResponse<CustomerPropectiveSearchSdo>> searchCustomerPropective(
            @RequestParam(required = false) String customerBusType,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String identityNo,
            @RequestParam(required = false) String areaCodeProvince,
            @RequestParam(required = false) String areaCodeDistrict,
            @RequestParam(required = false) String areaCodePrecinct,
            @RequestParam(required = false) String accountNo,
            @RequestParam(required = false) String accountServiceNo,
            @RequestParam(required = false) String status,
            @ApiIgnore @PageableDefault Pageable pageRequest
    ) {
        CustomerPropectiveSearchSdi sdi = new CustomerPropectiveSearchSdi();
        sdi.setCustomerBusType(customerBusType);
        sdi.setCustomerName(customerName);
        sdi.setIdentityNo(identityNo);
        sdi.setAreaCodeProvince(areaCodeProvince);
        sdi.setAreaCodeDistrict(areaCodeDistrict);
        sdi.setAreaCodePrecinct(areaCodePrecinct);
        sdi.setAccountNo(accountNo);
        sdi.setAccountServiceNo(accountServiceNo);
        sdi.setStatus(status);

        Page<CustomerPropectiveSearchSdo> result = customerPropectiveService.searchCustomerPropective(sdi, pageRequest);

        if (result != null) {
            return ResponseEntity.ok(PagedResponse.builder().page(result).build());
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Search customer propective by id">
    @GetMapping(value = "searchById")
    @ApiOperation(value = "Search customer propective by id")
    public ResponseEntity<CustomerPropectiveSearchByIdSdo> searchById(@RequestParam Long customerPropectiveId) {
        CustomerPropectiveSearchByIdSdo result = customerPropectiveService.searchById(customerPropectiveId);

        if (result != null && !DataUtil.isNullOrZero(result.getCustomerPropectiveId())) {
            return ResponseEntity.ok(result);
        } else {
            List<String> lstError = new ArrayList<>();
            lstError.add(DataUtil.safeToString(customerPropectiveId));

            throw new AppException("API-CP003", "customerPropectiveId " + customerPropectiveId + " not exist", lstError);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @GetMapping(value = "changeStatus")
    @ApiOperation(value = "Change status")
    public ResponseEntity<Map<String, Object>> changeStatus(
            @RequestParam Long customerPropectiveId,
            @RequestParam String status
    ) {
        Map<String, Object> result = customerPropectiveService.changeStatus(customerPropectiveId, status);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Create customer propective">
    @PostMapping(value = "createCustomerPropective")
    @ApiOperation(value = "Create customer propective")
    public ResponseEntity<CustomerPropectiveInsertSdo> createCustomerPropective(@RequestBody CustomerPropectiveInsertSdi dataInsert) {
        CustomerPropectiveInsertSdo result = customerPropectiveService.createCustomerPropective(dataInsert);

        if (result != null && !DataUtil.isNullOrZero(result.getCustomerPropectiveId())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Update customer propective">
    @PostMapping(value = "updateCustomerPropective")
    @ApiOperation(value = "Update customer propective")
    public ResponseEntity<CustomerPropectiveInsertSdo> updateCustomerPropective(@RequestBody CustomerPropectiveUpdateSdi dataUpdate) {
        CustomerPropectiveInsertSdo result = customerPropectiveService.updateCustomerPropective(dataUpdate);

        if (result != null && !DataUtil.isNullOrZero(result.getCustomerPropectiveId())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
