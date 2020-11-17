package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.GlobalParam;
import vt.qlkdtt.yte.repository.GlobalParamRepoCustom;
import vt.qlkdtt.yte.service.sdi.GlobalParamInsertSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListSearchSdo;
import vt.qlkdtt.yte.service.sdo.GlobalParamInsertSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalParamRepoCustomImpl implements GlobalParamRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="Find by code">
    @Override
    public List<GlobalParam> findByCode(String code) {
        List<GlobalParam> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append(" 	GLOBAL_PARAM_ID,	TYPE,	CODE,	NAME,VALUE	,	DESCRIPTION,	PRODUCT_GROUP_ID, ");
        sql.append(" 	PRODUCT_ID,	STATUS,	CREATE_DATE,	CREATE_USER,	UPDATE_DATE,	UPDATE_USER ");
        sql.append(" FROM ");
        sql.append(" 	GLOBAL_PARAM ");
        sql.append(" WHERE ");
        sql.append(" 	STATUS = '1' ");

        if (!DataUtil.isStringNullOrEmpty(code)) {
            sql.append("AND UPPER(CODE) = UPPER(:code) ");
            params.put("code", code);
        }

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();
        GlobalParam globalParam;
        for (Object[] item : queryResult) {
            globalParam = new GlobalParam();
            globalParam.setGlobalParamId(DataUtil.safeToLong(item[0]));
            globalParam.setType(DataUtil.safeToString(item[1]));
            globalParam.setCode(DataUtil.safeToString(item[2]));
            globalParam.setName(DataUtil.safeToString(item[3]));
            globalParam.setValue(DataUtil.safeToString(item[4]));
            globalParam.setDescription(DataUtil.safeToString(item[5]));
            globalParam.setProductGroupId(DataUtil.safeToString(item[6]));
            globalParam.setProductId(DataUtil.safeToLong(item[7]));
            globalParam.setStatus(DataUtil.safeToString(item[8]));
            globalParam.setCreateDate(DataUtil.safeToDate(item[9]));
            globalParam.setCreateUser(DataUtil.safeToString(item[10]));
            globalParam.setUpdateDate(DataUtil.safeToDate(item[11]));
            globalParam.setUpdateUser(DataUtil.safeToString(item[12]));
            result.add(globalParam);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Find by code and product">
    @Override
    public GlobalParam findByCodeAndProduct(String code, Long productId) {
        GlobalParam result = new GlobalParam();
        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("GLOBAL_PARAM_ID, TYPE, CODE, NAME, VALUE, DESCRIPTION, PRODUCT_GROUP_ID, ");
        sql.append("PRODUCT_ID, STATUS,	CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER ");
        sql.append("FROM ");
        sql.append("GLOBAL_PARAM ");
        sql.append("WHERE ");
        sql.append("STATUS = '1' ");
        sql.append("AND CODE = :code ");
        sql.append("AND PRODUCT_ID = :productId ");

        params.put("code", code);
        params.put("productId", productId);

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();
        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
            break;
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get max index of isdn">
    @Override
    public Long getMaxIndexOfIsdn(String prefix) {
        Long result = -1L;

        String tempPrefix = prefix;
        if (DataUtil.isNullObject(tempPrefix)) return -1L;
        tempPrefix = tempPrefix.trim();
        String regularEx = tempPrefix + "[0-9]{0,}$|^" + tempPrefix + "$";
        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" select max(a.indx) from ( ");
        sql.append(" select account_no , ");
        sql.append(" IFNULL(SUBSTR(account_no, LENGTH(:prefix) + 1 , LENGTH(account_no)),0) as indx ");
        sql.append(" from SUBSCRIBER ");
        sql.append(" where account_no like :prefixLike ");
        sql.append(" and account_no RLIKE :regex ) a");

        params.put("prefix", tempPrefix);
        params.put("prefixLike", tempPrefix + "%");
        params.put("regex", regularEx);

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        Object queryResult = query.getSingleResult();
        if (queryResult != null) {
            result = DataUtil.safeToLong(queryResult);
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    public boolean changeStatus(Long globalParamId, String status) {
        String sql = "UPDATE GLOBAL_PARAM SET STATUS = :status WHERE GLOBAL_PARAM_ID = :globalParamId";

        Query query = em.createNativeQuery(sql);
        query.setParameter("status", status);
        query.setParameter("globalParamId", globalParamId);

        Object queryResult = query.executeUpdate();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>
}
