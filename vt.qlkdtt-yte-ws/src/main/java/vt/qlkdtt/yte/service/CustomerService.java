package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.CustomerSearchAfterSaleSdi;
import vt.qlkdtt.yte.service.sdi.CustomerUpdateSdi;
import vt.qlkdtt.yte.service.sdo.CustomerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerSearchAfterSaleSdo;
import vt.qlkdtt.yte.service.sdo.CustomerSearchSdo;
import vt.qlkdtt.yte.service.sdo.CustomerUpdateSdo;

import java.text.ParseException;
import java.util.List;

public interface CustomerService {
    Page<CustomerSearchSdo> searchCustomerConnection(String customerBusType, String customerName, String accountServiceNo,
                                                     String customerIdentityType, String customerIdentityNo, Pageable pageRequest);

    Page<CustomerSearchAfterSaleSdo> searchCustomerAfterSale(CustomerSearchAfterSaleSdi dataSearch, Pageable pageRequest);

    CustomerFindByIdSdo findById(Long customerId) throws ParseException, IllegalAccessException;

    CustomerUpdateSdo update(CustomerUpdateSdi dataUpdate);
}
