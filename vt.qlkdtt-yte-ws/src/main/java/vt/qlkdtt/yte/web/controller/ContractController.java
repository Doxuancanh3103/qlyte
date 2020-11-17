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
import vt.qlkdtt.yte.domain.CustomerContract;
import vt.qlkdtt.yte.repository.ContractRepo;
import vt.qlkdtt.yte.service.ContractService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.ContractSearchSdi;
import vt.qlkdtt.yte.service.sdi.ContractUpdateSdi;
import vt.qlkdtt.yte.service.sdo.ContractFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ContractSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchSdo;

@Slf4j
@RestController
@RequestMapping(value = "customerContract")
@Api(value = "customerContract")
public class ContractController {
    @Autowired
    ContractService contractService;

    @Autowired
    ContractRepo contractRepo;

    //<editor-fold desc="Search contract">
    @GetMapping(value = "searchContract")
    @ApiOperation(value = "search contract")
    @ApiPageable
    public ResponseEntity<PagedResponse<ContractSearchSdo>> searchContract(
            @RequestParam(value = "productGroupId", required = false) String productGroupId,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "contractNo", required = false) String contractNo,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "accountNo", required = false) String accountNo,
            @RequestParam(value = "accountServiceNo", required = false) String accountServiceNo,
            @ApiIgnore
            @PageableDefault
                    Pageable pageRequest) throws Exception {
        ContractSearchSdi contractSearchSdi = new ContractSearchSdi();
        contractSearchSdi.setProductGroupId(DataUtil.safeToLong(productGroupId));
        contractSearchSdi.setProductId(productId);
        contractSearchSdi.setProvince(province);
        contractSearchSdi.setDistrict(district);
        contractSearchSdi.setFromDate(fromDate);
        contractSearchSdi.setToDate(toDate);
        contractSearchSdi.setContractNo(contractNo);
        contractSearchSdi.setCustomerName(customerName);
        contractSearchSdi.setAccountNo(accountNo);
        contractSearchSdi.setAccountServiceNo(accountServiceNo);

        Page<ContractSearchSdo> result = contractService.searchContract(contractSearchSdi, pageRequest);

        if (result != null) {
            return ResponseEntity.ok(PagedResponse.builder().page(result).build());
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Search contract by customer">
    @GetMapping(value = "searchContractByCustomer")
    @ApiOperation(value = "search contract by customer")
    @ApiPageable
    public ResponseEntity<PagedResponse<ContractSearchConnectSdo>> searchContractByCustomer(
            @RequestParam(value = "productGroupId", required = false) String productGroupId,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "contractNo", required = false) String contractNo,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "accountNo", required = false) String accountNo,
            @RequestParam(value = "accountServiceNo", required = false) String accountServiceNo,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @ApiIgnore
            @PageableDefault
                    Pageable pageRequest) throws Exception {
        ContractSearchSdi contractSearchSdi = new ContractSearchSdi();
        contractSearchSdi.setProductGroupId(DataUtil.safeToLong(productGroupId));
        contractSearchSdi.setProductId(productId);
        contractSearchSdi.setProvince(province);
        contractSearchSdi.setDistrict(district);
        contractSearchSdi.setFromDate(fromDate);
        contractSearchSdi.setToDate(toDate);
        contractSearchSdi.setContractNo(contractNo);
        contractSearchSdi.setCustomerName(customerName);
        contractSearchSdi.setAccountNo(accountNo);
        contractSearchSdi.setAccountServiceNo(accountServiceNo);
        contractSearchSdi.setCustomerId(customerId);

        Page<ContractSearchConnectSdo> result = contractService.searchContractByCustomer(contractSearchSdi, pageRequest);

        if (result != null) {
            return ResponseEntity.ok(PagedResponse.builder().page(result).build());
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @GetMapping(value = "findById")
    @ApiOperation(value = "find contract by id")
    public ResponseEntity<ContractFindByIdSdo> findById(
            @RequestParam(value = "customerContractId", required = true) long customerContractId) {
        ContractFindByIdSdo contractFindByIdSdo = contractService.findById(customerContractId);
        if (contractFindByIdSdo == null || DataUtil.isNullOrZero(contractFindByIdSdo.getContractId())) {
            throw new AppException("API-ERR001", "Khong ton tai id tren he thong");
        }
        return ResponseEntity.ok(contractFindByIdSdo);
    }
    //</editor-fold>

    //<editor-fold desc="Update">
    @PostMapping(value = "updateContract")
    @ApiOperation(value = "update contract")
    public ResponseEntity<ContractSdo> updateContract(
            @RequestBody ContractUpdateSdi contractUpdateSdi
    ) {
        contractRepo.isExits("HD300501", 57);

        CustomerContract customerContract = contractService.updateContract(contractUpdateSdi);

        if (customerContract != null && !DataUtil.isNullOrZero(customerContract.getContractId())) {
            ContractSdo contractSdo = new ContractSdo();
            return ResponseEntity.ok(contractSdo.toContractSdo(customerContract));
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Liquidate">
    @PostMapping(value = "liquidateContract")
    @ApiOperation(value = "liquidate contract")
    public ResponseEntity<ContractSdo> liquidateContract(
            @RequestBody ContractSdo contractSdi
    ) {
        log.info("REQ liquidateContract ...  {}", contractSdi);
        CustomerContract customerContract = contractService.liquidateContract(contractSdi.getContractId());

        if (customerContract != null && !DataUtil.isNullOrZero(customerContract.getContractId())) {
            ContractSdo contractSdo = new ContractSdo();
            return ResponseEntity.ok(contractSdo.toContractSdo(customerContract));
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>

    //<editor-fold desc="Extension">
    @PostMapping(value = "extensionContract")
    @ApiOperation(value = "extension contract")
    public ResponseEntity<ContractSdo> extensionContract(
            @RequestBody ContractSdo contractSdi
    ) {
        log.info("REQ liquidateContract ...  {}", contractSdi);
        CustomerContract customerContract = contractService.extensionContract(contractSdi.getContractId());

        if (customerContract != null && !DataUtil.isNullOrZero(customerContract.getContractId())) {
            ContractSdo contractSdo = new ContractSdo();
            return ResponseEntity.ok(contractSdo.toContractSdo(customerContract));
        }
        return ResponseEntity.noContent().build();
    }
    //</editor-fold>
}
