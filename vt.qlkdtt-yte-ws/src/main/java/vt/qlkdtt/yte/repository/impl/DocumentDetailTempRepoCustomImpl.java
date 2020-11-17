package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.DocumentDetailTemp;
import vt.qlkdtt.yte.repository.DocumentDetailTempRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DocumentDetailTempRepoCustomImpl implements DocumentDetailTempRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public List<DocumentDetailTemp> getByDocumentId(Long documentId) {
        List<DocumentDetailTemp> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("DOCUMENT_DETAIL_TEMP_ID, ");
        sql.append("DOCUMENT_ID, ");
        sql.append("PATH, ");
        sql.append("FILE_NAME ");
        sql.append("FROM DOCUMENT_DETAIL_TEMP WHERE DOCUMENT_ID = :documentId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("documentId", documentId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            DocumentDetailTemp ddt = new DocumentDetailTemp();
            ddt = DataUtil.convertObjectsToClass(item, ddt);

            result.add(ddt);
        }

        return result;
    }
}
