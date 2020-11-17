package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Document;
import vt.qlkdtt.yte.service.sdi.DocumentUpdateSdi;
import vt.qlkdtt.yte.repository.DocumentRepo;
import vt.qlkdtt.yte.repository.DocumentRepoCustom;
import vt.qlkdtt.yte.service.sdo.DocumentSearchSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;

@Transactional
@Repository
public class DocumentRepoCustomImpl implements DocumentRepoCustom {
    @Autowired
    EntityManager em;

    @Autowired
    DocumentRepo documentRepo;

    //<editor-fold desc="Count all item">
    private long countAllItem() {
        String sql = "SELECT COUNT(*) FROM DOCUMENT";

        Query query = em.createNativeQuery(sql);

        Object result = query.getSingleResult();

        return DataUtil.safeToLong(result);
    }
    //</editor-fold>

    //<editor-fold desc="Get DocumentSearchSdo by Object[]">
    private DocumentSearchSdo getByObject(Object[] objects) throws ParseException {
        DocumentSearchSdo sdo = new DocumentSearchSdo();

        sdo.setDocumentId(DataUtil.safeToLong(objects[0]));
        sdo.setCode(DataUtil.safeToString(objects[1]));
        sdo.setName(DataUtil.safeToString(objects[2]));
        sdo.setDescription(DataUtil.safeToString(objects[3]));
        sdo.setSignDate(DateUtil.date2StringByPattern(DataUtil.safeToDate(objects[4]), Const.DATE_FORMAT));
        sdo.setEffectDate(DateUtil.date2StringByPattern(DataUtil.safeToDate(objects[5]), Const.DATE_FORMAT));
        sdo.setExpireDate(DateUtil.date2StringByPattern(DataUtil.safeToDate(objects[6]), Const.DATE_FORMAT));
        sdo.setStatus(DataUtil.safeToString(objects[7]));
        sdo.setPath(DataUtil.safeToString(objects[8]));
        sdo.setFileName(DataUtil.safeToString(objects[9]));

        return sdo;
    }
    //</editor-fold>

    //<editor-fold desc="Find all">
    @Override
    public Page<DocumentSearchSdo> findAll(Pageable pageable) throws ParseException {
        List<DocumentSearchSdo> listResult = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("DOCUMENT_ID, ");
        sql.append("CODE, ");
        sql.append("NAME, ");
        sql.append("DESCRIPTION, ");
        sql.append("SIGN_DATE, ");
        sql.append("EFFECT_DATE, ");
        sql.append("EXPIRE_DATE, ");
        sql.append("STATUS, ");
        sql.append("PATH, ");
        sql.append("FILE_NAME ");
        sql.append("FROM ");
        sql.append("DOCUMENT");

        Query query = em.createNativeQuery(sql.toString());
//        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            DocumentSearchSdo sdo = getByObject(item);

            listResult.add(sdo);
        }

        long allItem = countAllItem();

        return new PageImpl<>(listResult, pageable, allItem);
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @Override
    public DocumentSearchSdo findById(long documentId) throws ParseException {
        return findByIdOrNo(documentId, null);
    }
    //</editor-fold>

    //<editor-fold desc="Find by no">
    @Override
    public List<DocumentSearchSdo> searchDocument(String documentCodeName) throws ParseException {
        List<DocumentSearchSdo> result = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("DOCUMENT_ID, ");
        sql.append("CODE, ");
        sql.append("NAME, ");
        sql.append("DESCRIPTION, ");
        sql.append("SIGN_DATE, ");
        sql.append("EFFECT_DATE, ");
        sql.append("EXPIRE_DATE, ");
        sql.append("STATUS, ");
        sql.append("PATH, ");
        sql.append("FILE_NAME ");
        sql.append("FROM ");
        sql.append("DOCUMENT ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isStringNullOrEmpty(documentCodeName)) {
            sql.append("AND (UPPER(CODE) LIKE :documentCodeName OR UPPER(NAME) LIKE :documentCodeName)");
            params.put("documentCodeName", "%" + documentCodeName.toUpperCase() + "%");
        }


        Query query = em.createNativeQuery(sql.toString());
        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result.add(getByObject(item));
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Find by id or no">
    private DocumentSearchSdo findByIdOrNo(Long docId, String documentCodeName) throws ParseException {
        DocumentSearchSdo result = null;
        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("DOCUMENT_ID, ");
        sql.append("CODE, ");
        sql.append("NAME, ");
        sql.append("DESCRIPTION, ");
        sql.append("SIGN_DATE, ");
        sql.append("EFFECT_DATE, ");
        sql.append("EXPIRE_DATE, ");
        sql.append("STATUS, ");
        sql.append("PATH, ");
        sql.append("FILE_NAME ");
        sql.append("FROM ");
        sql.append("DOCUMENT ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrZero(docId)) {
            sql.append("AND DOCUMENT_ID = :documentId");
            params.put("documentId", docId);
        } else if (!DataUtil.isStringNullOrEmpty(documentCodeName)) {
            sql.append("AND (UPPER(CODE) LIKE :documentCodeName OR UPPER(NAME) LIKE :documentCodeName)");
            params.put("documentCodeName", "%" + documentCodeName.toUpperCase() + "%");
        }


        Query query = em.createNativeQuery(sql.toString());
        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = getByObject(item);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    @org.springframework.data.jpa.repository.Query("")
    public Map<String, Object> changeStatus(long documentId, String status) {
        Map<String, Object> result = new HashMap<>();
        StringBuilder sql = new StringBuilder("UPDATE DOCUMENT SET STATUS = :status WHERE DOCUMENT_ID = :documentId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("documentId", documentId);

        Integer queryResult = query.executeUpdate();

        result.put("quantityRecordEffect", queryResult);

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    @org.springframework.data.jpa.repository.Query("")
    public Map<String, Object> changeStatus(String documentNo, String status) {
        Map<String, Object> result = new HashMap<>();
        StringBuilder sql = new StringBuilder("UPDATE DOCUMENT SET STATUS = :status WHERE CODE = :documentNo");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("documentNo", documentNo);

        Integer queryResult = query.executeUpdate();

        result.put("quantityRecordEffect", queryResult);

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Update">
    @Override
    public Map<String, Object> update(DocumentUpdateSdi documentUpdate) {
        Map<String, Object> result = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE DOCUMENT  ");
        sql.append("SET DOC_NO = :docNo,  ");
        sql.append("NAME = :name,  ");
        sql.append("DESCRIPTION = :description, ");
        sql.append("EXPIRE_DATE = :expireDate, ");
        sql.append("EFFECT_DATE = :effectDate, ");
        sql.append("UPDATE_USER = :updateUser, ");
        sql.append("UPDATE_DATETIME = :updateDatetime ");
        sql.append("WHERE DOCUMENT_ID = :documentId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("docNo", documentUpdate.getCode());
        query.setParameter("name", documentUpdate.getName());
        query.setParameter("description", documentUpdate.getDescription());
        query.setParameter("expireDate", documentUpdate.getExpireDate());
        query.setParameter("effectDate", documentUpdate.getEffectDate());
        query.setParameter("updateDatetime", new Date());
        query.setParameter("documentId", documentUpdate.getDocumentId());

        Integer quantityRowEffect = query.executeUpdate();
        result.put("quantityRecordEffect", quantityRowEffect);

        return result;
    }
    //</editor-fold>

    @Override
    public void changeStatusByProductId(String status, long productId) {
        StringBuilder sql = new StringBuilder("UPDATE DOCUMENT SET STATUS = :status WHERE PRODUCT_ID = :productId AND (PARTNER_REVENUE_SHARE_ID = 0 || PARTNER_REVENUE_SHARE_ID IS NULL)");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productId", productId);

        query.executeUpdate();
    }

    @Override
    public boolean isExist(Long documentId) {
        Optional<Document> optionalDoc = documentRepo.findById(documentId);

        return optionalDoc.isPresent();
    }

    @Override
    public boolean isExist(Long documentId, String documentCode) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM DOCUMENT WHERE CODE = :documentCode AND DOCUMENT_ID <> :documentId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("documentCode", documentCode);
        query.setParameter("documentId", documentId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
}
