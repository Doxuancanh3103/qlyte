package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.repository.CustomerExtMedicalRepo;
import vt.qlkdtt.yte.service.CustomerExtMedicalService;

@Service
@Transactional
public class CustomerExtMedicalServiceImpl implements CustomerExtMedicalService {
    @Autowired
    CustomerExtMedicalRepo customerExtMedicalRepo;

    @Override
    public boolean isExist(String medicalPermissionNo) {
        return customerExtMedicalRepo.isExist(medicalPermissionNo);
    }
}
