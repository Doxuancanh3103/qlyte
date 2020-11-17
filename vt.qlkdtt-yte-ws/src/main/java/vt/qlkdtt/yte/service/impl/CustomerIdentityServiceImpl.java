package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.repository.CustomerIdentityRepo;
import vt.qlkdtt.yte.service.CustomerIdentityService;

@Service
@Transactional
public class CustomerIdentityServiceImpl implements CustomerIdentityService {
    @Autowired
    CustomerIdentityRepo customerIdentityRepo;

    @Override
    public boolean isExist(String customerIdentityNo) {
        return customerIdentityRepo.isExist(customerIdentityNo);
    }
}
