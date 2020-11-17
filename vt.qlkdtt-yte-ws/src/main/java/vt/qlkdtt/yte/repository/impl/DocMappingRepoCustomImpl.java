package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.DocumentMapping;
import vt.qlkdtt.yte.repository.DocMappingRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Transactional
public class DocMappingRepoCustomImpl implements DocMappingRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public void changeChangeStatusByObjectMapId(String status, Long objectMappingId, String objectType) {
        StringBuilder sql = new StringBuilder("UPDATE DOCUMENT_MAPPING SET STATUS = :status WHERE MAPPING_OBJECT_ID = :objectMappingId AND OBJECT_TYPE = :objectType");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("objectMappingId", objectMappingId);
        query.setParameter("objectType", objectType);

        query.executeUpdate();
    }

    @Override
    public DocumentMapping findByDocumentIdAndObjectId(Long documentId, Long objectId, String objectType) {
        DocumentMapping result = new DocumentMapping();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM DOCUMENT_MAPPING ");
        sql.append("WHERE DOCUMENT_ID = :documentId ");
        sql.append("AND MAPPING_OBJECT_ID = :objectId ");
        sql.append("AND OBJECT_TYPE = :objectType");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("documentId", documentId);
        query.setParameter("objectId", objectId);
        query.setParameter("objectType", objectType);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {

            result.setDocumentMappingId(DataUtil.safeToLong(item[0]));
            result.setDocumentId(DataUtil.safeToLong(item[1]));
            result.setObjectType(DataUtil.safeToString(item[2]));
            result.setMappingObjectId(DataUtil.safeToLong(item[3]));
            result.setStatus(DataUtil.safeToString(item[4]));
            result.setCreateDatetime(DataUtil.safeToDate(item[5]));
            result.setCreateUser(DataUtil.safeToString(item[6]));
            result.setUpdateDatetime(DataUtil.safeToDate(item[7]));
            result.setUpdateUser(DataUtil.safeToString(item[8]));
            break;
        }

        return result;
    }

    @Override
    public boolean isExist(long documentMappingId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM DOCUMENT_MAPPING WHERE DOCUMENT_MAPPING_ID = :documentMappingId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("documentMappingId", documentMappingId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
}
