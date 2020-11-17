package vt.qlkdtt.yte.repository;

import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.domain.GlobalParam;
import vt.qlkdtt.yte.service.sdi.GlobalParamInsertSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListSearchSdo;
import vt.qlkdtt.yte.service.sdo.GlobalParamInsertSdo;

import java.util.List;

@Transactional
public interface GlobalParamRepoCustom {
    List<GlobalParam> findByCode(String code);

    GlobalParam findByCodeAndProduct(String code, Long productId);

    Long getMaxIndexOfIsdn(String prefix);

    boolean changeStatus(Long globalParamId, String status);
}
