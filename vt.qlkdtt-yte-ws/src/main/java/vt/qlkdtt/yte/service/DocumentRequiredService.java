package vt.qlkdtt.yte.service;

import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.service.sdo.DocumentRequiredSearchSdo;

import java.util.List;


public interface DocumentRequiredService {
    List<DocumentRequiredSearchSdo> searchDocumentRequired(String orderType, String actionType, Long productId, String productGroupId);
}
