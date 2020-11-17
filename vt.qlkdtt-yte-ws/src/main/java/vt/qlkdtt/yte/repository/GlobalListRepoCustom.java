package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.domain.GlobalList;
import vt.qlkdtt.yte.service.sdi.GlobalListUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListSearchSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListUpdateSdo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Transactional
public interface GlobalListRepoCustom {
    Page<GlobalListSdo> get(String keyword, Pageable pageable) throws ParseException;

    List<GlobalListSearchSdo> findByCode(String code, Long productId, String productGroupId);

    GlobalListSearchSdo findById(long id);

    GlobalListSdo find(long id);

    Map<String, Object> changeStatus(long id, String status);

    int update(GlobalListUpdateSdi sdi);

    int delete(long id);

    int deleteGlobalListValuesByGlobalListId(long id);

    long getGlobalCustomerTypeId();

    List<Long> getGlobalListValueIdsByProductId(long productId);

    boolean isExist(String globalListValue, String globalListType);
}
