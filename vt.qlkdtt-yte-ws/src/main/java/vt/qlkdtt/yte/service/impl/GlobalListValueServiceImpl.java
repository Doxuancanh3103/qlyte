package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.repository.GlobalListValueRepo;
import vt.qlkdtt.yte.service.GlobalListValueService;
import vt.qlkdtt.yte.service.sdi.GlobalListValueInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListValueUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListValueSdo;

import java.text.ParseException;

@Service
public class GlobalListValueServiceImpl implements GlobalListValueService {
    @Autowired
    public GlobalListValueRepo globalListValueRepo;

    @Override
    public GlobalListValueSdo insert(GlobalListValueInsertSdi data) throws ParseException {
        return globalListValueRepo.insert(data);
    }

    @Override
    public int update(GlobalListValueUpdateSdi data) throws ParseException {
        return globalListValueRepo.update(data);
    }

    @Override
    public GlobalListValueSdo find(Long id) {
        return globalListValueRepo.find(id);
    }

    @Override
    public boolean delete(long id) {
        int deletedRows = globalListValueRepo.delete(id);
        return deletedRows > 0;
    }
}
