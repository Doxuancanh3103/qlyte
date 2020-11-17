package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.domain.DocumentMapping;

public interface DocMappingRepoCustom {
    void changeChangeStatusByObjectMapId(String status, Long objectMappingId, String objectType);

    DocumentMapping findByDocumentIdAndObjectId(Long documentId, Long objectId, String objectType);

    boolean isExist(long documentMappingId);
}
