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
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.service.CustomerOrderService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.CustomerOrderExtDueDateSdi;
import vt.qlkdtt.yte.service.sdi.CustomerOrderSdi;
import vt.qlkdtt.yte.service.sdi.CustomerOrderSearchSdi;
import vt.qlkdtt.yte.service.sdo.CustomerOrderDetailSearchSdo;
import vt.qlkdtt.yte.service.sdo.CustomerOrderSearchSdo;

@Slf4j
@RestController
@RequestMapping(value = "customerOrder")
@Api(value = "CustomerOrder")
public class CustomerOrderController {
    @Autowired
    CustomerOrderService customerOrderService;

    //<editor-fold desc="Search customer order">
    @GetMapping(value = "searchCustomerOrder")
    @ApiOperation(value = "search customer order")
    @ApiPageable
    public ResponseEntity<PagedResponse<CustomerOrderSearchSdo>> searchCustomerOrder(
            @RequestParam(value = "productId", required = false) String productId,
            @RequestParam(value = "orderType", required = false) String orderType,
            @RequestParam(value = "orderActionTypeId", required = false) String orderActionTypeId,
            @RequestParam(value = "orderContactName", required = false) String orderContactName,
            @RequestParam(value = "asigneeCode", required = false) String asigneeCode,
            @RequestParam(value = "provinceCode", required = false) String provinceCode,
            @RequestParam(value = "customerCodeName", required = false) String customerCodeName,
            @RequestParam(value = "customerOrderId", required = false) String customerOrderId,
            @RequestParam(value = "orderFromDate", required = false) String orderFromDate,
            @RequestParam(value = "orderToDate", required = false) String orderToDate,
            @RequestParam(value = "dueFromDate", required = false) String dueFromDate,
            @RequestParam(value = "dueToDate", required = false) String dueToDate,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "accountNo", required = false) String accountNo,
            @RequestParam(value = "accountServiceNo", required = false) String accountServiceNo,
            @ApiIgnore
            @PageableDefault
                    Pageable pageRequest) throws Exception {
        if (!DateUtil.isValidDate(orderFromDate)) {
            throw new AppException("API-CO001", "orderFromDate phai la dinh dang date (" + Const.DATE_FORMAT + ")");
        } else if (!DateUtil.isValidDate(orderToDate)) {
            throw new AppException("API-CO002", "orderToDate phai la dinh dang date (" + Const.DATE_FORMAT + ")");
        } else if (!DateUtil.isValidDate(dueFromDate)) {
            throw new AppException("API-CO003", "dueFomDate phai la dinh dang date (" + Const.DATE_FORMAT + ")");
        } else if (!DateUtil.isValidDate(dueToDate)) {
            throw new AppException("API-CO004", "dueToDate phai la dinh dang date (" + Const.DATE_FORMAT + ")");
        }

        CustomerOrderSearchSdi customerOrderSearchSdi = new CustomerOrderSearchSdi();
        customerOrderSearchSdi.setProductId(DataUtil.safeToLong(productId));
        customerOrderSearchSdi.setOrderType(orderType);
        customerOrderSearchSdi.setOrderActionTypeId(orderActionTypeId);
        customerOrderSearchSdi.setOrderContactName(orderContactName);
        customerOrderSearchSdi.setAssigneeCode(asigneeCode);
        customerOrderSearchSdi.setProvinceCode(provinceCode);
        customerOrderSearchSdi.setCustomerCodeName(customerCodeName);
        customerOrderSearchSdi.setCustomerOrderId(DataUtil.safeToLong(customerOrderId));
        customerOrderSearchSdi.setOrderFromDate(orderFromDate);
        customerOrderSearchSdi.setOrderToDate(orderToDate);
        customerOrderSearchSdi.setDueFromDate(dueFromDate);
        customerOrderSearchSdi.setDueToDate(dueToDate);
        customerOrderSearchSdi.setStatus(status);
        customerOrderSearchSdi.setAccountNo(accountNo);
        customerOrderSearchSdi.setAccountServiceNo(accountServiceNo);

        return ResponseEntity.ok(PagedResponse.builder().page(customerOrderService.searchCustomerOrder(customerOrderSearchSdi, pageRequest)).build());
    }
    //</editor-fold>

    //<editor-fold desc="Search order detail">
    @GetMapping(value = "searchOrderDetail")
    @ApiOperation(value = "search order detail")
    public ResponseEntity<CustomerOrderDetailSearchSdo> searchOrderDetail(
            @RequestParam(value = "customerOrderId", required = true) long customerOrderId) throws Exception {
        CustomerOrderDetailSearchSdo result = customerOrderService.searchOrderDetail(customerOrderId);

        if (DataUtil.isNullOrZero(result.getCustomerOrderId())) {
            throw new AppException("API-", "customerOrderId [" + customerOrderId + "] not exist ");
        }

        return ResponseEntity.ok(result);
    }
    //</editor-fold>

    //<editor-fold desc="Extend date">
    @PostMapping(value = "extendDueDate")
    @ApiOperation(value = "extend customer order due date")
    public ResponseEntity<Boolean> extendOrderDueDate(
            @RequestBody CustomerOrderExtDueDateSdi customerOrderExtDueDateSdi
    ) throws Exception {
        if (customerOrderExtDueDateSdi != null && !DateUtil.isValidDate(customerOrderExtDueDateSdi.getExtendDueDate())) {
            throw new AppException("API-CO005", "extendDueDate phai la dinh dang date (" + Const.DATE_FORMAT + ")");
        }
        boolean result = customerOrderService.extendOrderDueDate(customerOrderExtDueDateSdi);
        return ResponseEntity.ok(result);
    }
    //</editor-fold>

    //<editor-fold desc="Acceptance charge">
    @PostMapping(value = "acceptanceCharge")
    @ApiOperation(value = "acceptance charge")
    public ResponseEntity<Boolean> acceptanceCharge(
            @RequestBody CustomerOrderSdi customerOrderSdi
    ) throws Exception {
        if (customerOrderSdi == null || DataUtil.isNullOrZero(customerOrderSdi.getCustomerOrderId())) {
            throw new AppException("API-CO014", "customerOrderId bat buoc nhap");
        }
        boolean result = customerOrderService.acceptanceCharge(customerOrderSdi.getCustomerOrderId());
        return ResponseEntity.ok(result);
    }
    //</editor-fold>

    //<editor-fold desc="Reject connect">
    @PostMapping(value = "rejectConnect")
    @ApiOperation(value = "reject connect by not accpectance charge")
    public ResponseEntity<Boolean> rejectConnect(
            @RequestBody CustomerOrderSdi customerOrderSdi
    ) throws Exception {
        if (customerOrderSdi == null || DataUtil.isNullOrZero(customerOrderSdi.getCustomerOrderId())) {
            throw new AppException("API-CO014", "customerOrderId bat buoc nhap");
        }
        boolean result = customerOrderService.rejectConnect(customerOrderSdi.getCustomerOrderId());
        return ResponseEntity.ok(result);
    }
    //</editor-fold>
}
