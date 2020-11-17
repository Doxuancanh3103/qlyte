package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.repository.DocumentRequiredRepo;
import vt.qlkdtt.yte.service.DocumentRequiredService;
import vt.qlkdtt.yte.service.sdo.DocumentRequiredSearchSdo;

import java.util.List;

@Service
@Transactional
public class DocumentRequiredServiceImpl implements DocumentRequiredService {
    @Autowired
    DocumentRequiredRepo documentRequiredRepo;

    @Override
    public List<DocumentRequiredSearchSdo> searchDocumentRequired(String orderType, String actionType, Long productId, String productGroupId) {
        return documentRequiredRepo.searchDocumentRequired(orderType, actionType, productId, productGroupId);
    }
}
