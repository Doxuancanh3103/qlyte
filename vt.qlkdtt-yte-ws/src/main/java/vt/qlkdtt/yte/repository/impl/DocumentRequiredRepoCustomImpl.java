package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.DocumentRequiredRepoCustom;
import vt.qlkdtt.yte.service.sdo.DocumentRequiredSearchSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentRequiredRepoCustomImpl implements DocumentRequiredRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public List<DocumentRequiredSearchSdo> searchDocumentRequired(String orderType, String actionType, Long productId, String productGroupId) {
        List<DocumentRequiredSearchSdo> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("DR.DOCUMENT_REQUIRED_ID, ");
        sql.append("DR.DOCUMENT_TYPE_REQUIRED, ");
        sql.append("GLV.`NAME` ");
        sql.append("FROM ");
        sql.append("DOCUMENT_REQUIRED DR, ");
        sql.append("GLOBAL_LIST GL, ");
        sql.append("GLOBAL_LIST_VALUE GLV ");
        sql.append("WHERE ");
        sql.append("DR.DOCUMENT_TYPE_REQUIRED = GLV.`VALUE` ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'DOCUMENT_TYPE' ");

        if (!DataUtil.isNullOrEmpty(orderType)) {
            sql.append("AND DR.ORDER_TYPE = :orderType ");
            params.put("orderType", orderType);
        }

        if (!DataUtil.isNullOrEmpty(actionType)) {
            sql.append("AND DR.ACTION_TYPE = :actionType ");
            params.put("actionType", actionType);
        }

        if (!DataUtil.isNullObject(productId)) {
            sql.append("AND DR.PRODUCT_ID = :productId ");
            params.put("productId", productId);
        }

        if (!DataUtil.isNullOrEmpty(productGroupId)) {
            sql.append("AND DR.PRODUCT_GROUP_ID = :productGroupId ");
            params.put("productGroupId", productGroupId);
        }

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            DocumentRequiredSearchSdo sdo = new DocumentRequiredSearchSdo();
            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        return result;
    }
}
