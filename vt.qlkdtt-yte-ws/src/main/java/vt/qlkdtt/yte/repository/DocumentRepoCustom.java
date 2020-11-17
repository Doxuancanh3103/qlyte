package vt.qlkdtt.yte.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.service.sdi.DocumentUpdateSdi;
import vt.qlkdtt.yte.service.sdo.DocumentSearchSdo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Transactional
public interface DocumentRepoCustom {
    Page<DocumentSearchSdo> findAll(Pageable pageable) throws ParseException;

    DocumentSearchSdo findById(long documentId) throws ParseException;

    List<DocumentSearchSdo> searchDocument(String documentCodeName) throws ParseException;

    Map<String, Object> changeStatus(long documentId, String status);

    Map<String, Object> changeStatus(String documentNo, String status);

    Map<String, Object> update(DocumentUpdateSdi documentUpdate);

    void changeStatusByProductId(String status, long productId);

    boolean isExist(Long documentId);

    boolean isExist(Long documentId, String documentCode);
}
