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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.CustomerService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.CustomerSearchAfterSaleSdi;
import vt.qlkdtt.yte.service.sdi.CustomerUpdateSdi;
import vt.qlkdtt.yte.service.sdo.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "customer")
@Api(value = "Customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    //<editor-fold desc="Search customer connection">
    @GetMapping(value = "searchCustomerConnection")
    @ApiOperation(value = "Search customer connection")
    @ApiPageable
    public ResponseEntity<PagedResponse<CustomerSearchSdo>> searchCustomerConnection(
            @RequestParam(required = false) String customerBusType,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String accountServiceNo,
            @RequestParam(required = false) String customerIdentityType,
            @RequestParam(required = false) String customerIdentityNo,
            @ApiIgnore @PageableDefault Pageable pageRequest
    ) {
        Page<CustomerSearchSdo> result = customerService.searchCustomerConnection(customerBusType, customerName, accountServiceNo,
                customerIdentityType, customerIdentityNo, pageRequest);
        if (result != null) {
            return ResponseEntity.ok(PagedResponse.builder().page(result).build());
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Search customer after sale">
    @GetMapping(value = "searchCustomerAfterSale")
    @ApiOperation(value = "Search customer after sale")
    @ApiPageable
    public ResponseEntity<PagedResponse<CustomerSearchAfterSaleSdo>> searchCustomerAfterSale(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String accountNo,
            @RequestParam(required = false) String accountServiceNo,
            @RequestParam(required = false) String customerIdentityNo,

            @RequestParam(required = false) String productGroup,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String customerBusType,
            @RequestParam(required = false) String medicalNo,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @ApiIgnore @PageableDefault Pageable pageRequest
    ) {
        try {
            CustomerSearchAfterSaleSdi dataSearch = new CustomerSearchAfterSaleSdi();
            dataSearch.setProductId(productId);
            dataSearch.setCustomerName(customerName);
            dataSearch.setAccountNo(accountNo);
            dataSearch.setAccountServiceNo(accountServiceNo);
            dataSearch.setCustomerIdentityNo(customerIdentityNo);

            dataSearch.setProductGroup(productGroup);
            dataSearch.setProductType(productType);
            dataSearch.setProvince(province);
            dataSearch.setDistrict(district);
            dataSearch.setCustomerBusType(customerBusType);
            dataSearch.setMedicalNo(medicalNo);
            dataSearch.setTel(tel);
            dataSearch.setFromDate(fromDate);
            dataSearch.setToDate(toDate);

            Page<CustomerSearchAfterSaleSdo> result = customerService.searchCustomerAfterSale(dataSearch, pageRequest);
            return ResponseEntity.ok(PagedResponse.builder().page(result).build());
        } catch (Exception e) {
            log.error(e.getMessage() != null ? e.getMessage() : "");
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @GetMapping(value = "findById")
    @ApiOperation(value = "Search customer by id")
    public ResponseEntity<CustomerFindByIdSdo> findById(
            @RequestParam Long customerId
    ) throws IllegalAccessException, ParseException {
        CustomerFindByIdSdo result = customerService.findById(customerId);

        if (result != null && !DataUtil.isNullOrZero(result.getCustomerId())) {
            return ResponseEntity.ok(result);
        } else {
            List<String> lstError = new ArrayList<>();
            lstError.add(DataUtil.safeToString(customerId));

            throw new AppException("API-CU001", "customerId " + customerId + " not exist", lstError);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update customer">
    @PostMapping(value = "update")
    @ApiOperation(value = "Update customer")
    public ResponseEntity<CustomerUpdateSdo> update(@RequestBody CustomerUpdateSdi dataUpdate) {
        CustomerUpdateSdo result = customerService.update(dataUpdate);

        if (result != null && !DataUtil.isNullOrZero(result.getCustomerId())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
