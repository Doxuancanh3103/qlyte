package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.domain.DocumentMapping;
import vt.qlkdtt.yte.repository.DocumentMappingRepo;
import vt.qlkdtt.yte.service.DocumentMappingService;

@Service
@Transactional
public class DocumentMappingServiceImpl implements DocumentMappingService {
    @Autowired
    DocumentMappingRepo docMappingRepo;

    @Override
    public void changeChangeStatusByObjectMapId(String status, Long objectMappingId, String objectType) {
        docMappingRepo.changeChangeStatusByObjectMapId(status, objectMappingId, objectType);
    }

    @Override
    public DocumentMapping findByDocumentIdAndObjectId(Long documentId, Long objectId, String objectType) {
        return docMappingRepo.findByDocumentIdAndObjectId(documentId, objectId, objectType);
    }

    @Override
    public boolean isExist(long documentMappingId) {
        return docMappingRepo.isExist(documentMappingId);
    }
}
