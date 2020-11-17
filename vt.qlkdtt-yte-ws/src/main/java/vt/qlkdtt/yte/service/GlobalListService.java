package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.GlobalListInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListInsertSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListUpdateSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListSearchSdo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface GlobalListService {
    Page<GlobalListSdo> get(String keyword, Pageable pageRequest) throws ParseException;

    List<GlobalListSearchSdo> findByCode(String code, Long productId, String productGroupId);

    GlobalListSearchSdo findById(long id);

    Map<String, Object> changeStatus(long id, String status);

    int update(GlobalListUpdateSdi sdi);

    GlobalListInsertSdo insert(GlobalListInsertSdi sdi);

    boolean delete(Long id);

    boolean isExist(String globalListValue, String globalListType);
}
