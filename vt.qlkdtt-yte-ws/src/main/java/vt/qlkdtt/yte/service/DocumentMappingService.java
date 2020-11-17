package vt.qlkdtt.yte.service;

import vt.qlkdtt.yte.domain.DocumentMapping;

public interface DocumentMappingService {
    void changeChangeStatusByObjectMapId(String status, Long objectMappingId, String objectType);

    DocumentMapping findByDocumentIdAndObjectId(Long documentId, Long objectId, String objectType);

    boolean isExist(long documentMappingId);
}
