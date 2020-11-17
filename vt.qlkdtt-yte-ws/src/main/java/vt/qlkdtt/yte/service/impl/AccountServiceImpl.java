package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.domain.CustomerAccount;
import vt.qlkdtt.yte.repository.AccountRepo;
import vt.qlkdtt.yte.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepo accountRepo;

    @Override
    public boolean isHadAccount(Long customerId) {
        return accountRepo.isHadAccount(customerId);
    }

//    @Override
//    public CustomerAccount findByCustomerId(Long customerId) {
//        return accountRepo.findByCustomerId(customerId);
//    }
}
