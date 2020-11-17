package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.GlobalListValue;
import vt.qlkdtt.yte.repository.GlobalListValueRepoCustom;
import vt.qlkdtt.yte.service.sdi.GlobalListValueInsertSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListValueSdi;
import vt.qlkdtt.yte.service.sdi.GlobalListValueUpdateSdi;
import vt.qlkdtt.yte.service.sdo.GlobalListValueSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.*;

public class GlobalListValueRepoCustomImpl implements GlobalListValueRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public GlobalListValueSdo insert(GlobalListValueInsertSdi data) throws ParseException {
        GlobalListValue glv = new GlobalListValue();
        glv.setGlobalListId(data.getGlobalListId());
        glv.setValue(data.getValue());
        glv.setStatus(data.getStatus());
        glv.setCreateUser(data.getCreateUser());
        glv.setCreateDatetime(DateUtil.sysDate());
        if (!DataUtil.isStringNullOrEmpty(data.getName())) {
            glv.setName(data.getName());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getDescription())) {
            glv.setDescription(data.getDescription());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getStaDate())) {
            glv.setStaDate(DateUtil.string2Date(data.getStaDate(), Const.DATE_FORMAT));
        }
        if (!DataUtil.isStringNullOrEmpty(data.getEndDate())) {
            glv.setEndDate(DateUtil.string2Date(data.getEndDate(), Const.DATE_FORMAT));
        }
        if (!DataUtil.isStringNullOrEmpty(data.getTelcoId())) {
            glv.setTelcoId(data.getTelcoId());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getTelcoCode())) {
            glv.setTelcoCode(data.getTelcoCode());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getTelcoValue())) {
            glv.setTelcoValue(data.getTelcoValue());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getProductGroupId())) {
            glv.setProductGroupId(data.getProductGroupId());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getProductId())) {
            glv.setProductId(data.getProductId());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getDisplayOrder())) {
            glv.setDisplayOrder(data.getDisplayOrder());
        }
        em.persist(glv);
        em.flush();

        return glv.toGlobalListValueSdo();
    }

    public GlobalListValueSdo insert1(GlobalListValueSdi data) {
        StringBuilder sql = new StringBuilder("INSERT INTO GLOBAL_LIST_VALUE (GLOBAL_LIST_ID, VALUE,STATUS,CREATE_USER, CREATE_DATETIME");
        ArrayList<String> fields =  new ArrayList<>();
        ArrayList<String> values =  new ArrayList<>();
        Map<String, Object> params =  new HashMap<>();

        if (!DataUtil.isStringNullOrEmpty(data.getName())) {
            fields.add("NAME");
            values.add(":name");
            params.put("name", data.getName());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getDescription())) {
            fields.add("DESCRIPTION");
            values.add(":description");
            params.put("description", data.getDescription());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getStaDate())) {
            fields.add("STA_DATE");
            values.add(":staDate");
            params.put("staDate", data.getStaDate());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getEndDate())) {
            fields.add("END_DATE");
            values.add(":endDate");
            params.put("endDate", data.getEndDate());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getTelcoId())) {
            fields.add("TELCO_ID");
            values.add(":telcoId");
            params.put("telcoId", data.getTelcoId());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getTelcoCode())) {
            fields.add("TELCO_CODE");
            values.add(":telcoCode");
            params.put("telcoCode", data.getTelcoCode());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getTelcoValue())) {
            fields.add("TELCO_VALUE");
            values.add(":telcoValue");
            params.put("telcoValue", data.getTelcoValue());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getProductGroupId())) {
            fields.add("PRODUCT_GROUP_ID");
            values.add(":productGroupId");
            params.put("productGroupId", data.getProductGroupId());
        }
        if (!DataUtil.isStringNullOrEmpty(data.getProductId())) {
            fields.add("TELCO_VALUE");
            values.add(":productId");
            params.put("productId", data.getProductId());
        }
        if (!fields.isEmpty()) {
            sql.append(", ");
            sql.append(fields.toString().replace("[", "").replace("]", ""));
        }
        sql.append(")");

        sql.append(" VALUES (:globalListId, :value, :status, :createUser, NOW()");
        if(!values.isEmpty()) {
            sql.append(", ");
            sql.append(values.toString().replace("[", "").replace("]", ""));
        }
        sql.append(")");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("globalListId", data.getGlobalListId());
        query.setParameter("value", data.getValue());
        query.setParameter("status", data.getStatus());
        query.setParameter("createUser", data.getCreateUser());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        int insertedRows = query.executeUpdate();
        GlobalListValueSdo sdo = null;
        if (insertedRows > 0) {
            sdo = new GlobalListValueSdo();
        }
        return sdo;
    }

    @Override
    public int update(GlobalListValueUpdateSdi sdi) {

        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE GLOBAL_LIST_VALUE ");
        sql.append("SET NAME = :name, ");
        sql.append("VALUE = :value, ");
        sql.append("DESCRIPTION = :description, ");
        if (!DataUtil.isStringNullOrEmpty(sdi.getStaDate())) {
            sql.append("STA_DATE = :staDate, ");
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getEndDate())) {
            sql.append("END_DATE = :endDate, ");
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getTelcoId())) {
            sql.append("TELCO_ID = :telcoId, ");
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getTelcoCode())) {
            sql.append("TELCO_CODE = :telcoCode, ");
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getTelcoValue())) {
            sql.append("TELCO_VALUE = :telcoValue, ");
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getProductGroupId())) {
            sql.append("PRODUCT_GROUP_ID = :productGroupId, ");
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getProductId())) {
            sql.append("PRODUCT_ID = :productId, ");
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getDisplayOrder())) {
            sql.append("DISPLAY_ORDER = :displayOrder, ");
        }

        sql.append("UPDATE_USER = :updateUser, ");
        sql.append("UPDATE_DATETIME = :updateDatetime, ");
        sql.append("STATUS = :status ");
        sql.append("WHERE GLOBAL_LIST_VALUE_ID = :id");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("name", sdi.getName());
        query.setParameter("value", sdi.getValue());
        query.setParameter("description", sdi.getDescription());
        query.setParameter("updateUser", sdi.getUpdateUser());
        query.setParameter("updateDatetime", DateUtil.sysDate());
        query.setParameter("status", sdi.getStatus());
        query.setParameter("id", sdi.getId());
        if (!DataUtil.isStringNullOrEmpty(sdi.getStaDate())) {
            query.setParameter("staDate", sdi.getStaDate());
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getEndDate())) {
            query.setParameter("endDate", sdi.getEndDate());
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getTelcoId())) {
            query.setParameter("telcoId", sdi.getTelcoId());
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getTelcoCode())) {
            query.setParameter("telcoCode", sdi.getTelcoCode());
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getTelcoValue())) {
            query.setParameter("telcoValue", sdi.getTelcoValue());
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getProductGroupId())) {
            query.setParameter("productGroupId", sdi.getProductGroupId());
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getProductId())) {
            query.setParameter("productId", sdi.getProductId());
        }
        if (!DataUtil.isStringNullOrEmpty(sdi.getDisplayOrder())) {
            query.setParameter("displayOrder", sdi.getDisplayOrder());
        }
        return query.executeUpdate();
    }

    @Override
    public GlobalListValueSdo find(long id) {
        StringBuilder sql = new StringBuilder("SELECT GLOBAL_LIST_VALUE_ID, GLOBAL_LIST_ID, VALUE,STATUS,CREATE_USER, CREATE_DATETIME, NAME, CODE, DESCRIPTION, STA_DATE, END_DATE, TELCO_ID, TELCO_CODE, TELCO_VALUE, UPDATE_USER, UPDATE_DATETIME, PRODUCT_GROUP_ID, PRODUCT_ID FROM GLOBAL_LIST_VALUE WHERE id = :id ");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("id", id);
        List<Object[]> rs = query.getResultList();
        Object[] item = rs.get(0);
        GlobalListValueSdo sdo = new GlobalListValueSdo();
        sdo.setId(DataUtil.safeToLong(item[0]));
        sdo.setGlobalListId(DataUtil.safeToLong(item[1]));
        sdo.setValue(DataUtil.safeToString(item[2]));
        sdo.setStatus(DataUtil.safeToString(item[3]));
        sdo.setCreateUser(DataUtil.safeToString(item[4]));
        sdo.setCreateDatetime(DataUtil.safeToString(item[5]));
        sdo.setName(DataUtil.safeToString(item[6]));
        sdo.setCode(DataUtil.safeToString(item[7]));
        sdo.setDescription(DataUtil.safeToString(item[8]));
        sdo.setStaDate(DataUtil.safeToString(item[9]));
        sdo.setEndDate(DataUtil.safeToString(item[10]));
        sdo.setTelcoId(DataUtil.safeToLong(item[11]));
        sdo.setTelcoCode(DataUtil.safeToString(item[12]));
        sdo.setTelcoValue(DataUtil.safeToString(item[13]));
        sdo.setUpdateUser(DataUtil.safeToString(item[14]));
        sdo.setUpdateDatetime(DataUtil.safeToString(item[15]));
        sdo.setProductGroupId(DataUtil.safeToLong(item[16]));
        sdo.setProductId(DataUtil.safeToLong(item[17]));
        return sdo;
    }

    public int delete(long id) {
//        StringBuilder sql = new StringBuilder("DELETE FROM GLOBAL_LIST_VALUE");
        StringBuilder sql = new StringBuilder("UPDATE GLOBAL_LIST_VALUE SET STATUS = '3'");
        sql.append(" WHERE GLOBAL_LIST_VALUE_ID = :id");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
