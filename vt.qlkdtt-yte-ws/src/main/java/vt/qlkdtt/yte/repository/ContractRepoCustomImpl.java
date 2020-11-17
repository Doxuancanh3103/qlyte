package vt.qlkdtt.yte.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.service.sdi.ContractSearchSdi;
import vt.qlkdtt.yte.service.sdo.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ContractRepoCustomImpl implements ContractRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="Count search contract">
    public long countSearchContract(ContractSearchSdi contractSearchSdi) {
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append("SELECT COUNT(*) ");
        strQuery.append("FROM CUSTOMER_CONTRACT CC ");
        strQuery.append("INNER JOIN CUSTOMER C ON CC.CUSTOMER_ID = C.CUSTOMER_ID ");
        strQuery.append("INNER JOIN PRODUCT_OFFER PO ON PO.PRODUCT_OFFER_ID = CC.PRODUCT_OFFER_ID ");
        strQuery.append("INNER JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID ");
        strQuery.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrZero(contractSearchSdi.getProductGroupId())) {
            strQuery.append(" AND P.PRODUCT_GROUP_ID = :productGroupId ");
            parameterMap.put("productGroupId", contractSearchSdi.getProductGroupId());
        }

        if (!DataUtil.isNullOrZero(contractSearchSdi.getProductId())) {
            strQuery.append("AND P.PRODUCT_ID = :productId ");
            parameterMap.put("productId", contractSearchSdi.getProductId());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getProvince())) {
            strQuery.append(" AND C.PROVINCE = :province ");
            parameterMap.put("province", contractSearchSdi.getProvince());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getDistrict())) {
            strQuery.append(" AND C.DISTRICT = :district ");
            parameterMap.put("district", contractSearchSdi.getDistrict());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getFromDate())) {
            strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') >= STR_TO_DATE(:fromDate,'%Y-%m-%d') ");
            parameterMap.put("fromDate", contractSearchSdi.getFromDate());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getToDate())) {
            strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') <= STR_TO_DATE(:toDate,'%Y-%m-%d') ");
            parameterMap.put("toDate", contractSearchSdi.getToDate());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getContractNo())) {
            strQuery.append(" AND UPPER(CC.CONTRACT_NO) LIKE :contractNo ");
            parameterMap.put("contractNo", "%" + contractSearchSdi.getContractNo().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getCustomerName())) {
            strQuery.append(" AND UPPER(C.NAME) LIKE :customerName ");
            parameterMap.put("customerName", "%" + contractSearchSdi.getCustomerName().toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountNo())) {
            strQuery.append("AND UPPER(CA.ACCOUNT_NO) LIKE :accountNo ");
            parameterMap.put("accountNo", "%" + contractSearchSdi.getAccountNo().toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountServiceNo())) {
            strQuery.append("AND UPPER(CA.ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
            parameterMap.put("accountServiceNo", "%" + contractSearchSdi.getAccountServiceNo().toUpperCase() + "%");
        }

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        Object queryResult = query.getSingleResult();
        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Count search contract by customer">
    public long countSearchContractByCustomer(ContractSearchSdi contractSearchSdi) {
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append("SELECT COUNT(*) ");
        strQuery.append("FROM CUSTOMER_CONTRACT CC ");
        strQuery.append("INNER JOIN CUSTOMER C ON CC.CUSTOMER_ID = C.CUSTOMER_ID ");
        strQuery.append("LEFT JOIN CUSTOMER_ACCOUNT CA ON CC.CUSTOMER_ACCOUNT_ID = CA.ACCOUNT_ID ");
        strQuery.append("INNER JOIN PRODUCT_OFFER PO ON PO.PRODUCT_OFFER_ID = CC.PRODUCT_OFFER_ID ");
        strQuery.append("INNER JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID ");
        strQuery.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrZero(contractSearchSdi.getProductGroupId())) {
            strQuery.append(" AND P.PRODUCT_GROUP_ID = :productGroupId ");
            parameterMap.put("productGroupId", contractSearchSdi.getProductGroupId());
        }

        if (!DataUtil.isNullOrZero(contractSearchSdi.getProductId())) {
            strQuery.append("AND P.PRODUCT_ID = :productId ");
            parameterMap.put("productId", contractSearchSdi.getProductId());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getProvince())) {
            strQuery.append(" AND C.PROVINCE = :province ");
            parameterMap.put("province", contractSearchSdi.getProvince());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getFromDate())) {
            strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') >= STR_TO_DATE(:fromDate,'%Y-%m-%d') ");
            parameterMap.put("fromDate", contractSearchSdi.getFromDate());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getToDate())) {
            strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') <= STR_TO_DATE(:toDate,'%Y-%m-%d') ");
            parameterMap.put("toDate", contractSearchSdi.getToDate());
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getContractNo())) {
            strQuery.append(" AND UPPER(CC.CONTRACT_NO) LIKE :contractNo ");
            parameterMap.put("contractNo", "%" + contractSearchSdi.getContractNo().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getCustomerName())) {
            strQuery.append(" AND UPPER(C.NAME) LIKE :customerName ");
            parameterMap.put("customerName", "%" + contractSearchSdi.getCustomerName().toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountNo())) {
            strQuery.append("AND UPPER(CA.ACCOUNT_NO) LIKE :accountNo ");
            parameterMap.put("accountNo", "%" + contractSearchSdi.getAccountNo().toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountServiceNo())) {
            strQuery.append("AND UPPER(CA.ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
            parameterMap.put("accountServiceNo", "%" + contractSearchSdi.getAccountServiceNo().toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrZero(contractSearchSdi.getCustomerId())) {
            strQuery.append("AND C.CUSTOMER_ID = :customerId ");
            parameterMap.put("customerId", contractSearchSdi.getCustomerId());
        }

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        Object queryResult = query.getSingleResult();
        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Search contract">
    @Override
    public Page<ContractSearchSdo> searchContract(ContractSearchSdi contractSearchSdi, Pageable page) {
        List<ContractSearchSdo> result = new ArrayList<>();
        long count = countSearchContract(contractSearchSdi);
        if (count > 0) {
            StringBuilder strQuery = new StringBuilder();
            Map<String, Object> parameterMap = new HashMap<>();

            strQuery.append("SELECT ");
            strQuery.append("CC.CONTRACT_ID, ");
            strQuery.append("CC.CONTRACT_NO, ");
            strQuery.append("C.NAME, ");
            strQuery.append("(SELECT A.NAME FROM AREA A WHERE A.AREA_CODE = C.PROVINCE) PROVINCE, ");
            strQuery.append("(SELECT A.NAME FROM AREA A WHERE A.AREA_CODE = C.DISTRICT) DISTRICT, ");
            strQuery.append("CC.SIGN_DATE, ");
            strQuery.append("CC.STATUS, ");
            strQuery.append("CC.REPRESENT_NAME ");
            strQuery.append("FROM CUSTOMER_CONTRACT CC ");
            strQuery.append("INNER JOIN CUSTOMER C ON CC.CUSTOMER_ID = C.CUSTOMER_ID ");
            strQuery.append("INNER JOIN PRODUCT_OFFER PO ON PO.PRODUCT_OFFER_ID = CC.PRODUCT_OFFER_ID ");
            strQuery.append("INNER JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID ");
            strQuery.append("WHERE 1 = 1 ");

            if (!DataUtil.isNullOrZero(contractSearchSdi.getProductGroupId())) {
                strQuery.append(" AND P.PRODUCT_GROUP_ID = :productGroupId ");
                parameterMap.put("productGroupId", contractSearchSdi.getProductGroupId());
            }

            if (!DataUtil.isNullOrZero(contractSearchSdi.getProductId())) {
                strQuery.append("AND P.PRODUCT_ID = :productId ");
                parameterMap.put("productId", contractSearchSdi.getProductId());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getProvince())) {
                strQuery.append(" AND C.PROVINCE = :province ");
                parameterMap.put("province", contractSearchSdi.getProvince());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getDistrict())) {
                strQuery.append(" AND C.DISTRICT = :district ");
                parameterMap.put("district", contractSearchSdi.getDistrict());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getFromDate())) {
                strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') >= STR_TO_DATE(:fromDate,'%Y-%m-%d') ");
                parameterMap.put("fromDate", contractSearchSdi.getFromDate());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getToDate())) {
                strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') <= STR_TO_DATE(:toDate,'%Y-%m-%d') ");
                parameterMap.put("toDate", contractSearchSdi.getToDate());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getContractNo())) {
                strQuery.append(" AND UPPER(CC.CONTRACT_NO) LIKE :contractNo ");
                parameterMap.put("contractNo", "%" + contractSearchSdi.getContractNo().toUpperCase() + "%");
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getCustomerName())) {
                strQuery.append(" AND UPPER(C.NAME) LIKE :customerName ");
                parameterMap.put("customerName", "%" + contractSearchSdi.getCustomerName().toUpperCase() + "%");
            }

            if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountNo())) {
                strQuery.append("AND UPPER(CA.ACCOUNT_NO) LIKE :accountNo ");
                parameterMap.put("accountNo", "%" + contractSearchSdi.getAccountNo().toUpperCase() + "%");
            }

            if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountServiceNo())) {
                strQuery.append("AND UPPER(CA.ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
                parameterMap.put("accountServiceNo", "%" + contractSearchSdi.getAccountServiceNo().toUpperCase() + "%");
            }

            strQuery.append("ORDER BY CC.CREATE_DATETIME DESC ");

            Query query = em.createNativeQuery(strQuery.toString());
            query.setMaxResults(page.getPageSize());
            query.setFirstResult((page.getPageNumber()) * page.getPageSize());

            for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
                query.setParameter(p.getKey(), p.getValue());
            }
            List<Object[]> queryResult = query.getResultList();
            ContractSearchSdo dto;
            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] obj : queryResult) {
                    dto = new ContractSearchSdo();
                    dto = DataUtil.convertObjectsToClass(obj, dto);

                    result.add(dto);
                }
            }
        }
        return new PageImpl<>(result, page, count);
    }
    //</editor-fold>

    //<editor-fold desc="Search contract connect">
    @Override
    public Page<ContractSearchConnectSdo> searchContractConnect(ContractSearchSdi contractSearchSdi, Pageable page) {
        List<ContractSearchConnectSdo> result = new ArrayList<>();
        long count = countSearchContractByCustomer(contractSearchSdi);
        if (count > 0) {
            StringBuilder strQuery = new StringBuilder();
            Map<String, Object> parameterMap = new HashMap<>();

            strQuery.append("SELECT ");
            strQuery.append("CC.CONTRACT_ID, ");
            strQuery.append("CC.CONTRACT_NO, ");
            strQuery.append("CC.CUSTOMER_ID, ");
            strQuery.append("C.NAME, ");
            strQuery.append("CC.SIGN_DATE, ");
            strQuery.append("CC.STATUS, ");
            strQuery.append("CA.BILL_NOTIFICATION_METHOD, ");
            strQuery.append("CA.BILL_NOTIFICATION_ADDRESS, ");
            strQuery.append("CA.BILL_ADDRESS, ");
            strQuery.append("CC.REPRESENT_NAME, ");
            strQuery.append("CC.REPRESENT_TITLE, ");
            strQuery.append("CC.REPRESENT_ID_TYPE, ");
            strQuery.append("CC.REPRESENT_ID_NO, ");
            strQuery.append("CC.REPRESENT_EMAIL, ");
            strQuery.append("CC.REPRESENT_TEL, ");
            strQuery.append("CA.PAYMENT_METHOD, ");
            strQuery.append("CC.EFFECT_DATE, ");
            strQuery.append("C.PROVINCE, ");
            strQuery.append("(SELECT A.NAME FROM AREA A WHERE A.AREA_CODE = C.PROVINCE) PROVINCE_NAME, ");
            strQuery.append("C.DISTRICT, ");
            strQuery.append("(SELECT A.NAME FROM AREA A WHERE A.AREA_CODE = C.DISTRICT) DISTRICT_NAME ");
            strQuery.append("FROM CUSTOMER_CONTRACT CC ");
            strQuery.append("INNER JOIN CUSTOMER C ON CC.CUSTOMER_ID = C.CUSTOMER_ID ");
            strQuery.append("LEFT JOIN CUSTOMER_ACCOUNT CA ON CC.CUSTOMER_ACCOUNT_ID = CA.ACCOUNT_ID ");
            strQuery.append("INNER JOIN PRODUCT_OFFER PO ON PO.PRODUCT_OFFER_ID = CC.PRODUCT_OFFER_ID ");
            strQuery.append("INNER JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID ");
            strQuery.append("WHERE 1 = 1 ");

            if (!DataUtil.isNullOrZero(contractSearchSdi.getProductGroupId())) {
                strQuery.append(" AND P.PRODUCT_GROUP_ID = :productGroupId ");
                parameterMap.put("productGroupId", contractSearchSdi.getProductGroupId());
            }

            if (!DataUtil.isNullOrZero(contractSearchSdi.getProductId())) {
                strQuery.append("AND P.PRODUCT_ID = :productId ");
                parameterMap.put("productId", contractSearchSdi.getProductId());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getProvince())) {
                strQuery.append(" AND C.PROVINCE = :province ");
                parameterMap.put("province", contractSearchSdi.getProvince());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getFromDate())) {
                strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') >= STR_TO_DATE(:fromDate,'%Y-%m-%d') ");
                parameterMap.put("fromDate", contractSearchSdi.getFromDate());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getToDate())) {
                strQuery.append(" AND DATE_FORMAT(CC.CREATE_DATETIME , '%Y-%m-%d') <= STR_TO_DATE(:toDate,'%Y-%m-%d') ");
                parameterMap.put("toDate", contractSearchSdi.getToDate());
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getContractNo())) {
                strQuery.append(" AND UPPER(CC.CONTRACT_NO) LIKE :contractNo ");
                parameterMap.put("contractNo", "%" + contractSearchSdi.getContractNo().toUpperCase() + "%");
            }

            if (!DataUtil.isStringNullOrEmpty(contractSearchSdi.getCustomerName())) {
                strQuery.append(" AND UPPER(C.NAME) LIKE :customerName ");
                parameterMap.put("customerName", "%" + contractSearchSdi.getCustomerName().toUpperCase() + "%");
            }

            if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountNo())) {
                strQuery.append("AND UPPER(CA.ACCOUNT_NO) LIKE :accountNo ");
                parameterMap.put("accountNo", "%" + contractSearchSdi.getAccountNo().toUpperCase() + "%");
            }

            if (!DataUtil.isNullOrEmpty(contractSearchSdi.getAccountServiceNo())) {
                strQuery.append("AND UPPER(CA.ACCOUNT_SERVICE_NO) LIKE :accountServiceNo ");
                parameterMap.put("accountServiceNo", "%" + contractSearchSdi.getAccountServiceNo().toUpperCase() + "%");
            }

            if (!DataUtil.isNullOrZero(contractSearchSdi.getCustomerId())) {
                strQuery.append("AND C.CUSTOMER_ID = :customerId ");
                parameterMap.put("customerId", contractSearchSdi.getCustomerId());
            }

            strQuery.append("ORDER BY CC.CREATE_DATETIME DESC ");

            Query query = em.createNativeQuery(strQuery.toString());
            query.setMaxResults(page.getPageSize());
            query.setFirstResult((page.getPageNumber()) * page.getPageSize());

            for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
                query.setParameter(p.getKey(), p.getValue());
            }
            List<Object[]> queryResult = query.getResultList();
            ContractSearchConnectSdo dto;
            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] obj : queryResult) {
                    dto = new ContractSearchConnectSdo();
                    dto = DataUtil.convertObjectsToClass(obj, dto);

                    result.add(dto);
                }
            }
        }
        return new PageImpl<>(result, page, count);
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @Override
    public ContractFindByIdSdo findById(long customerContractId) {
        StringBuilder strQuery;
        Query query;
        List<Object[]> queryResult;
        ContractFindByIdSdo dto = new ContractFindByIdSdo();

        if (!DataUtil.isNullOrZero(customerContractId)) {
            //Thông tin hợp đồng
            strQuery = new StringBuilder();
            strQuery.append(" select cc.CONTRACT_ID , cc.CONTRACT_NO, cc.SIGN_DATE , cc.REPRESENT_NAME , cc.REPRESENT_ID_NO , cc.REPRESENT_TITLE ");
            strQuery.append(" , cc.REPRESENT_TEL , cc.REPRESENT_EMAIL , cc.CUSTOMER_ID, cc.STATUS ");
            strQuery.append(" from CUSTOMER_CONTRACT cc  ");
            strQuery.append(" where cc.CONTRACT_ID = :customerContractId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("customerContractId", customerContractId);

            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] obj : queryResult) {
                    dto.setContractId(DataUtil.safeToLong(obj[0]));
                    dto.setContractNo(DataUtil.safeToString(obj[1]));
                    dto.setSignDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(obj[2])));
                    dto.setRepresentName(DataUtil.safeToString(obj[3]));
                    dto.setRepresentIdNo(DataUtil.safeToString(obj[4]));
                    dto.setRepresentTitle(DataUtil.safeToString(obj[5]));
                    dto.setRepresentTel(DataUtil.safeToString(obj[6]));
                    dto.setRepresentEmail(DataUtil.safeToString(obj[7]));
                    dto.setCustomerId(DataUtil.safeToLong(obj[8]));
                    dto.setContractStatus(DataUtil.safeToString(obj[9]));
                }
            }
        }

        if (!DataUtil.isNullOrZero(dto.getCustomerId())) {
            //Thông tin khách hàng
            strQuery = new StringBuilder();
            strQuery.append("SELECT ");
            strQuery.append("c.CUSTOMER_BUS_TYPE, ");
            strQuery.append("c.CUSTOMER_TYPE, ");
            strQuery.append("c.`NAME`, ");
            strQuery.append("cem.MEDICAL_PERMISSION_NO, ");
            strQuery.append("ci.ID_NO, ");
            strQuery.append("c.BIRTH_DATE, ");
            strQuery.append("c.TEL, ");
            strQuery.append("c.EMAIL, ");
            strQuery.append("c.PROVINCE, ");
            strQuery.append("c.DISTRICT, ");
            strQuery.append("c.PRECINCT, ");
            strQuery.append("c.ADDRESS, ");
            strQuery.append("c.REPRESENT_NAME, ");
            strQuery.append("c.REPRESENT_ID_NO, ");
            strQuery.append("c.REPRESENT_TITLE, ");
            strQuery.append("c.REPRESENT_TEL, ");
            strQuery.append("c.REPRESENT_EMAIL, ");
            strQuery.append("c.CUSTOMER_ID, ");
            strQuery.append("(select name from AREA where PROVINCE = c.PROVINCE and DISTRICT is null) PROVINCE_NAME, ");
            strQuery.append("(select name from AREA where PROVINCE = c.PROVINCE and DISTRICT = c.DISTRICT and PRECINCT is null) DISTRICT_NAME, ");
            strQuery.append("(select name from AREA where PROVINCE = c.PROVINCE and DISTRICT = c.DISTRICT and PRECINCT = c.PRECINCT and STREET_BLOCK is NULL) PRECINCT_NAME ");
            strQuery.append(" from CUSTOMER c  ");
            strQuery.append(" left join CUSTOMER_EXT_MEDICAL cem on (cem.CUSTOMER_ID = c.CUSTOMER_ID) ");
            strQuery.append(" left join CUSTOMER_IDENTITY ci on (ci.CUSTOMER_ID = c.CUSTOMER_ID) ");
            strQuery.append(" where c.CUSTOMER_ID = :customerId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("customerId", dto.getCustomerId());

            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object obj : queryResult) {
                    Object[] objects = (Object[]) obj;

                    dto.setCustomerBusType(DataUtil.safeToString(objects[0]));
                    dto.setCustomerType(DataUtil.safeToString(objects[1]));
                    dto.setCustomerName(DataUtil.safeToString(objects[2]));
                    dto.setMedicalPermissionNo(DataUtil.safeToString(objects[3]));
                    dto.setCustomerIdentityNo(DataUtil.safeToString(objects[4]));
                    dto.setBirthDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(objects[5])));
                    dto.setTel(DataUtil.safeToString(objects[6]));
                    dto.setEmail(DataUtil.safeToString(objects[7]));
                    dto.setProvince(DataUtil.safeToString(objects[8]));
                    dto.setDistrict(DataUtil.safeToString(objects[9]));
                    dto.setPrecinct(DataUtil.safeToString(objects[10]));
                    dto.setAddress(DataUtil.safeToString(objects[11]));
                    dto.setCustomerRepresentName(DataUtil.safeToString(objects[12]));
                    dto.setCustomerRepresentIdNo(DataUtil.safeToString(objects[13]));
                    dto.setCustomerRepresentTitle(DataUtil.safeToString(objects[14]));
                    dto.setCustomerRepresentTel(DataUtil.safeToString(objects[15]));
                    dto.setCustomerRepresentEmail(DataUtil.safeToString(objects[16]));
                    dto.setCustomerId(DataUtil.safeToLong(objects[17]));
                    dto.setProvinceName(DataUtil.safeToString(objects[18]));
                    dto.setDistrictName(DataUtil.safeToString(objects[19]));
                    dto.setPrecinctName(DataUtil.safeToString(objects[20]));
                }

                //CUSTOMER_ACCOUNT
                strQuery = new StringBuilder();
                strQuery.append(" select ca.BILL_NOTIFICATION_METHOD , ca.BILL_ADDRESS, ca.BILL_NOTIFICATION_ADDRESS, ca.PAYMENT_METHOD, ca.ACCOUNT_NO, ca.ACCOUNT_ID ");
                strQuery.append(" from CUSTOMER_ACCOUNT ca ");
                strQuery.append(" where ca.CUSTOMER_ID = :customerId ");

                query = em.createNativeQuery(strQuery.toString());
                query.setParameter("customerId", dto.getCustomerId());

                queryResult = query.getResultList();

                if (!DataUtil.isNullOrEmpty(queryResult)) {
                    for (Object obj : queryResult) {
                        Object[] objects = (Object[]) obj;

                        dto.setBillNotificationMethod(DataUtil.safeToString(objects[0]));
                        dto.setBillAddress(DataUtil.safeToString(objects[1]));
                        dto.setBillNotificationAddress(DataUtil.safeToString(objects[2]));
                        dto.setPaymentMethod(DataUtil.safeToString(objects[3]));
                        dto.setAccountNo(DataUtil.safeToString(objects[4]));
                        dto.setAccountId(DataUtil.safeToLong(objects[5]));
                    }
                }
            }

            //Thông tin dịch vụ
            strQuery = new StringBuilder();
            strQuery.append(" select S.SUBSCRIBER_ID , S.PRODUCT_ID , P.PRODUCT_NAME , PO.PRODUCT_OFFER_ID ");
            strQuery.append(" , PO.`NAME` as PRODUCT_OFFER_NAME , S.ACCOUNT_NO , S.STA_DATE , S.END_DATE ");
            strQuery.append(" , S.STATUS , S.PRICE_SALES , S.VOL_BASE , S.VOL_PROMOTION ,  S.PROJECT_CODE , S.PROMOTION_CODE ");
            strQuery.append(" , P.PRODUCT_CODE , PO.CODE , S.APP_ACCOUNT_NO ");
            strQuery.append(" from SUBSCRIBER S  ");
            strQuery.append(" left join PRODUCT P ON (P.PRODUCT_ID = S.PRODUCT_ID) ");
            strQuery.append(" left join PRODUCT_OFFER PO ON (PO.PRODUCT_OFFER_ID = S.PRODUCT_OFFER_ID) ");
            strQuery.append(" where S.CUSTOMER_CONTRACT_ID = :contractId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("contractId", dto.getContractId());

            queryResult = query.getResultList();

            List<SubscriberSdo> lsSubscriberSdos = new ArrayList<>();
            SubscriberSdo subscriberSdo;
            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object obj : queryResult) {
                    Object[] objects = (Object[]) obj;
                    subscriberSdo = new SubscriberSdo();

                    subscriberSdo.setSubscriberId(DataUtil.safeToLong(objects[0]));
                    subscriberSdo.setProductId(DataUtil.safeToLong(objects[1]));
                    subscriberSdo.setProductName(DataUtil.safeToString(objects[2]));
                    subscriberSdo.setProductOffferId(DataUtil.safeToLong(objects[3]));
                    subscriberSdo.setProductOfferName(DataUtil.safeToString(objects[4]));
                    subscriberSdo.setAccountNo(DataUtil.safeToString(objects[5]));
                    subscriberSdo.setEffectDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(objects[6])));
                    subscriberSdo.setDueDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(objects[7])));
                    subscriberSdo.setStatus(DataUtil.safeToString(objects[8]));
                    subscriberSdo.setPriceSale(DataUtil.safeToLong(objects[9]));
                    subscriberSdo.setVolBase(DataUtil.safeToLong(objects[10]));
                    subscriberSdo.setVolPromotion(DataUtil.safeToLong(objects[11]));
                    subscriberSdo.setProjectCode(DataUtil.safeToString(objects[12]));
                    subscriberSdo.setPromotionName(DataUtil.safeToString(objects[13]));
                    subscriberSdo.setProductCode(DataUtil.safeToString(objects[14]));
                    subscriberSdo.setProductOfferCode(DataUtil.safeToString(objects[15]));
                    subscriberSdo.setAccountServiceNo(DataUtil.safeToString(objects[16]));

                    lsSubscriberSdos.add(subscriberSdo);
                }
            }

            if (lsSubscriberSdos != null && !lsSubscriberSdos.isEmpty()) {
                dto.setLstSubscriberSdos(lsSubscriberSdos);
            }

            //Thông tin hồ sơ
            strQuery = new StringBuilder();
            strQuery.append(" select D.DOCUMENT_ID , D.TYPE , D.`NAME`, ");
            strQuery.append("(SELECT GLV.NAME ");
            strQuery.append("FROM GLOBAL_LIST GL, GLOBAL_LIST_VALUE GLV ");
            strQuery.append("WHERE D.TYPE = GLV.VALUE ");
            strQuery.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
            strQuery.append("AND GL.CODE = 'DOCUMENT_TYPE' ");
            strQuery.append(") DOCUMENT_TYPE_NAME ");
            strQuery.append(" from DOCUMENT D , DOCUMENT_MAPPING DM ");
            strQuery.append(" where D.DOCUMENT_ID = DM.DOCUMENT_ID ");
            strQuery.append(" and DM.STATUS = '1' ");
            strQuery.append(" and DM.OBJECT_TYPE = 'CUSTOMER' ");
            strQuery.append(" and DM.MAPPING_OBJECT_ID = :customerId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("customerId", dto.getCustomerId());

            queryResult = query.getResultList();

            List<DocumentSdo> lstDocumentSdos = new ArrayList<>();
            DocumentSdo documentSdo;
            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object obj : queryResult) {
                    Object[] objects = (Object[]) obj;
                    documentSdo = new DocumentSdo();

                    documentSdo.setDocumentId(DataUtil.safeToLong(objects[0]));
                    documentSdo.setDocumentType(DataUtil.safeToString(objects[1]));
                    documentSdo.setDocumentName(DataUtil.safeToString(objects[2]));
                    documentSdo.setDocumentTypeName(DataUtil.safeToString(objects[3]));

                    lstDocumentSdos.add(documentSdo);
                }
            }

            if (lstDocumentSdos != null && !lstDocumentSdos.isEmpty()) {
                dto.setLstDocumentSdos(lstDocumentSdos);
            }

        }

        return dto;
    }
    //</editor-fold>

    //<editor-fold desc="is exist">
    @Override
    public boolean isExits(String contractNo, long contractId) {
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();
        strQuery.append(" SELECT");
        strQuery.append(" COUNT(1)");
        strQuery.append(" FROM ");
        strQuery.append(" CUSTOMER_CONTRACT ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append(" AND UPPER(CONTRACT_NO) = :contractNo");
        parameterMap.put("contractNo", contractNo.toUpperCase());

        if (!DataUtil.isNullOrZero(contractId)) {
            strQuery.append(" AND CONTRACT_ID != :contractId");
            parameterMap.put("contractId", contractId);
        }

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue() > 0L;
    }
    //</editor-fold>

    //<editor-fold desc="Update bill info">
    @Override
    public boolean updateBillInfo(String billNotificationMethod, String billAddress, long customerAccountId) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        sql.append(" UPDATE CUSTOMER_ACCOUNT ");
        sql.append(" SET UPDATE_DATETIME = now() , UPDATE_USER = 'SYSTEM' ");

        if (!DataUtil.isStringNullOrEmpty(billNotificationMethod)) {
            sql.append(" , BILL_NOTIFICATION_METHOD = :billNotificationMethod ");
            parameterMap.put("billNotificationMethod", billNotificationMethod);
        }

        if (!DataUtil.isStringNullOrEmpty(billAddress)) {
            sql.append(" , BILL_ADDRESS = :billAddress ");
            parameterMap.put("billAddress", billAddress);
        }

        sql.append(" WHERE ACCOUNT_ID = :customerAccountId ");

        Query query = em.createNativeQuery(sql.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }

        query.setParameter("customerAccountId", customerAccountId);

        Object queryResult = query.executeUpdate();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>

    @Override
    public Long getAccountId(Long contractId) {
        String sql = "SELECT CUSTOMER_ACCOUNT_ID FROM CUSTOMER_CONTRACT WHERE CONTRACT_ID = :contractId";

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("contractId", contractId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult);
    }

}


