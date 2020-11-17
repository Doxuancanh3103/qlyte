package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.CustomerPropective;
import vt.qlkdtt.yte.repository.CustomerPropectiveRepoCustom;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveSearchSdi;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class CustomerPropectiveRepoCustomImpl implements CustomerPropectiveRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="Change status by product offer">
    @Override
    public void changeStatusByProductOffer(String status, long productOfferId) {
        StringBuilder sql = new StringBuilder("UPDATE CUSTOMER_PROPECTIVE SET STATUS = :status WHERE PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productOfferId", productOfferId);

        query.executeUpdate();
    }
    //</editor-fold>

    //<editor-fold desc="is Exist">
    @Override
    public boolean isExist(long customerPropectiveId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_PROPECTIVE WHERE PROPECTIVE_CUSTOMER_ID = :customerPropectiveId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerPropectiveId", customerPropectiveId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExistMedicalPermissionNo(String medicalPermissionNo) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_PROPECTIVE WHERE MEDICAL_PERMISSION_NO = :medicalPermissionNo");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("medicalPermissionNo", medicalPermissionNo);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExistMedicalPermissionNo(String medicalPermissionNo, Long customerPropectiveId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_PROPECTIVE WHERE MEDICAL_PERMISSION_NO = :medicalPermissionNo AND PROPECTIVE_CUSTOMER_ID <> :customerPropectiveId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("medicalPermissionNo", medicalPermissionNo);
        query.setParameter("customerPropectiveId", customerPropectiveId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExistIdentityNo(String identityNo) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_PROPECTIVE WHERE IDENTITY_NO = :identityNo");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("identityNo", identityNo);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExistIdentityNo(String identityNo, Long customerPropectiveId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_PROPECTIVE WHERE IDENTITY_NO = :identityNo AND PROPECTIVE_CUSTOMER_ID <> :customerPropectiveId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("identityNo", identityNo);
        query.setParameter("customerPropectiveId", customerPropectiveId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="Count all item search">
    private Long countAllItemSearch(CustomerPropectiveSearchSdi sdi){
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("SELECT COUNT(*) ");
        sql.append("FROM CUSTOMER_PROPECTIVE CP, GLOBAL_LIST GL, GLOBAL_LIST_VALUE GLV ");
        sql.append("WHERE CP.CUSTOMER_BUS_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'CUSTOMER_BUS_TYPE' ");

        String customerBusType = sdi.getCustomerBusType();
        String customerName = sdi.getCustomerName();
        String identityNo = sdi.getIdentityNo();
        String areaCodeProvince = sdi.getAreaCodeProvince();
        String areaCodeDistrict = sdi.getAreaCodeDistrict();
        String areaCodePrecinct = sdi.getAreaCodePrecinct();
        String accountNo = sdi.getAccountNo();
        String accountServiceNo = sdi.getAccountServiceNo();
        String status = sdi.getStatus();

        if (!DataUtil.isNullOrEmpty(customerBusType)) {
            sql.append("AND CP.CUSTOMER_BUS_TYPE = :customerBusType ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isNullOrEmpty(customerName)) {
            sql.append("AND UPPER(CP.CUSTOMER_NAME) LIKE :customerName ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(identityNo)) {
            sql.append("AND UPPER(CP.IDENTITY_NO) LIKE :identityNo ");
            params.put("identityNo", "%" + identityNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(areaCodeProvince)) {
            sql.append("AND CP.PROVINCE = :areaCodeProvince ");
            params.put("areaCodeProvince", areaCodeProvince);
        }

        if (!DataUtil.isNullOrEmpty(areaCodeDistrict)) {
            sql.append("AND CP.DISTRICT = :areaCodeDistrict ");
            params.put("areaCodeDistrict", areaCodeDistrict);
        }

        if (!DataUtil.isNullOrEmpty(areaCodePrecinct)) {
            sql.append("AND CP.PRECINCT = :areaCodePrecinct ");
            params.put("areaCodePrecinct", areaCodePrecinct);
        }

        if (!DataUtil.isNullOrEmpty(accountNo)) {
            sql.append("AND UPPER(CP.ACCOUNT_NO) LIKE :accountNo ");
            params.put("accountNo", "%" + accountNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(accountServiceNo)) {
            sql.append("AND UPPER(CP.ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(status)) {
            if (status.equals(Const.STATUS.INACTIVE)) {
                sql.append("AND (CP.STATUS = :status OR CP.STATUS IS NULL) ");
            } else {
                sql.append("AND CP.STATUS = :status ");
            }
            params.put("status", status);
        }

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Search customer propective">
    @Override
    public Page<CustomerPropectiveSearchSdo> searchCustomerPropective(CustomerPropectiveSearchSdi sdi, Pageable pageable) {
        List<CustomerPropectiveSearchSdo> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("SELECT ");
        sql.append("CP.PROPECTIVE_CUSTOMER_ID, ");
        sql.append("GLV.NAME, ");
        sql.append("CP.CUSTOMER_NAME, ");
        sql.append("CP.IDENTITY_NO, ");
        sql.append("CP.BIRTH_DATE, ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = CP.PROVINCE) PROVINCE_NAME, ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = CP.DISTRICT) DISTRICT_NAME, ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = CP.PRECINCT) PRECINCT_NAME, ");
        sql.append("CP.STATUS, ");
        sql.append("CP.MEDICAL_NO, ");
        sql.append("CP.ACCOUNT_NO, ");
        sql.append("CP.ACCOUNT_SERVICE_NO, ");
        sql.append("CP.PROVISIONING_STATUS, ");
        sql.append("(SELECT GLV.NAME ");
        sql.append("FROM GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE CP.PROVISIONING_STATUS = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'PROVISIONING_STATUS' ");
        sql.append(") PROVISIONING_STATUS_NAME ");
        sql.append("FROM CUSTOMER_PROPECTIVE CP, GLOBAL_LIST GL, GLOBAL_LIST_VALUE GLV ");
        sql.append("WHERE CP.CUSTOMER_BUS_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'CUSTOMER_BUS_TYPE' ");

        String customerBusType = sdi.getCustomerBusType();
        String customerName = sdi.getCustomerName();
        String identityNo = sdi.getIdentityNo();
        String areaCodeProvince = sdi.getAreaCodeProvince();
        String areaCodeDistrict = sdi.getAreaCodeDistrict();
        String areaCodePrecinct = sdi.getAreaCodePrecinct();
        String accountNo = sdi.getAccountNo();
        String accountServiceNo = sdi.getAccountServiceNo();
        String status = sdi.getStatus();

        if (!DataUtil.isNullOrEmpty(customerBusType)) {
            sql.append("AND CP.CUSTOMER_BUS_TYPE = :customerBusType ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isNullOrEmpty(customerName)) {
            sql.append("AND UPPER(CP.CUSTOMER_NAME) LIKE :customerName ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(identityNo)) {
            sql.append("AND UPPER(CP.IDENTITY_NO) LIKE :identityNo ");
            params.put("identityNo", "%" + identityNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(areaCodeProvince)) {
            sql.append("AND CP.PROVINCE = :areaCodeProvince ");
            params.put("areaCodeProvince", areaCodeProvince);
        }

        if (!DataUtil.isNullOrEmpty(areaCodeDistrict)) {
            sql.append("AND CP.DISTRICT = :areaCodeDistrict ");
            params.put("areaCodeDistrict", areaCodeDistrict);
        }

        if (!DataUtil.isNullOrEmpty(areaCodePrecinct)) {
            sql.append("AND CP.PRECINCT = :areaCodePrecinct ");
            params.put("areaCodePrecinct", areaCodePrecinct);
        }

        if (!DataUtil.isNullOrEmpty(accountNo)) {
            sql.append("AND UPPER(CP.ACCOUNT_NO) LIKE :accountNo ");
            params.put("accountNo", "%" + accountNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(accountServiceNo)) {
            sql.append("AND UPPER(CP.ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(status)) {
            if (status.equals(Const.STATUS.INACTIVE)) {
                sql.append("AND (CP.STATUS = :status OR CP.STATUS IS NULL) ");
            } else {
                sql.append("AND CP.STATUS = :status ");
            }
            params.put("status", status);
        }

        Query query = em.createNativeQuery(sql.toString());
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();
        for (Object[] item : queryResult) {
            CustomerPropectiveSearchSdo sdo = new CustomerPropectiveSearchSdo();
            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        Long countAllItem = countAllItemSearch(sdi);

        return new PageImpl<>(result, pageable, countAllItem);
    }
    //</editor-fold>

    //<editor-fold desc="Search by id">
    @Override
    public CustomerPropectiveSearchByIdSdo searchById(Long customerPropectiveId) {
        CustomerPropectiveSearchByIdSdo result = new CustomerPropectiveSearchByIdSdo();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("CP.PROPECTIVE_CUSTOMER_ID, ");
        sql.append("CP.CUSTOMER_PROPECTIVE_CODE, ");
        sql.append("CP.CUSTOMER_BUS_TYPE, ");
        sql.append("CP.CUSTOMER_NAME, ");
        sql.append("CP.IDENTITY_TYPE, ");
        sql.append("CP.IDENTITY_NO, ");
        sql.append("CP.BIRTH_DATE, ");
        sql.append("CP.PROVINCE, ");
        sql.append("CP.DISTRICT, ");
        sql.append("CP.PRECINCT, ");
        sql.append("CP.ADDRESS, ");
        sql.append("CP.TEL, ");
        sql.append("CP.EMAIL, ");
        sql.append("CP.STATUS, ");
        sql.append("CP.PRODUCT_ID, ");
        sql.append("P.PRODUCT_NAME, ");
        sql.append("CP.MEDICAL_NO, ");
        sql.append("CP.MEDICAL_PERMISSION_NO, ");
        sql.append("CP.MEDICAL_PERMISSION_DATE, ");
        sql.append("CP.ACCOUNT_NO, ");
        sql.append("CP.ACCOUNT_SERVICE_NO, ");
        sql.append("CP.REPRESENT_NAME, ");
        sql.append("CP.REPRESENT_ID_TYPE, ");
        sql.append("CP.REPRESENT_ID_NO, ");
        sql.append("CP.REPRESENT_TEL, ");
        sql.append("CP.REPRESENT_EMAIL, ");
        sql.append("CP.SALES_MAN_CODE, ");
        sql.append("CP.BROKERS_PARTNER_CODE, ");
        sql.append("CP.MEDICAL_ORG_TYPE, ");
        sql.append("CP.PROVISIONING_STATUS ");
        sql.append("FROM CUSTOMER_PROPECTIVE CP ");
        sql.append("LEFT JOIN PRODUCT P ON CP.PRODUCT_ID = P.PRODUCT_ID AND P.STATUS = 1 ");
        sql.append("WHERE PROPECTIVE_CUSTOMER_ID = :customerPropectiveId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerPropectiveId", customerPropectiveId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    public Map<String, Object> changeStatus(Long customerPropectiveId, String status) {
        Map<String, Object> result = new HashMap<>();

        StringBuilder sql = new StringBuilder("UPDATE CUSTOMER_PROPECTIVE SET STATUS = :status WHERE PROPECTIVE_CUSTOMER_ID = :customerPropectiveId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("customerPropectiveId", customerPropectiveId);

        Object queryResult = query.executeUpdate();

        result.put("quantityRecordEffect", DataUtil.safeToLong(queryResult));

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Search by identity no">
    @Override
    public CustomerPropective searchByIdentityNo(String identityNo) {
        CustomerPropective result = new CustomerPropective();

        String sql = "SELECT * FROM CUSTOMER_PROPECTIVE WHERE IDENTITY_NO = :identityNo ORDER BY CUSTOMER_ID DESC";

        Query query = em.createNativeQuery(sql);
        query.setParameter("identityNo", identityNo);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
            break;
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Search by customer name">
    @Override
    public CustomerPropective searchByCustomerName(String customerName) {
        CustomerPropective result = new CustomerPropective();

        String sql = "SELECT * FROM CUSTOMER_PROPECTIVE WHERE CUSTOMER_NAME = :customerName AND CUSTOMER_BUS_TYPE = '2' ORDER BY CUSTOMER_ID DESC";

        Query query = em.createNativeQuery(sql);
        query.setParameter("customerName", customerName);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
            break;
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Update after connect">
    @Override
    public void updateAfterConnect(String cpCode, Long cpId, Long customerId) {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE CUSTOMER_PROPECTIVE  ");
        sql.append("SET CUSTOMER_ID = :customerId, STATUS = '2' ");
        sql.append("WHERE CUSTOMER_PROPECTIVE_CODE = :cpCode ");
        sql.append("AND PROPECTIVE_CUSTOMER_ID <> :cpId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerId", customerId);
        query.setParameter("cpCode", cpCode);
        query.setParameter("cpId", cpId);

        query.executeUpdate();


        sql = new StringBuilder();

        sql.append("UPDATE CUSTOMER_PROPECTIVE ");
        sql.append("SET CUSTOMER_ID = :customerId, ");
        sql.append("STATUS = '2', PROVISIONING_STATUS = 1 ");
        sql.append("WHERE PROPECTIVE_CUSTOMER_ID = :cpId");

        query = em.createNativeQuery(sql.toString());
        query.setParameter("customerId", customerId);
        query.setParameter("cpId", cpId);

        query.executeUpdate();
    }
    //</editor-fold>

    //<editor-fold desc="Get customer propective code">
    @Override
    public String getCustomerPropectiveCode() {
        String result = "CP00000001";

        String sql = "SELECT CUSTOMER_PROPECTIVE_CODE FROM CUSTOMER_PROPECTIVE ORDER BY CUSTOMER_PROPECTIVE_CODE DESC LIMIT 1";

        Query query = em.createNativeQuery(sql);

        Object queryResult = query.getSingleResult();

        String rs = DataUtil.safeToString(queryResult);
        if (!DataUtil.isNullOrEmpty(rs)) {
            Long code = DataUtil.safeToLong(rs.substring(2)) + 1;
            String codeStr = DataUtil.safeToString(code);
            int codeStrLength = codeStr.length();
            for (int i = 0; i < 8 - codeStrLength; i++) {
                codeStr = "0" + codeStr;
            }
            result = "CP" + codeStr;
        }

        return result;
    }
    //</editor-fold>
}
