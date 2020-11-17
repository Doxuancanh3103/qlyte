package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.ProductCustSegm;
import vt.qlkdtt.yte.repository.ProductCustSegmRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class ProductCustSegmRepoCustomImpl implements ProductCustSegmRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="Find by global list value id">
    @Override
    public ProductCustSegm findByValue(String globalListValue) {
        ProductCustSegm result = new ProductCustSegm();

        StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCT_CUST_SEGM WHERE GLOBAL_VALUE = :globalListValue");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("globalListValue", globalListValue);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result.setProductCustSegmId(DataUtil.safeToLong(item[0]));
            result.setGlobalValue(DataUtil.safeToString(item[1]));
            result.setProductId(DataUtil.safeToLong(item[2]));
            result.setProductOfferId(DataUtil.safeToLong(item[3]));
            result.setStaDate(DataUtil.safeToDate(item[4]));
            result.setEndDate(DataUtil.safeToDate(item[5]));
            result.setStatus(DataUtil.safeToString(item[6]));
            result.setCreateUser(DataUtil.safeToString(item[7]));
            result.setCreateDatetime(DataUtil.safeToDate(item[8]));
            result.setUpdateUser(DataUtil.safeToString(item[9]));
            result.setUpdateDatetime(DataUtil.safeToDate(item[10]));
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Find by global list value id and productId">
    @Override
    public ProductCustSegm findByValueAndProduct(String globalListValue, Long productId) {
        ProductCustSegm result = new ProductCustSegm();

        StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCT_CUST_SEGM WHERE GLOBAL_VALUE = :globalListValue and PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("globalListValue", globalListValue);
        query.setParameter("productId", productId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result.setProductCustSegmId(DataUtil.safeToLong(item[0]));
            result.setGlobalValue(DataUtil.safeToString(item[1]));
            result.setProductId(DataUtil.safeToLong(item[2]));
            result.setProductOfferId(DataUtil.safeToLong(item[3]));
            result.setStaDate(DataUtil.safeToDate(item[4]));
            result.setEndDate(DataUtil.safeToDate(item[5]));
            result.setStatus(DataUtil.safeToString(item[6]));
            result.setCreateUser(DataUtil.safeToString(item[7]));
            result.setCreateDatetime(DataUtil.safeToDate(item[8]));
            result.setUpdateUser(DataUtil.safeToString(item[9]));
            result.setUpdateDatetime(DataUtil.safeToDate(item[10]));
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Find by global list value and product offer">
    @Override
    public ProductCustSegm findByValueAndProductOffer(String globalListValue, Long productOfferId) {
        ProductCustSegm result = new ProductCustSegm();

        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM PRODUCT_CUST_SEGM WHERE 1 =1 ");

        if (!DataUtil.isNullOrEmpty(globalListValue)) {
            sql.append("AND GLOBAL_VALUE = :globalListValue ");
            params.put("globalListValue", globalListValue);
        }

        if (!DataUtil.isNullObject(productOfferId)) {
            sql.append("AND PRODUCT_OFFER_ID = :productOfferId ");
            params.put("productOfferId", productOfferId);
        }

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {

            result.setProductCustSegmId(DataUtil.safeToLong(item[0]));
            result.setGlobalValue(DataUtil.safeToString(item[1]));
            result.setProductId(DataUtil.safeToLong(item[2]));
            result.setProductOfferId(DataUtil.safeToLong(item[3]));
            result.setStaDate(DataUtil.safeToDate(item[4]));
            result.setEndDate(DataUtil.safeToDate(item[5]));
            result.setStatus(DataUtil.safeToString(item[6]));
            result.setCreateUser(DataUtil.safeToString(item[7]));
            result.setCreateDatetime(DataUtil.safeToDate(item[8]));
            result.setUpdateUser(DataUtil.safeToString(item[9]));
            result.setUpdateDatetime(DataUtil.safeToDate(item[10]));
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Find by product offer id">
    public List<ProductCustSegm> findByProductOfferId(Long productOfferId) {
        List<ProductCustSegm> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder sqlCommon = new StringBuilder();
        sqlCommon.append("SELECT ");
        sqlCommon.append("PCS.PRODUCT_CUST_SEGM_ID, ");
        sqlCommon.append("PCS.GLOBAL_VALUE, ");
        sqlCommon.append("GLV.NAME, ");
        sqlCommon.append("PCS.PRODUCT_ID, ");
        sqlCommon.append("PCS.PRODUCT_OFFER_ID, ");
        sqlCommon.append("PCS.STA_DATE, ");
        sqlCommon.append("PCS.END_DATE, ");
        sqlCommon.append("PCS.STATUS, ");
        sqlCommon.append("PCS.CREATE_USER, ");
        sqlCommon.append("PCS.CREATE_DATETIME, ");
        sqlCommon.append("PCS.UPDATE_USER, ");
        sqlCommon.append("PCS.UPDATE_DATETIME ");
        sqlCommon.append("FROM ");
        sqlCommon.append("PRODUCT_CUST_SEGM PCS, ");
        sqlCommon.append("GLOBAL_LIST_VALUE GLV, ");
        sqlCommon.append("GLOBAL_LIST GL ");
        sqlCommon.append("WHERE PCS.GLOBAL_VALUE = GLV.VALUE ");
        sqlCommon.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sqlCommon.append("AND GL.CODE = 'PRODUCT_CUST_SEGM' ");


        StringBuilder sql = new StringBuilder(sqlCommon);
        sql.append("AND PCS.PRODUCT_OFFER_ID = :productOfferId ");
        sql.append("ORDER BY PCS.PRODUCT_CUST_SEGM_ID ");

        params.put("productOfferId", productOfferId);

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();


        ProductCustSegm pcs;
        if (queryResult != null && queryResult.size() > 0) {
            for (Object[] item : queryResult) {
                pcs = new ProductCustSegm();
                pcs = DataUtil.convertObjectsToClass(item, pcs);

                result.add(pcs);
            }
        } else {
            params = new HashMap<>();

            sql = new StringBuilder(sqlCommon);
            sql.append("AND PCS.PRODUCT_ID = (SELECT PRODUCT_ID FROM PRODUCT_OFFER WHERE PRODUCT_OFFER_ID = :productOfferId) ");
            sql.append("ORDER BY PCS.PRODUCT_CUST_SEGM_ID ");

            params.put("productOfferId", productOfferId);

            query = em.createNativeQuery(sql.toString());

            for (Map.Entry<String, Object> item : params.entrySet()) {
                query.setParameter(item.getKey(), item.getValue());
            }

            queryResult = query.getResultList();

            for (Object[] item : queryResult) {
                pcs = new ProductCustSegm();
                pcs = DataUtil.convertObjectsToClass(item, pcs);

                result.add(pcs);
            }
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Change status by productId">
    @Override
    public void changeStatusByProductId(String status, long productId) {
        StringBuilder sql = new StringBuilder("UPDATE PRODUCT_CUST_SEGM SET STATUS = :status WHERE PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productId", productId);

        query.executeUpdate();
    }
    //</editor-fold>

    //<editor-fold desc="Change status by product offer id">
    @Override
    public void changeStatusByProductOfferId(String status, long productOfferId) {
        StringBuilder sql = new StringBuilder("UPDATE PRODUCT_CUST_SEGM SET STATUS = :status WHERE PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productOfferId", productOfferId);

        query.executeUpdate();
    }
    //</editor-fold>
}
