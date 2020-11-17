package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveInsertSdi;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveSearchSdi;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveUpdateSdi;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveInsertSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchSdo;

import java.util.Map;

public interface CustomerPropectiveService {
    void changeStatusByProductOffer(String status, long productOfferId);

    boolean isExist(long customerPropectiveId);

    Page<CustomerPropectiveSearchSdo> searchCustomerPropective(CustomerPropectiveSearchSdi sdi, Pageable pageable);

    CustomerPropectiveSearchByIdSdo searchById(Long customerPropectiveId);

    Map<String, Object> changeStatus(Long customerPropectiveId, String status);

    CustomerPropectiveInsertSdo createCustomerPropective(CustomerPropectiveInsertSdi dataInsert);

    CustomerPropectiveInsertSdo updateCustomerPropective(CustomerPropectiveUpdateSdi dataUpdate);
}
