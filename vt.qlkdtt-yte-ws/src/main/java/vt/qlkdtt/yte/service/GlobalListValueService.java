package vt.qlkdtt.yte.service;

import vt.qlkdtt.yte.service.sdi.GlobalListValueInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListValueSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListValueUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListValueSdo;

import java.text.ParseException;

public interface GlobalListValueService {
    public GlobalListValueSdo find(Long id);
    public GlobalListValueSdo insert(GlobalListValueInsertSdi data) throws ParseException;
    public int update(GlobalListValueUpdateSdi data) throws ParseException;
    public boolean delete(long id);
}
