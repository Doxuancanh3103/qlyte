package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.CustomerOrderExtDueDateSdi;
import vt.qlkdtt.yte.service.sdi.CustomerOrderSearchSdi;
import vt.qlkdtt.yte.service.sdo.CustomerOrderDetailSearchSdo;
import vt.qlkdtt.yte.service.sdo.CustomerOrderSearchSdo;

public interface CustomerOrderService {
    Page<CustomerOrderSearchSdo> searchCustomerOrder(CustomerOrderSearchSdi customerOrderSearchSdi, Pageable pageable) throws Exception;

    CustomerOrderDetailSearchSdo searchOrderDetail(long customerOrderId) throws Exception;

    boolean extendOrderDueDate(CustomerOrderExtDueDateSdi customerOrderExtDueDateSdi) throws Exception;

    boolean acceptanceCharge(long customerOrderId) throws Exception;

    boolean rejectConnect(long customerOrderId) throws Exception;
}
