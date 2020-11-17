package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.service.GlobalListService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.GlobalListInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListInsertSdo;
import vt.qlkdtt.yte.domain.GlobalList;
import vt.qlkdtt.yte.repository.GlobalListRepo;
import vt.qlkdtt.yte.service.sdo.GlobalListSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListSearchSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListUpdateSdo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GlobalListServiceImpl implements GlobalListService {
    @Autowired
    GlobalListRepo globalListRepo;

    public Page<GlobalListSdo> get(String keyword, Pageable pageRequest) throws ParseException {
        return globalListRepo.get(keyword, pageRequest);
    }

    @Override
    public List<GlobalListSearchSdo> findByCode(String code, Long productId, String productGroupId) {
        return globalListRepo.findByCode(code, productId, productGroupId);
    }

    @Override
    public GlobalListSearchSdo findById(long id) {
        return globalListRepo.findById(id);
    }

    @Override
    public Map<String, Object> changeStatus(long id, String status) {
        return globalListRepo.changeStatus(id, status);
    }

    @Override
    public int update(GlobalListUpdateSdi sdi) {
        return globalListRepo.update(sdi);
    }

    @Override
    public GlobalListInsertSdo insert(GlobalListInsertSdi sdi) {
        validate(sdi);
        GlobalList globalList = sdi.toOptionSet();

        GlobalList result = globalListRepo.save(globalList);

        return result.toGlobalListInsertSdo();
    }

    @Override
    public boolean delete(Long id) {
        int deletedRows = globalListRepo.delete(id);
        return deletedRows > 0;
    }

    @Override
    public boolean isExist(String globalListValue, String globalListType) {
        return globalListRepo.isExist(globalListValue, globalListType);
    }

    /**
     * Validate the global list data for creating
     * @param sdi
     */
    private void validate(GlobalListInsertSdi sdi) {
        // Code
        if (DataUtil.isStringNullOrEmpty(sdi.getCode())) {
            throw new AppException("API-GL001", "Global code is required");
        }

        // Status
        if (DataUtil.isStringNullOrEmpty(sdi.getStatus())) {
            throw new AppException("API-GL002", "Global status is required");
        }

        // Create user
        if (DataUtil.isStringNullOrEmpty(sdi.getCreateUser())) {
            throw new AppException("API-GL003", "Create user is required");
        }
    }
}
