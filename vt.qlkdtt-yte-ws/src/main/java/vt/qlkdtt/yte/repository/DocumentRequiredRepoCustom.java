package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.service.sdo.DocumentRequiredSearchSdo;

import java.util.List;

public interface DocumentRequiredRepoCustom {
    List<DocumentRequiredSearchSdo> searchDocumentRequired(String orderType, String actionType, Long productId, String productGroupId);
}
