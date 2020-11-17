package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.domain.CustomerPropective;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveSearchSdi;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchSdo;

import java.util.Map;

public interface CustomerPropectiveRepoCustom {
    void changeStatusByProductOffer(String status, long productOfferId);

    boolean isExist(long customerPropectiveId);

    boolean isExistMedicalPermissionNo(String medicalPermissionNo);

    boolean isExistMedicalPermissionNo(String medicalPermissionNo, Long customerPropectiveId);

    boolean isExistIdentityNo(String identityNo);

    boolean isExistIdentityNo(String identityNo, Long customerPropectiveId);

    Page<CustomerPropectiveSearchSdo> searchCustomerPropective(CustomerPropectiveSearchSdi sdi, Pageable pageable);

    CustomerPropectiveSearchByIdSdo searchById(Long customerPropectiveId);

    Map<String, Object> changeStatus(Long customerPropectiveId, String status);

    CustomerPropective searchByIdentityNo(String identityNo);

    CustomerPropective searchByCustomerName(String customerName);

    void updateAfterConnect(String cpCode, Long cpId, Long customerId);

    String getCustomerPropectiveCode();
}
