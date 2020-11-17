package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.domain.CustomerOrder;
import vt.qlkdtt.yte.service.sdi.CustomerOrderSearchSdi;
import vt.qlkdtt.yte.service.sdo.CustomerOrderDetailSearchSdo;
import vt.qlkdtt.yte.service.sdo.CustomerOrderSearchSdo;

public interface CustomerOrderRepoCustom {
    Page<CustomerOrderSearchSdo> searchCustomerOrder(CustomerOrderSearchSdi customerOrderSearchSdi, Pageable page);

    CustomerOrderDetailSearchSdo searchOrderDetail(long customerOrderId);

    CustomerOrder searchById(long customerId);
}
