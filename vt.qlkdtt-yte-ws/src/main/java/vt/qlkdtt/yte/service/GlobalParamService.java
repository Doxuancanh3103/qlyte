package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import vt.qlkdtt.yte.domain.GlobalParam;
import vt.qlkdtt.yte.service.sdi.GenerateAccountSdi;
import vt.qlkdtt.yte.service.sdi.GlobalParamInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalParamUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalParamInsertSdo;
import vt.qlkdtt.yte.service.sdo.GlobalParamSearchSdo;

import java.util.List;

public interface GlobalParamService {
    List<GlobalParam> findByCode(String code);

    GlobalParam findByCodeAndProduct(String code, Long productId);

    Long getMaxIndexOfIsdn(String prefix);

    boolean changeStatus(Long globalParamId, String status);

    GlobalParamInsertSdo createGlobalParam(GlobalParamInsertSdi dataInsert);

    GlobalParamInsertSdo updateGlobalParam(GlobalParamUpdateSdi dataUpdate);

    String generateAccount(GenerateAccountSdi generateAccountSdi) throws Exception;

    String generateAccountService(GenerateAccountSdi generateAccountSdi) throws Exception;
}
