package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.repository.GlobalListRepoCustom;
import vt.qlkdtt.yte.service.sdi.GlobalListUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListSearchSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListUpdateSdo;
import vt.qlkdtt.yte.service.sdo.GlobalListValueSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.*;

public class GlobalListRepoCustomImpl implements GlobalListRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public Page<GlobalListSdo> get(String keyword, Pageable pageable) throws ParseException {
        List<GlobalListSdo> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder("SELECT GLOBAL_LIST_ID, NAME, CODE, DESCRIPTION, STATUS, CREATE_USER, CREATE_DATETIME, UPDATE_USER, UPDATE_DATETIME FROM GLOBAL_LIST WHERE  STATUS <> '3'");
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(GLOBAL_LIST_ID) FROM GLOBAL_LIST WHERE STATUS <> '3'");
        if (!DataUtil.isStringNullOrEmpty(keyword)) {
            sql.append(" AND (LOWER(NAME) LIKE LOWER(:keyword) OR LOWER(CODE) LIKE LOWER(:keyword) OR LOWER(DESCRIPTION) LIKE LOWER(:keyword))");
            sqlCount.append(" AND (LOWER(NAME) LIKE LOWER(:keyword) OR LOWER(CODE) LIKE LOWER(:keyword) OR LOWER(DESCRIPTION) LIKE LOWER(:keyword))");
            params.put("keyword", "%" + keyword + "%");
        }
        /*if (!DataUtil.isStringNullOrEmpty(code)) {
            sql.append(" AND CODE LIKE :code");
            sqlCount.append(" AND CODE LIKE :code");
            params.put("code", "%" + code + "%");
        }
        if (!DataUtil.isStringNullOrEmpty(valueName)) {
            sql.append(" AND EXISTS (SELECT GLOBAL_LIST_VALUE_ID FROM GLOBAL_LIST_VALUE WHERE GLOBAL_LIST.GLOBAL_LIST_ID = GLOBAL_LIST_VALUE.GLOBAL_LIST_VALUE_ID AND NAME LIKE :valueName LIMIT 1)");
            sqlCount.append(" AND EXISTS (SELECT GLOBAL_LIST_VALUE_ID FROM GLOBAL_LIST_VALUE WHERE GLOBAL_LIST.GLOBAL_LIST_ID = GLOBAL_LIST_VALUE.GLOBAL_LIST_VALUE_ID AND NAME LIKE :valueName LIMIT 1)");
            params.put("valueName", "%" + valueName + "%");
        }
        if (!DataUtil.isStringNullOrEmpty(valueCode)) {
            sql.append(" AND EXISTS (SELECT GLOBAL_LIST_VALUE_ID FROM GLOBAL_LIST_VALUE WHERE GLOBAL_LIST.GLOBAL_LIST_ID = GLOBAL_LIST_VALUE.GLOBAL_LIST_VALUE_ID AND CODE LIKE :valueCode LIMIT 1)");
            sqlCount.append(" AND EXISTS (SELECT GLOBAL_LIST_VALUE_ID FROM GLOBAL_LIST_VALUE WHERE GLOBAL_LIST.GLOBAL_LIST_ID = GLOBAL_LIST_VALUE.GLOBAL_LIST_VALUE_ID AND CODE LIKE :valueCode LIMIT 1)");
            params.put("valueCode", "%" + valueCode + "%");
        }*/
        sql.append(" ORDER BY CREATE_DATETIME DESC");
        Query query = em.createNativeQuery(sql.toString());
        Query countQuery = em.createNativeQuery(sqlCount.toString());
        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
            countQuery.setParameter(item.getKey(), item.getValue());
        }

        int page = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Long total = DataUtil.safeToLong(countQuery.getSingleResult());
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> queryResult = query.getResultList();
        ArrayList<Long> ids = new ArrayList<>();
        GlobalListSdo sdo;
        for (Object[] item : queryResult) {
            sdo = new GlobalListSdo();
            sdo.setId(DataUtil.safeToLong(item[0]));
            sdo.setName(DataUtil.safeToString(item[1]));
            sdo.setCode(DataUtil.safeToString(item[2]));
            sdo.setDescription(DataUtil.safeToString(item[3]));
            sdo.setStatus(DataUtil.safeToString(item[4]));
            result.add(sdo);
            ids.add(DataUtil.safeToLong(item[0]));
        }
        if (!ids.isEmpty()) {
            List<GlobalListValueSdo> globalListValues = this.getGlobalValuesByIds(ids);
            for (GlobalListSdo rs : result) {
                ArrayList<GlobalListValueSdo> values = new ArrayList<>();
                for (GlobalListValueSdo val : globalListValues) {
                    if (rs.getId().equals(val.getGlobalListId())) {
                        values.add(val);
                    }
                }
                rs.setValues(values);
            }
        }

        return new PageImpl<>(result, pageable, total);
    }

    protected List<GlobalListValueSdo> getGlobalValuesByIds(ArrayList<Long> ids) throws ParseException {
        List<GlobalListValueSdo> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT");
        sql.append(" g.GLOBAL_LIST_ID,"); // 0
        sql.append(" g.GLOBAL_LIST_VALUE_ID,"); // 1
        sql.append(" g.NAME,"); // 2
        sql.append(" g.CODE,"); // 3
        sql.append(" g.VALUE,");// 4
        sql.append(" g.DESCRIPTION,"); // 5
        sql.append(" g.STA_DATE,"); // 6
        sql.append(" g.END_DATE,");// 7
        sql.append(" g.STATUS,"); // 8
        sql.append(" g.TELCO_ID,"); // 9
        sql.append(" g.TELCO_CODE,");// 10
        sql.append(" g.TELCO_VALUE,"); // 11
        sql.append(" g.CREATE_USER,"); // 12
        sql.append(" g.CREATE_DATETIME,"); // 13
        sql.append(" g.UPDATE_USER,"); // 14
        sql.append(" g.UPDATE_DATETIME,"); // 15
        sql.append(" g.PRODUCT_GROUP_ID,"); // 16
        sql.append(" g.PRODUCT_ID,"); // 17
        sql.append(" g.DISPLAY_ORDER,"); // 18
        sql.append(" p.PRODUCT_NAME"); // 19

        sql.append(" FROM GLOBAL_LIST_VALUE g ");
        sql.append(" LEFT JOIN PRODUCT p ON g.PRODUCT_ID = p.PRODUCT_ID");
        sql.append(" WHERE g.STATUS <> '3'");
        if (!ids.isEmpty()) {
            sql.append(" AND g.GLOBAL_LIST_ID IN " + ids.toString().replace("[", "(").replace("]", ")"));
        }
        sql.append(" ORDER BY g.DISPLAY_ORDER ASC");
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> queryResult = query.getResultList();
        GlobalListValueSdo sdo;
        for (Object[] item : queryResult) {
            sdo = new GlobalListValueSdo();
            sdo.setGlobalListId(DataUtil.safeToLong(item[0]));
            sdo.setId(DataUtil.safeToLong(item[1]));
            sdo.setName(DataUtil.safeToString(item[2]));
            sdo.setCode(DataUtil.safeToString(item[3]));
            sdo.setValue(DataUtil.safeToString(item[4]));
            sdo.setDescription(DataUtil.safeToString(item[5]));
            sdo.setStaDate(DataUtil.safeToString(item[6]));
            sdo.setEndDate(DataUtil.safeToString(item[7]));
            sdo.setStatus(DataUtil.safeToString(item[8]));
            sdo.setTelcoId(DataUtil.safeToLong(item[9]));
            sdo.setTelcoCode(DataUtil.safeToString(item[10]));
            sdo.setTelcoValue(DataUtil.safeToString(item[11]));
            sdo.setCreateUser(DataUtil.safeToString(item[12]));
            sdo.setCreateDatetime(DateUtil.date2StringByPattern(DataUtil.safeToDate(item[13]), Const.DATE_FORMAT));
            sdo.setUpdateUser(DataUtil.safeToString(item[14]));
            sdo.setUpdateDatetime(DataUtil.safeToString(item[15]));
            sdo.setProductGroupId(DataUtil.safeToLong(item[16]));
            sdo.setProductId(DataUtil.safeToLong(item[17]));
            sdo.setDisplayOrder(DataUtil.safeToLong(item[18]));
            sdo.setProductName(DataUtil.safeToString(item[19]));
            result.add(sdo);
        }
        return result;
    }

    //<editor-fold desc="Get option customer type id">
    public long getGlobalCustomerTypeId() {
        StringBuilder sql = new StringBuilder("SELECT GLOBAL_LIST_ID FROM GLOBAL_LIST WHERE CODE = 'customer_type'");

        Query query = em.createNativeQuery(sql.toString());

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Find by code">
    @Override
    public List<GlobalListSearchSdo> findByCode(String code, Long productId, String productGroupId) {
        List<GlobalListSearchSdo> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append(" GLV.GLOBAL_LIST_VALUE_ID, ");
        sql.append(" GLV.NAME, ");
        sql.append(" GLV.VALUE, ");
        sql.append(" GLV.DESCRIPTION, ");
        sql.append(" GL.CODE, ");
        sql.append(" GLV.PRODUCT_ID ");
        sql.append(" FROM ");
        sql.append(" GLOBAL_LIST_VALUE GLV, ");
        sql.append(" GLOBAL_LIST GL ");
        sql.append(" WHERE GLV.STATUS = '1' AND GL.STATUS = '1'");
        sql.append(" AND GL.GLOBAL_LIST_ID = GLV.GLOBAL_LIST_ID ");

        if (!DataUtil.isStringNullOrEmpty(code)) {
            sql.append("AND UPPER(GL.CODE) = UPPER(:globalCode) ");
            params.put("globalCode", code);
        }

        if (!DataUtil.isNullObject(productId)) {
            sql.append("AND GLV.PRODUCT_ID = :productId ");
            params.put("productId", productId);
        }

        if (!DataUtil.isNullOrEmpty(productGroupId)) {
            sql.append("AND GLV.PRODUCT_GROUP_ID = :productGroupId ");
            params.put("productGroupId", productGroupId);
        }

        sql.append(" ORDER BY GLV.DISPLAY_ORDER, GL.CODE , GLV.NAME ");

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();
        GlobalListSearchSdo sdo;
        for (Object[] item : queryResult) {
            sdo = new GlobalListSearchSdo();
            sdo.setId(DataUtil.safeToLong(item[0]));
            sdo.setName(DataUtil.safeToString(item[1]));
            sdo.setValue(DataUtil.safeToString(item[2]));
            sdo.setDescription(DataUtil.safeToString(item[3]));
            sdo.setCode(DataUtil.safeToString(item[4]));
            result.add(sdo);
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @Override
    public GlobalListSearchSdo findById(long id) {
        GlobalListSearchSdo sdo = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("OSV.OPTION_SET_VALUE_ID, ");
        sql.append("OSV.NAME, ");
        sql.append("OSV.DESCRIPTION ");
        sql.append("FROM ");
        sql.append("OPTION_SET_VALUE OSV, ");
        sql.append("OPTION_SET OS ");
        sql.append("WHERE ");
        sql.append("OS.CODE = 'customer_type' ");
        sql.append("AND OSV.OPTION_SET_VALUE_ID = :id");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("id", id);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            sdo = DataUtil.convertObjectsToClass(item, sdo);
            break;
        }

        return sdo;
    }

    @Override
    public GlobalListSdo find(long id) {
        GlobalListSdo sdo = null;
        StringBuilder sql = new StringBuilder("SELECT GLOBAL_LIST_ID, NAME, CODE, DESCRIPTION, STATUS, CREATE_USER, CREATE_DATETIME, UPDATE_USER, UPDATE_DATETIME FROM GLOBAL_LIST WHERE");
        sql.append(" GLOBAL_LIST_ID = :id ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("id", id);

        List<Object[]> items = query.getResultList();
        if (!items.isEmpty()) {
            Object[] item = items.get(0);
            sdo = new GlobalListSdo();
            sdo.setId(DataUtil.safeToLong(item[0]));
            sdo.setName(DataUtil.safeToString(item[1]));
            sdo.setCode(DataUtil.safeToString(item[2]));
            sdo.setDescription(DataUtil.safeToString(item[3]));
            sdo.setStatus(DataUtil.safeToString(item[4]));
        }
        return sdo;
    }

    @Override
    public int delete(long id) {
        this.deleteGlobalListValuesByGlobalListId(id);

//        StringBuilder sql = new StringBuilder("DELETE FROM GLOBAL_LIST");
        StringBuilder sql = new StringBuilder("UPDATE GLOBAL_LIST SET STATUS=3");
        sql.append(" WHERE GLOBAL_LIST_ID = :id");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public int deleteGlobalListValuesByGlobalListId(long globalListId) {
        StringBuilder sql = new StringBuilder("DELETE FROM GLOBAL_LIST_VALUE");
        sql.append(" WHERE GLOBAL_LIST_ID = :globalListId");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("globalListId", globalListId);
        return query.executeUpdate();
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    public Map<String, Object> changeStatus(long id, String status) {
        Map<String, Object> result = new HashMap<>();

        StringBuilder sql = new StringBuilder("UPDATE option_set_value SET STATUS = :status WHERE OPTION_SET_VALUE_ID = :id");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("id", id);

        Object queryResult = query.executeUpdate();

        result.put("quantityRecordEffect", DataUtil.safeToLong(queryResult));

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Update">
    @Override
    public int update(GlobalListUpdateSdi sdi) {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE GLOBAL_LIST ");
        sql.append("SET NAME = :name, ");
        sql.append("DESCRIPTION = :description, ");
        sql.append("UPDATE_USER = :updateUser, ");
        sql.append("UPDATE_DATETIME = :updateDatetime, ");
        sql.append("STATUS = :status ");
        sql.append("WHERE GLOBAL_LIST_ID = :id");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("name", sdi.getName());
        query.setParameter("description", sdi.getDescription());
        query.setParameter("updateUser", sdi.getUpdateUser());
        query.setParameter("updateDatetime", new Date());
        query.setParameter("status", sdi.getStatus());
        query.setParameter("id", sdi.getId());

        return query.executeUpdate();
    }
    //</editor-fold>

    @Override
    public List<Long> getGlobalListValueIdsByProductId(long productId) {
        List<Long> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT GLOBAL_LIST_VALUE_ID FROM PRODUCT_CUST_SEGM WHERE PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        List queryResult = query.getResultList();

        for (Object item : queryResult) {
            result.add(DataUtil.safeToLong(item));
        }

        return result;
    }

    @Override
    public boolean isExist(String globalListValue, String globalListType) {
        Boolean result = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND UPPER(GL.CODE) = :globalListType ");
        sql.append("AND GLV.VALUE = :globalListValue ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("globalListType", globalListType);
        query.setParameter("globalListValue", globalListValue);

        Object queryResult = query.getSingleResult();

        if (DataUtil.safeToLong(queryResult) > 0) {
            result = true;
        }

        return result;
    }
}
