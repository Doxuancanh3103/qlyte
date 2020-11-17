package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.domain.CustomerAccount;

public interface AccountRepoCustom {
    boolean isHadAccount(Long customerId);

//    CustomerAccount findByCustomerId(Long customerId);
}
