package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.CustomerOrderRepo;
import vt.qlkdtt.yte.repository.CustomerRepoCustom;
import vt.qlkdtt.yte.service.AreaService;
import vt.qlkdtt.yte.service.sdo.CustomerSearchSdo;
import vt.qlkdtt.yte.service.sdi.CustomerSearchAfterSaleSdi;
import vt.qlkdtt.yte.service.sdo.*;
import vt.qlkdtt.yte.service.sdo.CustomerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerSearchAfterSaleSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.*;

@Transactional
public class CustomerRepoCustomImpl implements CustomerRepoCustom {
    @Autowired
    EntityManager em;

    @Autowired
    AreaService areaService;

    @Autowired
    CustomerOrderRepo customerOrderRepo;

    //<editor-fold desc="Count all item search customer connect">
    private Long countAllItemSearchCustomerConnect(String customerBusType, String customerName, String accountServiceNo,
                                                   String customerIdentityType, String customerIdentityNo) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT(DISTINCT C.CUSTOMER_ID) ");
        sql.append("FROM CUSTOMER C ");
        sql.append("LEFT JOIN CUSTOMER_IDENTITY CI ON C.CUSTOMER_ID = CI.CUSTOMER_ID ");
//        sql.append("LEFT JOIN CUSTOMER_ACCOUNT CA ON C.CUSTOMER_ID = CA.CUSTOMER_ID ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isStringNullOrEmpty(customerBusType)) {
            sql.append("AND C.CUSTOMER_BUS_TYPE = :customerBusType  ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerName)) {
            sql.append("AND UPPER( C.NAME ) LIKE :customerName  ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(accountServiceNo)) {
            sql.append("AND C.CUSTOMER_ID IN (SELECT S.CUSTOMER_ID FROM SUBSCRIBER S WHERE UPPER(S.APP_ACCOUNT_NO) LIKE :accountServiceNo) ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityType)) {
            sql.append("AND CI.ID_TYPE = :customerIdentityType ");
            params.put("customerIdentityType", customerIdentityType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityNo)) {
            sql.append("AND UPPER(CI.ID_NO) LIKE :customerIdentityNo ");
            params.put("customerIdentityNo", "%" + customerIdentityNo.toUpperCase() + "%");
        }

        sql.append("UNION ALL ");

        sql.append("SELECT COUNT(*) ");
        sql.append("FROM CUSTOMER_PROPECTIVE CP ");
        sql.append("WHERE PROVISIONING_STATUS = 0 AND STATUS <> 0 ");

        if (!DataUtil.isStringNullOrEmpty(customerBusType)) {
            sql.append("AND CUSTOMER_BUS_TYPE = :customerBusType  ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerName)) {
            sql.append("AND UPPER(CUSTOMER_NAME) LIKE :customerName  ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(accountServiceNo)) {
            sql.append("AND UPPER(ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityType)) {
            sql.append("AND IDENTITY_TYPE = :customerIdentityType ");
            params.put("customerIdentityType", customerIdentityType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityNo)) {
            sql.append("AND UPPER(IDENTITY_NO) LIKE :customerIdentityNo ");
            params.put("customerIdentityNo", "%" + customerIdentityNo.toUpperCase() + "%");
        }


        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object> queryResult = query.getResultList();

        Long result = 0L;

        for (Object item : queryResult) {
            result += DataUtil.safeToLong(item);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Search customer connection">
    @Override
    public Page<CustomerSearchSdo> searchCustomerConnection(String customerBusType, String customerName, String accountServiceNo,
                                                            String customerIdentityType, String customerIdentityNo, Pageable pageRequest) {
        List<CustomerSearchSdo> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT ");
        sql.append("C.CUSTOMER_ID, ");
        sql.append("NULL AS PROPECTIVE_CUSTOMER_ID, ");
        sql.append("NULL AS CUSTOMER_PROPECTIVE_CODE, ");
        sql.append("C.CUSTOMER_BUS_TYPE, ");
        sql.append("C.CUSTOMER_TYPE, ");
        sql.append("C.NAME, ");
        sql.append("CI.ID_NO, ");
        sql.append("CI.ID_TYPE, ");
        sql.append("C.BIRTH_DATE, ");
        sql.append("C.PROVINCE, ");
        sql.append("C.DISTRICT, ");
        sql.append("C.PRECINCT, ");
        sql.append("C.ADDRESS, ");
        sql.append("C.TEL, ");
        sql.append("C.EMAIL, ");
        sql.append("C.REPRESENT_NAME, ");
        sql.append("NULL AS REPRESENT_ID_TYPE, ");
        sql.append("C.REPRESENT_ID_NO, ");
        sql.append("C.REPRESENT_TITLE, ");
        sql.append("C.REPRESENT_TEL, ");
        sql.append("C.REPRESENT_EMAIL, ");
        sql.append("(SELECT COUNT(*) FROM SUBSCRIBER WHERE CUSTOMER_ID = C.CUSTOMER_ID) COUNT_SUBS, ");
        sql.append("NULL AS SALES_MAN_CODE, ");
        sql.append("NULL AS BROKERS_PARTNER_CODE, ");
        sql.append("NULL AS MEDICAL_ORG_TYPE, ");
        sql.append("NULL AS ACCOUNT_NO, ");
        sql.append("NULL AS ACCOUNT_SERVICE_NO, ");
        sql.append("NULL AS MEDICAL_NO, ");
        sql.append("NULL AS PRODUCT_ID, ");
        sql.append("NULL AS PRODUCT_CODE, ");
        sql.append("NULL AS MEDICAL_PERMISSION_NO, ");
        sql.append("NULL AS MEDICAL_PERMISSION_DATE ");
        sql.append("FROM CUSTOMER C ");
        sql.append("LEFT JOIN CUSTOMER_IDENTITY CI ON C.CUSTOMER_ID = CI.CUSTOMER_ID ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isStringNullOrEmpty(customerBusType)) {
            sql.append("AND C.CUSTOMER_BUS_TYPE = :customerBusType  ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerName)) {
            sql.append("AND UPPER( C.NAME ) LIKE :customerName  ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(accountServiceNo)) {
            sql.append("AND C.CUSTOMER_ID IN (SELECT S.CUSTOMER_ID FROM SUBSCRIBER S WHERE UPPER(S.APP_ACCOUNT_NO) LIKE :accountServiceNo) ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityType)) {
            sql.append("AND CI.ID_TYPE = :customerIdentityType ");
            params.put("customerIdentityType", customerIdentityType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityNo)) {
            sql.append("AND UPPER(CI.ID_NO) LIKE :customerIdentityNo ");
            params.put("customerIdentityNo", "%" + customerIdentityNo.toUpperCase() + "%");
        }

        sql.append("UNION ALL ");

        sql.append("SELECT ");
        sql.append("CUSTOMER_ID, ");
        sql.append("PROPECTIVE_CUSTOMER_ID, ");
        sql.append("CUSTOMER_PROPECTIVE_CODE, ");
        sql.append("CUSTOMER_BUS_TYPE, ");
        sql.append("CUSTOMER_TYPE, ");
        sql.append("CUSTOMER_NAME, ");
        sql.append("IDENTITY_NO, ");
        sql.append("IDENTITY_TYPE, ");
        sql.append("BIRTH_DATE, ");
        sql.append("PROVINCE, ");
        sql.append("DISTRICT, ");
        sql.append("PRECINCT, ");
        sql.append("ADDRESS, ");
        sql.append("TEL, ");
        sql.append("EMAIL, ");
        sql.append("REPRESENT_NAME, ");
        sql.append("REPRESENT_ID_TYPE, ");
        sql.append("REPRESENT_ID_NO, ");
        sql.append("REPRESENT_TITLE, ");
        sql.append("REPRESENT_TEL, ");
        sql.append("REPRESENT_EMAIL, ");
        sql.append("0 AS COUNT_SUBS, ");
        sql.append("SALES_MAN_CODE, ");
        sql.append("BROKERS_PARTNER_CODE, ");
        sql.append("MEDICAL_ORG_TYPE, ");
        sql.append("ACCOUNT_NO, ");
        sql.append("ACCOUNT_SERVICE_NO, ");
        sql.append("MEDICAL_NO, ");
        sql.append("PRODUCT_ID, ");
        sql.append("(SELECT PRODUCT_CODE FROM PRODUCT PR WHERE PR.PRODUCT_ID = CP.PRODUCT_ID) AS PRODUCT_CODE, ");
        sql.append("MEDICAL_PERMISSION_NO, ");
        sql.append("MEDICAL_PERMISSION_DATE ");
        sql.append("FROM CUSTOMER_PROPECTIVE CP ");
        sql.append("WHERE PROVISIONING_STATUS = 0 AND STATUS <> 0 ");

        if (!DataUtil.isStringNullOrEmpty(customerBusType)) {
            sql.append("AND CUSTOMER_BUS_TYPE = :customerBusType  ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerName)) {
            sql.append("AND UPPER(CUSTOMER_NAME) LIKE :customerName  ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(accountServiceNo)) {
            sql.append("AND UPPER(ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityType)) {
            sql.append("AND IDENTITY_TYPE = :customerIdentityType ");
            params.put("customerIdentityType", customerIdentityType);
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityNo)) {
            sql.append("AND UPPER(IDENTITY_NO) LIKE :customerIdentityNo ");
            params.put("customerIdentityNo", "%" + customerIdentityNo.toUpperCase() + "%");
        }

        Query query = em.createNativeQuery(sql.toString());
        query.setMaxResults(pageRequest.getPageSize());
        query.setFirstResult(pageRequest.getPageSize() * pageRequest.getPageNumber());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            CustomerSearchSdo sdo = new CustomerSearchSdo();
            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        Long countAllItem = countAllItemSearchCustomerConnect(customerBusType, customerName, accountServiceNo, customerIdentityType, customerIdentityNo);

        return new PageImpl<>(result, pageRequest, countAllItem);
    }
    //</editor-fold>

    //<editor-fold desc="Count all item Search customer after sale">
    private Long countAllItemSearchProductAfterSale(CustomerSearchAfterSaleSdi dataSearch) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT(*) ");
        sql.append("FROM CUSTOMER C ");
        sql.append("JOIN SUBSCRIBER S ON C.CUSTOMER_ID = S.CUSTOMER_ID ");
        sql.append("JOIN PRODUCT P ON P.PRODUCT_ID = S.PRODUCT_ID AND P.STATUS = 1 ");
        sql.append("LEFT JOIN CUSTOMER_IDENTITY CI ON C.CUSTOMER_ID = CI.CUSTOMER_ID ");
        sql.append("WHERE 1 = 1 ");

        Long productId = dataSearch.getProductId();
        String customerName = dataSearch.getCustomerName();
        String accountNo = dataSearch.getAccountNo();
        String accountServiceNo = dataSearch.getAccountServiceNo();
        String customerIdentityNo = dataSearch.getCustomerIdentityNo();

        String productGroup = dataSearch.getProductGroup();
        String productType = dataSearch.getProductType();
        String province = dataSearch.getProvince();
        String district = dataSearch.getDistrict();
        String customerBusType = dataSearch.getCustomerBusType();
        String medicalNo = dataSearch.getMedicalNo();
        String tel = dataSearch.getTel();
        String fromDate = dataSearch.getFromDate();
        String toDate = dataSearch.getToDate();

        if (!DataUtil.isNullObject(productId)) {
            sql.append("AND P.PRODUCT_ID = :productId ");
            params.put("productId", productId);
        }

        if (!DataUtil.isStringNullOrEmpty(customerName)) {
            sql.append("AND UPPER(C.NAME) LIKE :customerName ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(accountNo)) {
            sql.append("AND UPPER(S.ACCOUNT_NO) LIKE :accountNo ");
            params.put("accountNo", "%" + accountNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(accountServiceNo)) {
            sql.append("AND UPPER(S.APP_ACCOUNT_NO) LIKE :accountServiceNo ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityNo)) {
            sql.append("AND UPPER(CI.ID_NO) LIKE :customerIdentityNo ");
            params.put("customerIdentityNo", "%" + customerIdentityNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(productGroup)) {
            sql.append("AND P.PRODUCT_GROUP_ID = :productGroup ");
            params.put("productGroup", productGroup);
        }

        if (!DataUtil.isNullOrEmpty(productType)) {
            sql.append("AND P.PRODUCT_TYPE = :productType ");
            params.put("productType", productType);
        }

        if (!DataUtil.isStringNullOrEmpty(province)) {
            sql.append("AND C.PROVINCE = :province ");
            params.put("province", province);
        }

        if (!DataUtil.isStringNullOrEmpty(district)) {
            sql.append("AND C.DISTRICT = :district ");
            params.put("district", district);
        }

        if (!DataUtil.isStringNullOrEmpty(customerBusType)) {
            sql.append("AND C.CUSTOMER_BUS_TYPE = :customerBusType ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isStringNullOrEmpty(medicalNo)) {
            sql.append("AND S.SUBSCRIBER_ID IN (SELECT SUBSCRIBER_ID FROM SUBSCRIBER_EXT_MEDICAL WHERE UPPER(MEDICAL_NO) LIKE :medicalNo ) ");
            params.put("medicalNo", "%" + medicalNo + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(tel)) {
            sql.append("AND C.TEL LIKE :tel ");
            params.put("tel", "%" + tel + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(fromDate) && !DataUtil.isStringNullOrEmpty(toDate)) {
            sql.append("AND S.EFFECTIVE_DATE BETWEEN :fromDate AND :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", fromDate);
        } else {
            if (!DataUtil.isStringNullOrEmpty(fromDate)) {
                sql.append("AND S.EFFECTIVE_DATE >= STR_TO_DATE( :fromDate ,'%Y-%m-%d') ");
                params.put("fromDate", fromDate);
            }

            if (!DataUtil.isStringNullOrEmpty(toDate)) {
                sql.append("AND S.EFFECTIVE_DATE <= STR_TO_DATE( :toDate ,'%Y-%m-%d') ");
                params.put("toDate", toDate);
            }
        }

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Search customer after sale">
    @Override
    public Page<CustomerSearchAfterSaleSdo> searchCustomerAfterSale(CustomerSearchAfterSaleSdi dataSearch, Pageable pageRequest) {
        List<CustomerSearchAfterSaleSdo> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("C.CUSTOMER_ID, ");
        sql.append("C.PROVINCE, ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = C.PROVINCE) AS PROVINCE_NAME, ");
        sql.append("C.DISTRICT, ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = C.DISTRICT) AS DISTRICT_NAME, ");
        sql.append("C.CUSTOMER_BUS_TYPE, ");
        sql.append("C.NAME, ");
        sql.append("CI.ID_NO, ");
        sql.append("C.TEL, ");
        sql.append("S.EFFECTIVE_DATE, ");
        sql.append("S.EXPIRE_DATE, ");
        sql.append("S.APP_ACCOUNT_NO ");
        sql.append("FROM CUSTOMER C ");
        sql.append("JOIN SUBSCRIBER S ON C.CUSTOMER_ID = S.CUSTOMER_ID ");
        sql.append("JOIN PRODUCT P ON P.PRODUCT_ID = S.PRODUCT_ID AND P.STATUS = 1 ");
        sql.append("LEFT JOIN CUSTOMER_IDENTITY CI ON C.CUSTOMER_ID = CI.CUSTOMER_ID ");
        sql.append("WHERE 1 = 1 ");

        Long productId = dataSearch.getProductId();
        String customerName = dataSearch.getCustomerName();
        String accountNo = dataSearch.getAccountNo();
        String accountServiceNo = dataSearch.getAccountServiceNo();
        String customerIdentityNo = dataSearch.getCustomerIdentityNo();

        String productGroup = dataSearch.getProductGroup();
        String productType = dataSearch.getProductType();
        String province = dataSearch.getProvince();
        String district = dataSearch.getDistrict();
        String customerBusType = dataSearch.getCustomerBusType();
        String medicalNo = dataSearch.getMedicalNo();
        String tel = dataSearch.getTel();
        String fromDate = dataSearch.getFromDate();
        String toDate = dataSearch.getToDate();

        if (!DataUtil.isNullObject(productId)) {
            sql.append("AND P.PRODUCT_ID = :productId ");
            params.put("productId", productId);
        }

        if (!DataUtil.isStringNullOrEmpty(customerName)) {
            sql.append("AND UPPER(C.NAME) LIKE :customerName ");
            params.put("customerName", "%" + customerName.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(accountNo)) {
            sql.append("AND UPPER(S.ACCOUNT_NO) LIKE :accountNo ");
            params.put("accountNo", "%" + accountNo.toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(accountServiceNo)) {
            sql.append("AND UPPER(S.APP_ACCOUNT_NO) LIKE :accountServiceNo ");
            params.put("accountServiceNo", "%" + accountServiceNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(customerIdentityNo)) {
            sql.append("AND UPPER(CI.ID_NO) LIKE :customerIdentityNo ");
            params.put("customerIdentityNo", "%" + customerIdentityNo.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(productGroup)) {
            sql.append("AND P.PRODUCT_GROUP_ID = :productGroup ");
            params.put("productGroup", productGroup);
        }

        if (!DataUtil.isNullOrEmpty(productType)) {
            sql.append("AND P.PRODUCT_TYPE = :productType ");
            params.put("productType", productType);
        }

        if (!DataUtil.isStringNullOrEmpty(province)) {
            sql.append("AND C.PROVINCE = :province ");
            params.put("province", province);
        }

        if (!DataUtil.isStringNullOrEmpty(district)) {
            sql.append("AND C.DISTRICT = :district ");
            params.put("district", district);
        }

        if (!DataUtil.isStringNullOrEmpty(customerBusType)) {
            sql.append("AND C.CUSTOMER_BUS_TYPE = :customerBusType ");
            params.put("customerBusType", customerBusType);
        }

        if (!DataUtil.isStringNullOrEmpty(medicalNo)) {
            sql.append("AND S.SUBSCRIBER_ID IN (SELECT SUBSCRIBER_ID FROM SUBSCRIBER_EXT_MEDICAL WHERE UPPER(MEDICAL_NO) LIKE :medicalNo ) ");
            params.put("medicalNo", "%" + medicalNo + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(tel)) {
            sql.append("AND C.TEL LIKE :tel ");
            params.put("tel", "%" + tel + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(fromDate) && !DataUtil.isStringNullOrEmpty(toDate)) {
            sql.append("AND S.EFFECTIVE_DATE BETWEEN :fromDate AND :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else {
            if (!DataUtil.isStringNullOrEmpty(fromDate)) {
                sql.append("AND S.EFFECTIVE_DATE >= STR_TO_DATE( :fromDate ,'%Y-%m-%d') ");
                params.put("fromDate", fromDate);
            }

            if (!DataUtil.isStringNullOrEmpty(toDate)) {
                sql.append("AND S.EFFECTIVE_DATE <= STR_TO_DATE( :toDate ,'%Y-%m-%d') ");
                params.put("toDate", toDate);
            }
        }

        sql.append("ORDER BY C.CREATE_DATETIME DESC ");

        Query query = em.createNativeQuery(sql.toString());
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();

        CustomerSearchAfterSaleSdo sdo;
        for (Object[] item : queryResult) {
            sdo = new CustomerSearchAfterSaleSdo();
            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        Long countAllItem = countAllItemSearchProductAfterSale(dataSearch);

        return new PageImpl<>(result, pageRequest, countAllItem);
    }
    //</editor-fold>

    //<editor-fold desc="Get customer information - Find by Id">
    private CustomerFindByIdSdo getCustomerInfoFindById(Long customerId) {
        CustomerFindByIdSdo result = new CustomerFindByIdSdo();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("C.CUSTOMER_ID, ");
        sql.append("C.CUSTOMER_BUS_TYPE, ");
        sql.append("C.CUSTOMER_TYPE, ");
        sql.append("C.NAME, ");
        sql.append("CI.ID_TYPE, ");
        sql.append("CI.ID_NO, ");
        sql.append("C.BIRTH_DATE, ");
        sql.append("C.TEL, ");
        sql.append("C.EMAIL, ");
        sql.append("C.ADDRESS, ");
        sql.append("C.AREA_CODE, ");
        sql.append("C.PROVINCE, ");
        sql.append("C.DISTRICT, ");
        sql.append("C.PRECINCT, ");
        sql.append("C.REPRESENT_NAME, ");
        sql.append("C.REPRESENT_ID_NO, ");
        sql.append("C.REPRESENT_TITLE, ");
        sql.append("C.REPRESENT_TEL, ");
        sql.append("C.REPRESENT_EMAIL ");
        sql.append("FROM CUSTOMER C ");
        sql.append("LEFT JOIN CUSTOMER_IDENTITY CI ON C.CUSTOMER_ID = CI.CUSTOMER_ID ");
        sql.append("WHERE C.CUSTOMER_ID = :customerId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerId", customerId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get list contract - Find by id">
    private List<ContractCustomerFindByIdSdo> getListContractFindById(Long customerId) {
        List<ContractCustomerFindByIdSdo> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("CC.CONTRACT_ID, ");
        sql.append("P.PRODUCT_NAME, ");
        sql.append("CC.EFFECT_DATE, ");
        sql.append("CC.EXPIRE_DATE, ");
        sql.append("PO.PRODUCT_OFFER_ID, ");
        sql.append("PO.NAME, ");
        sql.append("CC.CONTRACT_NO, ");
        sql.append("S.ACCOUNT_NO, ");
        sql.append("CC.STATUS AS CONTRACT_STATUS, ");
        sql.append("SEM.MEDICAL_PERMISSION_NO, ");
        sql.append("S.EFFECTIVE_DATE, ");
        sql.append("S.STATUS SUBS_STATUS, ");
        sql.append("S.SUBSCRIBER_ID ");
        sql.append("FROM CUSTOMER_CONTRACT CC ");
        sql.append("LEFT JOIN SUBSCRIBER S ON S.CUSTOMER_CONTRACT_ID = CC.CONTRACT_ID ");
        sql.append("LEFT JOIN SUBSCRIBER_EXT_MEDICAL SEM ON S.SUBSCRIBER_ID = SEM.SUBSCRIBER_ID ");
        sql.append("LEFT JOIN PRODUCT_OFFER PO ON S.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID ");
        sql.append("LEFT JOIN PRODUCT P ON PO.PRODUCT_ID = P.PRODUCT_ID ");
        sql.append("WHERE CC.CUSTOMER_ID = :customerId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerId", customerId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            ContractCustomerFindByIdSdo sdo = new ContractCustomerFindByIdSdo();

            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get list document - Find by id">
    private List<DocumentCustomerFindByIdSdo> getListDocumentFindById(Long customerId) {
        List<DocumentCustomerFindByIdSdo> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("D.DOCUMENT_ID, ");
        sql.append("D.TYPE, ");
        sql.append("D.NAME ");
        sql.append("FROM DOCUMENT D ");
        sql.append("LEFT JOIN DOCUMENT_MAPPING DM ON DM.DOCUMENT_ID = D.DOCUMENT_ID ");
        sql.append("LEFT JOIN CUSTOMER C ON C.CUSTOMER_ID = DM.MAPPING_OBJECT_ID ");
        sql.append("AND DM.OBJECT_TYPE = 'CUSTOMER' ");
        sql.append("WHERE C.CUSTOMER_ID = :customerId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerId", customerId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            DocumentCustomerFindByIdSdo sdo = new DocumentCustomerFindByIdSdo();

            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Search customer by id">
    @Override
    public CustomerFindByIdSdo searchCustomerById(Long customerId) throws ParseException, IllegalAccessException {
        CustomerFindByIdSdo result = getCustomerInfoFindById(customerId);

        List<ContractCustomerFindByIdSdo> listContract = getListContractFindById(customerId);
        List<DocumentCustomerFindByIdSdo> listDocument = getListDocumentFindById(customerId);

        result.setListContract(listContract);
        result.setListDocument(listDocument);

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Is exist">
    @Override
    public boolean isExist(Long customerId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER WHERE CUSTOMER_ID = :customerId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerId", customerId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>
}
