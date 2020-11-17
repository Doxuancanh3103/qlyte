package vt.qlkdtt.yte.repository;


import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.service.sdi.GlobalListValueInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListValueUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListValueSdo;

import java.text.ParseException;

@Transactional
public interface GlobalListValueRepoCustom {
    public GlobalListValueSdo insert(GlobalListValueInsertSdi data) throws ParseException;
    public int update(GlobalListValueUpdateSdi data);
    public GlobalListValueSdo find(long id);
    public int delete(long id);
}
