package vt.qlkdtt.yte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.service.sdi.DocumentInsertSdi;
import vt.qlkdtt.yte.service.sdi.DocumentUpdateSdi;
import vt.qlkdtt.yte.service.sdo.DocumentInsertSdo;
import vt.qlkdtt.yte.service.sdo.DocumentSearchSdo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DocumentService {
    Page<DocumentSearchSdo> findAll(Pageable pageable) throws ParseException;

    DocumentSearchSdo findById(Long documentId) throws ParseException;

    List<DocumentSearchSdo> searchDocument(String documentCodeName) throws ParseException;

    Map<String, Object> changeStatus(Long documentId, String status);

    DocumentInsertSdo update(DocumentUpdateSdi documentUpdate);

    DocumentInsertSdo insert(DocumentInsertSdi sdi);

    void changeStatusByProductId(String status, long productId);

    boolean isExist(Long documentId);

    boolean isExist(Long documentId, String documentCode);
}
