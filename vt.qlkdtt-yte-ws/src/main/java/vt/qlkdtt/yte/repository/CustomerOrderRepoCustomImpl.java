package vt.qlkdtt.yte.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerOrder;
import vt.qlkdtt.yte.service.sdi.CustomerOrderSearchSdi;
import vt.qlkdtt.yte.service.sdo.CustomerOrderDetailSearchSdo;
import vt.qlkdtt.yte.service.sdo.CustomerOrderSearchSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerOrderRepoCustomImpl implements CustomerOrderRepoCustom {
    @Autowired
    EntityManager em;

    private Long countAllItem(CustomerOrderSearchSdi customerOrderSearchSdi) {
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append("SELECT COUNT(*) ");
        strQuery.append("FROM CUSTOMER_ORDER co ");
        strQuery.append("LEFT JOIN PRODUCT p ON ( p.PRODUCT_ID = co.PRODUCT_ID ) ");
        strQuery.append("LEFT JOIN CUSTOMER c ON ( c.CUSTOMER_ID = co.CUSTOMER_ID ) ");
        strQuery.append("LEFT JOIN SUBSCRIBER s ON ( co.SUBSCRIBER_ID = s.SUBSCRIBER_ID ) ");
        strQuery.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrZero(customerOrderSearchSdi.getProductId())) {
            strQuery.append(" AND co.PRODUCT_ID = :productId ");
            parameterMap.put("productId", customerOrderSearchSdi.getProductId());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderType())) {
            strQuery.append(" AND co.ORDER_TYPE = :orderType ");
            parameterMap.put("orderType", customerOrderSearchSdi.getOrderType());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderActionTypeId())) {
            strQuery.append(" AND co.ORDER_ACTION_TYPE_ID = :orderActionTypeId ");
            parameterMap.put("orderActionTypeId", customerOrderSearchSdi.getOrderActionTypeId());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderContactName())) {
            strQuery.append(" AND UPPER(co.ORDER_CONTACT_NAME) LIKE :orderContactName ");
            parameterMap.put("orderContactName", "%" + customerOrderSearchSdi.getOrderContactName().toUpperCase() + "%");
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getAssigneeCode())) {
            strQuery.append(" AND UPPER(co.ASSIGNEE_CODE) LIKE :asigneeCode ");
            parameterMap.put("asigneeCode", "%" + customerOrderSearchSdi.getAssigneeCode().toUpperCase() + "%");
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getProvinceCode())) {
            strQuery.append(" AND co.PROVINCE = :provinceCode ");
            parameterMap.put("provinceCode", customerOrderSearchSdi.getProvinceCode());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getCustomerCodeName())) {
            strQuery.append(" AND UPPER(c.NAME) LIKE :customerCodeName ");
            parameterMap.put("customerCodeName", "%" + customerOrderSearchSdi.getCustomerCodeName().toUpperCase() + "%");
        }
        if (!DataUtil.isNullOrZero(customerOrderSearchSdi.getCustomerOrderId())) {
            strQuery.append(" AND co.CUSTOMER_ORDER_ID = :customerOrderId ");
            parameterMap.put("customerOrderId", customerOrderSearchSdi.getCustomerOrderId());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderFromDate())) {
            strQuery.append(" AND DATE_FORMAT(co.ORDER_DATETIME, '%Y-%m-%d') >= STR_TO_DATE(:orderFromDate,'%Y-%m-%d') ");
            parameterMap.put("orderFromDate", customerOrderSearchSdi.getOrderFromDate());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderToDate())) {
            strQuery.append(" AND DATE_FORMAT(co.ORDER_DATETIME, '%Y-%m-%d') <= STR_TO_DATE(:orderToDate,'%Y-%m-%d')");
            parameterMap.put("orderToDate", customerOrderSearchSdi.getOrderToDate());
        }

        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getDueFromDate())) {
            strQuery.append(" AND DATE_FORMAT(co.DUE_DATETIME, '%Y-%m-%d') >= STR_TO_DATE(:dueFromDate,'%Y-%m-%d') ");
            parameterMap.put("dueFromDate", customerOrderSearchSdi.getDueFromDate());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getDueToDate())) {
            strQuery.append(" AND DATE_FORMAT(co.DUE_DATETIME, '%Y-%m-%d') <= STR_TO_DATE(:dueToDate,'%Y-%m-%d') ");
            parameterMap.put("dueToDate", customerOrderSearchSdi.getDueToDate());
        }

        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getStatus())) {
            strQuery.append(" AND co.STATUS = :status ");
            parameterMap.put("status", customerOrderSearchSdi.getStatus());
        }

        if (!DataUtil.isNullOrEmpty(customerOrderSearchSdi.getAccountNo())) {
            strQuery.append("AND UPPER(s.ACCOUNT_NO) LIKE :accountNo ");
            parameterMap.put("accountNo", "%" + customerOrderSearchSdi.getAccountNo().toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(customerOrderSearchSdi.getAccountServiceNo())) {
            strQuery.append("AND UPPER(s.APP_ACCOUNT_NO) LIKE :accountServiceNo ");
            parameterMap.put("accountServiceNo", "%" + customerOrderSearchSdi.getAccountServiceNo().toUpperCase() + "%");
        }

        Query query = em.createNativeQuery(strQuery.toString());

        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult);
    }

    @Override
    public Page<CustomerOrderSearchSdo> searchCustomerOrder(CustomerOrderSearchSdi customerOrderSearchSdi, Pageable page) {
        List<CustomerOrderSearchSdo> result = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append("SELECT ");
        strQuery.append("co.CUSTOMER_ORDER_ID, ");
        strQuery.append("co.ORDER_TYPE, ");
        strQuery.append("co.PRODUCT_ID, ");
        strQuery.append("p.PRODUCT_NAME, ");
        strQuery.append("p.PRODUCT_CODE, ");
        strQuery.append("(SELECT NAME FROM AREA WHERE AREA_CODE = co.PROVINCE AND DISTRICT IS NULL) AS PROVINCE_NAME, ");
        strQuery.append("co.PROVINCE AS PROVINCE_CODE, ");
        strQuery.append("co.CUSTOMER_ACCOUNT_ID, ");
        strQuery.append("s.ACCOUNT_NO, ");
        strQuery.append("s.APP_ACCOUNT_NO, ");
        strQuery.append("co.CUSTOMER_ID, ");
        strQuery.append("c.`NAME`, ");
        strQuery.append("co.ORDER_CONTACT_NAME, ");
        strQuery.append("co.ORDER_DATETIME, ");
        strQuery.append("co.`STATUS`, ");
        strQuery.append("co.ASSIGNEE_CODE, ");
        strQuery.append("co.REPORTER_CODE, ");
        strQuery.append("co.DUE_DATETIME, ");
        strQuery.append("co.EXT_DUE_DATETIME, ");
        strQuery.append("co.ORDER_ACTION_TYPE_ID, ");
        strQuery.append("(SELECT cfg.LIMIT_TIME FROM CUSTOMER_ORDER_CONFIG cfg WHERE cfg.ORDER_TYPE_ID = co.ORDER_TYPE AND cfg.ACTION_TYPE_ID = co.ORDER_ACTION_TYPE_ID AND cfg.PRODUCT_ID = co.PRODUCT_ID) AS LIMIT_TIME, ");
        strQuery.append("(SELECT cfg.EXTEND_TIME FROM CUSTOMER_ORDER_CONFIG cfg WHERE cfg.ORDER_TYPE_ID = co.ORDER_TYPE AND cfg.ACTION_TYPE_ID = co.ORDER_ACTION_TYPE_ID AND cfg.PRODUCT_ID = co.PRODUCT_ID) AS EXTEND_TIME ");
        strQuery.append("FROM CUSTOMER_ORDER co ");
        strQuery.append("LEFT JOIN PRODUCT p ON ( p.PRODUCT_ID = co.PRODUCT_ID ) ");
        strQuery.append("LEFT JOIN CUSTOMER c ON ( c.CUSTOMER_ID = co.CUSTOMER_ID ) ");
        strQuery.append("LEFT JOIN SUBSCRIBER s ON ( co.SUBSCRIBER_ID = s.SUBSCRIBER_ID ) ");
        strQuery.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrZero(customerOrderSearchSdi.getProductId())) {
            strQuery.append(" AND co.PRODUCT_ID = :productId ");
            parameterMap.put("productId", customerOrderSearchSdi.getProductId());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderType())) {
            strQuery.append(" AND co.ORDER_TYPE = :orderType ");
            parameterMap.put("orderType", customerOrderSearchSdi.getOrderType());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderActionTypeId())) {
            strQuery.append(" AND co.ORDER_ACTION_TYPE_ID = :orderActionTypeId ");
            parameterMap.put("orderActionTypeId", customerOrderSearchSdi.getOrderActionTypeId());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderContactName())) {
            strQuery.append(" AND UPPER(co.ORDER_CONTACT_NAME) LIKE :orderContactName ");
            parameterMap.put("orderContactName", "%" + customerOrderSearchSdi.getOrderContactName().toUpperCase() + "%");
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getAssigneeCode())) {
            strQuery.append(" AND UPPER(co.ASSIGNEE_CODE) LIKE :asigneeCode ");
            parameterMap.put("asigneeCode", "%" + customerOrderSearchSdi.getAssigneeCode().toUpperCase() + "%");
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getProvinceCode())) {
            strQuery.append(" AND co.PROVINCE = :provinceCode ");
            parameterMap.put("provinceCode", customerOrderSearchSdi.getProvinceCode());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getCustomerCodeName())) {
            strQuery.append(" AND UPPER(c.NAME) LIKE :customerCodeName ");
            parameterMap.put("customerCodeName", "%" + customerOrderSearchSdi.getCustomerCodeName().toUpperCase() + "%");
        }
        if (!DataUtil.isNullOrZero(customerOrderSearchSdi.getCustomerOrderId())) {
            strQuery.append(" AND co.CUSTOMER_ORDER_ID = :customerOrderId ");
            parameterMap.put("customerOrderId", customerOrderSearchSdi.getCustomerOrderId());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderFromDate())) {
            strQuery.append(" AND DATE_FORMAT(co.ORDER_DATETIME, '%Y-%m-%d') >= STR_TO_DATE(:orderFromDate,'%Y-%m-%d') ");
            parameterMap.put("orderFromDate", customerOrderSearchSdi.getOrderFromDate());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getOrderToDate())) {
            strQuery.append(" AND DATE_FORMAT(co.ORDER_DATETIME, '%Y-%m-%d') <= STR_TO_DATE(:orderToDate,'%Y-%m-%d')");
            parameterMap.put("orderToDate", customerOrderSearchSdi.getOrderToDate());
        }

        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getDueFromDate())) {
            strQuery.append(" AND DATE_FORMAT(co.DUE_DATETIME, '%Y-%m-%d') >= STR_TO_DATE(:dueFromDate,'%Y-%m-%d') ");
            parameterMap.put("dueFromDate", customerOrderSearchSdi.getDueFromDate());
        }
        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getDueToDate())) {
            strQuery.append(" AND DATE_FORMAT(co.DUE_DATETIME, '%Y-%m-%d') <= STR_TO_DATE(:dueToDate,'%Y-%m-%d') ");
            parameterMap.put("dueToDate", customerOrderSearchSdi.getDueToDate());
        }

        if (!DataUtil.isStringNullOrEmpty(customerOrderSearchSdi.getStatus())) {
            strQuery.append(" AND co.STATUS = :status ");
            parameterMap.put("status", customerOrderSearchSdi.getStatus());
        }

        if (!DataUtil.isNullOrEmpty(customerOrderSearchSdi.getAccountNo())) {
            strQuery.append("AND UPPER(s.ACCOUNT_NO) LIKE :accountNo ");
            parameterMap.put("accountNo", "%" + customerOrderSearchSdi.getAccountNo().toUpperCase() + "%");
        }

        if (!DataUtil.isNullOrEmpty(customerOrderSearchSdi.getAccountServiceNo())) {
            strQuery.append("AND UPPER(s.APP_ACCOUNT_NO) LIKE :accountServiceNo ");
            parameterMap.put("accountServiceNo", "%" + customerOrderSearchSdi.getAccountServiceNo().toUpperCase() + "%");
        }

        strQuery.append("ORDER BY co.CREATE_DATETIME DESC, co.UPDATE_DATETIME DESC ");

        Query query = em.createNativeQuery(strQuery.toString());
        query.setFirstResult((page.getPageNumber()) * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }

        List<Object[]> queryResult = query.getResultList();
        CustomerOrderSearchSdo dto;
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] objects : queryResult) {
                dto = new CustomerOrderSearchSdo();
                dto = DataUtil.convertObjectsToClass(objects, dto);

                result.add(dto);
            }
        }

        Long countAllItem = countAllItem(customerOrderSearchSdi);

        return new PageImpl<>(result, page, countAllItem);
    }


    @Override
    public CustomerOrderDetailSearchSdo searchOrderDetail(long customerOrderId) {
        //CUSTOMER_ORDER
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select co.ORDER_TYPE , co.CUSTOMER_ORDER_ID , co.ORDER_CONTACT_NAME , co.ORDER_CONTACT_TEL, co.ORDER_CONTACT_EMAIL ");
        strQuery.append(" , co.ORDER_ACTION_TYPE_ID ,co.ASSIGNEE_CODE , co.ORDER_DATETIME , IFNULL(co.EXT_DUE_DATETIME,co.DUE_DATETIME) , co.`STATUS` ");
        strQuery.append(" , co.CUSTOMER_ID , co.SUBSCRIBER_ID , co.CUSTOMER_ACCOUNT_ID ");
        strQuery.append(" from CUSTOMER_ORDER co ");
        strQuery.append(" where co.CUSTOMER_ORDER_ID = :customerOrderId ");

        Query query = em.createNativeQuery(strQuery.toString());
        query.setParameter("customerOrderId", customerOrderId);

        List<Object[]> queryResult = query.getResultList();
        CustomerOrderDetailSearchSdo dto = new CustomerOrderDetailSearchSdo();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] obj : queryResult) {

                dto.setOrderType(DataUtil.safeToString(obj[0]));
                dto.setCustomerOrderId(DataUtil.safeToLong(obj[1]));
                dto.setOrderContactName(DataUtil.safeToString(obj[2]));
                dto.setOrderContactTel(DataUtil.safeToString(obj[3]));
                dto.setOrderContactEmail(DataUtil.safeToString(obj[4]));
                dto.setOrderActionTypeId(DataUtil.safeToString(obj[5]));
                dto.setAssigneeCode(DataUtil.safeToString(obj[6]));
                dto.setOrderDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(obj[7])));
                dto.setDueDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(obj[8])));
                dto.setStatus(DataUtil.safeToString(obj[9]));
                dto.setCustomerId(DataUtil.safeToLong(obj[10]));
                dto.setSubscriberId(DataUtil.safeToLong(obj[11]));
                dto.setCustomerAccountId(DataUtil.safeToLong(obj[12]));
            }
        }

        if (!DataUtil.isNullOrZero(dto.getCustomerId())) {
            //CUSTOMER
            strQuery = new StringBuilder();
            strQuery.append(" select c.CUSTOMER_BUS_TYPE , c.CUSTOMER_TYPE , c.`NAME`, cem.MEDICAL_PERMISSION_NO ");
            strQuery.append(" , ci.ID_NO ,c.BIRTH_DATE , c.TEL , c.EMAIL , c.PROVINCE , c.DISTRICT , c.PRECINCT ");
            strQuery.append(" , c.ADDRESS , c.REPRESENT_NAME , c.REPRESENT_ID_NO , c.REPRESENT_TITLE , c.REPRESENT_TEL , c.REPRESENT_EMAIL , c.CUSTOMER_ID ");
            strQuery.append(" from CUSTOMER c  ");
            strQuery.append(" left join CUSTOMER_EXT_MEDICAL cem on (cem.CUSTOMER_ID = c.CUSTOMER_ID) ");
            strQuery.append(" left join CUSTOMER_IDENTITY ci on (ci.CUSTOMER_ID = c.CUSTOMER_ID) ");
            strQuery.append(" where c.CUSTOMER_ID = :customerId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("customerId", dto.getCustomerId());

            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] obj : queryResult) {
                    dto.setCustomerBusType(DataUtil.safeToString(obj[0]));
                    dto.setCustomerType(DataUtil.safeToString(obj[1]));
                    dto.setCustomerName(DataUtil.safeToString(obj[2]));
                    dto.setMedicalPermissionNo(DataUtil.safeToString(obj[3]));
                    dto.setCustomerIdentityNo(DataUtil.safeToString(obj[4]));
                    dto.setBirthDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(obj[5])));
                    dto.setTel(DataUtil.safeToString(obj[6]));
                    dto.setEmail(DataUtil.safeToString(obj[7]));
                    dto.setProvince(DataUtil.safeToString(obj[8]));
                    dto.setDistrict(DataUtil.safeToString(obj[9]));
                    dto.setPrecinct(DataUtil.safeToString(obj[10]));
                    dto.setAddress(DataUtil.safeToString(obj[11]));
//                    dto.setRepresentName(DataUtil.safeToString(obj[12]));
//                    dto.setRepresentIdNo(DataUtil.safeToString(obj[13]));
//                    dto.setRepresentTitle(DataUtil.safeToString(obj[14]));
//                    dto.setRepresentTel(DataUtil.safeToString(obj[15]));
//                    dto.setRepresentEmail(DataUtil.safeToString(obj[16]));
                    dto.setCustomerId(DataUtil.safeToLong(obj[17]));
                }
            }
        }


        if (!DataUtil.isNullOrZero(dto.getCustomerId()) && !DataUtil.isNullOrZero(dto.getCustomerAccountId())) {
            //CUSTOMER_CONTRACT
            strQuery = new StringBuilder();
            strQuery.append(" select cc.CONTRACT_ID , cc.CONTRACT_NO , cc.SIGN_DATE , cc.REPRESENT_NAME , cc.REPRESENT_ID_NO , cc.REPRESENT_TITLE ");
            strQuery.append(" , cc.REPRESENT_TEL , cc.REPRESENT_EMAIL ");
            strQuery.append(" from CUSTOMER_CONTRACT cc  ");
            strQuery.append(" where cc.CUSTOMER_ID = :customerId and cc.CUSTOMER_ACCOUNT_ID = :customerAccountId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("customerId", dto.getCustomerId());
            query.setParameter("customerAccountId", dto.getCustomerAccountId());

            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] obj : queryResult) {
                    dto.setContractId(DataUtil.safeToLong(obj[0]));
                    dto.setContractNo(DataUtil.safeToString(obj[1]));
                    dto.setSignDate(DateUtil.safeDate2StringByPattern(DataUtil.safeToDate(obj[2])));
                    dto.setContractRepresentName(DataUtil.safeToString(obj[3]));
                    dto.setContractRepresentIdNo(DataUtil.safeToString(obj[4]));
                    dto.setContractRepresentTitle(DataUtil.safeToString(obj[5]));
                    dto.setContractRepresentTel(DataUtil.safeToString(obj[6]));
                    dto.setContractRepresentEmail(DataUtil.safeToString(obj[7]));

                }
            }
        }

        if (!DataUtil.isNullOrZero(dto.getCustomerAccountId())) {
            //CUSTOMER_ACCOUNT
            strQuery = new StringBuilder();
            strQuery.append(" select ca.BILL_NOTIFICATION_METHOD , ca.BILL_ADDRESS , ca.BILL_NOTIFICATION_ADDRESS, ca.PAYMENT_METHOD ");
            strQuery.append(" from CUSTOMER_ACCOUNT ca ");
            strQuery.append(" where ca.ACCOUNT_ID = :customerAccountId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("customerAccountId", dto.getCustomerAccountId());

            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] obj : queryResult) {
                    dto.setBillNotificationMethod(DataUtil.safeToString(obj[0]));
                    dto.setBillAddress(DataUtil.safeToString(obj[1]));
                    dto.setBillNotificationAddress(DataUtil.safeToString(obj[2]));
                    dto.setPaymentMethod(DataUtil.safeToString(obj[3]));
                }
            }
        }

        if (!DataUtil.isNullOrZero(dto.getSubscriberId())) {
            //SUBSCRIBER
            strQuery = new StringBuilder();
            strQuery.append(" select s.PRODUCT_ID , p.PRODUCT_NAME , s.PARTNER_ID , pa.`NAME` as PARTNER_NAME , s.STAFF_CODE , s.SALES_CHANNEL_TYPE  ");
            strQuery.append(" , s.PRODUCT_OFFER_ID  , po.`NAME` , s.PROJECT_CODE , s.PROMOTION_CODE , s.PRICE_SALES , s.PRICE_MIN ");
            strQuery.append(" , s.PRICE_BASE , s.VOL_BASE , s.VOL_PROMOTION , s.ACCOUNT_NO , s.APP_ACCOUNT_NO, ");
            strQuery.append("sem.MEDICAL_NO, sem.MEDICAL_PERMISSION_NO, s.BROKERS_PARTNER_CODE, s.PRODUCT_CUST_SEGM_ID, s.PRICE_MAX ");
            strQuery.append(" from SUBSCRIBER s  ");
            strQuery.append(" left join PRODUCT p ON (p.PRODUCT_ID = s.PRODUCT_ID) ");
            strQuery.append(" left join PARTNER pa ON (pa.PARTNER_ID = s.PARTNER_ID) ");
            strQuery.append(" left join PRODUCT_OFFER po ON (po.PRODUCT_OFFER_ID = s.PRODUCT_OFFER_ID) ");
            strQuery.append(" join SUBSCRIBER_EXT_MEDICAL sem on s.SUBSCRIBER_ID = sem.SUBSCRIBER_ID ");
            strQuery.append(" where s.SUBSCRIBER_ID = :subscriberId ");

            query = em.createNativeQuery(strQuery.toString());
            query.setParameter("subscriberId", dto.getSubscriberId());

            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] obj : queryResult) {
                    dto.setProductId(DataUtil.safeToLong(obj[0]));
                    dto.setProductName(DataUtil.safeToString(obj[1]));
                    dto.setPartneId(DataUtil.safeToLong(obj[2]));
                    dto.setPartnerName(DataUtil.safeToString(obj[3]));
                    dto.setSalePerson(DataUtil.safeToString(obj[4]));
                    dto.setSaleChanel(DataUtil.safeToString(obj[5]));
                    dto.setProductOfferId(DataUtil.safeToLong(obj[6]));
                    dto.setProductOfferName(DataUtil.safeToString(obj[7]));
                    dto.setProjectCode(DataUtil.safeToString(obj[8]));
                    dto.setPromotionName(DataUtil.safeToString(obj[9]));
                    dto.setPriceSale(DataUtil.safeToLong(obj[10]));
                    dto.setPriceMin(DataUtil.safeToLong(obj[11]));
                    dto.setPriceBase(DataUtil.safeToLong(obj[12]));
                    dto.setVolBase(DataUtil.safeToLong(obj[13]));
                    dto.setVolPromotion(DataUtil.safeToLong(obj[14]));
                    dto.setAccountNo(DataUtil.safeToString(obj[15]));
                    dto.setAccountServiceNo(DataUtil.safeToString(obj[16]));
                    dto.setMedicalNo(DataUtil.safeToString(obj[17]));
                    dto.setMedicalPermissionNo(DataUtil.safeToString(obj[18]));
                    dto.setBrokersPartnerCode(DataUtil.safeToString(obj[19]));
                    dto.setProductCustSegmId(DataUtil.safeToString(obj[20]));
                    dto.setPriceMax(DataUtil.safeToLong(obj[21]));
                }
            }
        }

        return dto;
    }

    @Override
    public CustomerOrder searchById(long customerOrderId) {

        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT CUSTOMER_ORDER_ID,CUSTOMER_ID,CUSTOMER_ACCOUNT_ID,PRODUCT_ID,SUBSCRIBER_ID,PROVINCE  ");
        strQuery.append(" ,ORDER_TYPE,ORDER_ACTION_TYPE_ID,ORDER_CONTACT_NAME,ORDER_CONTACT_TEL,ORDER_CONTACT_EMAIL  ");
        strQuery.append(" ,ORDER_CONTACT_ADDRESS,ORDER_DATETIME,DUE_DATETIME,EXT_DUE_DATETIME,STATUS,ASSIGNEE_CODE  ");
        strQuery.append(" ,REPORTER_CODE,SHOP_CODE,CREATE_USER,CREATE_DATETIME,UPDATE_USER,UPDATE_DATETIME  ");
        strQuery.append(" FROM CUSTOMER_ORDER  ");
        strQuery.append(" WHERE CUSTOMER_ORDER_ID = :customerOrderId  ");

        Query query = em.createNativeQuery(strQuery.toString());
        query.setParameter("customerOrderId", customerOrderId);

        List<Object[]> queryResult = query.getResultList();
        CustomerOrder dto = new CustomerOrder();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] objects : queryResult) {

                dto.setCustomerOrderId(DataUtil.safeToLong(objects[0]));
                dto.setCustomerId(DataUtil.safeToLong(objects[1]));
                dto.setCustomerAccountId(DataUtil.safeToLong(objects[2]));
                dto.setProductId(DataUtil.safeToLong(objects[3]));
                dto.setSubscriberId(DataUtil.safeToLong(objects[4]));
                dto.setProvince(DataUtil.safeToString(objects[5]));
                dto.setOrderType(DataUtil.safeToString(objects[6]));
                dto.setOrderActionTypeId(DataUtil.safeToString(objects[7]));
                dto.setOrderContactName(DataUtil.safeToString(objects[8]));
                dto.setOrderContactTel(DataUtil.safeToString(objects[9]));
                dto.setOrderContactEmail(DataUtil.safeToString(objects[10]));
                dto.setOrderContactAddress(DataUtil.safeToString(objects[11]));
                dto.setOrderDatetime(DataUtil.safeToDate(objects[12]));
                dto.setDueDatetime(DataUtil.safeToDate(objects[13]));
                dto.setExtDueDatetime(DataUtil.safeToDate(objects[14]));
                dto.setStatus(DataUtil.safeToString(objects[15]));
                dto.setAssigneeCode(DataUtil.safeToString(objects[16]));
                dto.setReporterCode(DataUtil.safeToString(objects[17]));
                dto.setShopCode(DataUtil.safeToString(objects[18]));
                dto.setCreateUser(DataUtil.safeToString(objects[19]));
                dto.setCreateDatetime(DataUtil.safeToDate(objects[20]));
                dto.setUpdateUser(DataUtil.safeToString(objects[21]));
                dto.setUpdateDatetime(DataUtil.safeToDate(objects[22]));
            }
        }
        return dto;
    }
}
