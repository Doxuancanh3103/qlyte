package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.SubscriberRepoCustom;
import vt.qlkdtt.yte.service.sdo.SubscriberFindByIdSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class SubscriberRepoCustomImpl implements SubscriberRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="Find by id">
    @Override
    public SubscriberFindByIdSdo findById(Long subscriberId) {
        SubscriberFindByIdSdo result = new SubscriberFindByIdSdo();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("S.SUBSCRIBER_ID, ");
        sql.append("CC.CONTRACT_ID, ");
        sql.append("CC.CONTRACT_NO, ");
        sql.append("CC.SIGN_DATE, ");
        sql.append("CA.BILL_NOTIFICATION_METHOD, ");
        sql.append("CA.BILL_NOTIFICATION_ADDRESS, ");
        sql.append("CA.BILL_ADDRESS, ");
        sql.append("CC.REPRESENT_NAME, ");
        sql.append("CC.REPRESENT_ID_NO, ");
        sql.append("CC.REPRESENT_TITLE, ");
        sql.append("CC.REPRESENT_TEL, ");
        sql.append("CC.REPRESENT_EMAIL, ");
        sql.append("P.PRODUCT_ID, ");
        sql.append("P.PRODUCT_NAME, ");
        sql.append("PN.PARTNER_ID, ");
        sql.append("PN.NAME PARTNER_NAME, ");
        sql.append("S.STAFF_CODE, ");
        sql.append("S.SALES_CHANNEL_TYPE, ");
        sql.append("(SELECT GLV.NAME ");
        sql.append("FROM GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE S.SALES_CHANNEL_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'SALE_CHANEL' ");
        sql.append(") SALES_CHANNEL_TYPE_NAME, ");
        sql.append("PO.PRODUCT_OFFER_ID, ");
        sql.append("PO.NAME PRODUCT_OFFER_NAME, ");
        sql.append("S.PROJECT_CODE, ");
        sql.append("S.PROMOTION_CODE, ");
        sql.append("S.PRICE_SALES, ");
        sql.append("S.PRICE_MIN, ");
        sql.append("S.PRICE_MAX, ");
        sql.append("S.PRICE_BASE, ");
        sql.append("S.VOL_BASE, ");
        sql.append("S.VOL_PROMOTION, ");
        sql.append("S.ACCOUNT_NO, ");
        sql.append("S.APP_ACCOUNT_NO, ");
        sql.append("S.PRODUCT_CUST_SEGM_ID, ");
        sql.append("CA.PAYMENT_METHOD, ");
        sql.append("SEM.MEDICAL_NO, ");
        sql.append("S.BROKERS_PARTNER_CODE, ");
        sql.append("SEM.MEDICAL_PERMISSION_NO ");
        sql.append("FROM SUBSCRIBER S ");
        sql.append("LEFT JOIN CUSTOMER_CONTRACT CC ON S.CUSTOMER_CONTRACT_ID = CC.CONTRACT_ID ");
        sql.append("LEFT JOIN PRODUCT P ON S.PRODUCT_ID = P.PRODUCT_ID ");
        sql.append("LEFT JOIN PARTNER PN ON S.PARTNER_ID = PN.PARTNER_ID ");
        sql.append("LEFT JOIN PRODUCT_OFFER PO ON S.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID ");
        sql.append("LEFT JOIN CUSTOMER_ACCOUNT CA ON CC.CUSTOMER_ACCOUNT_ID = CA.ACCOUNT_ID ");
        sql.append("LEFT JOIN SUBSCRIBER_EXT_MEDICAL SEM ON S.SUBSCRIBER_ID = SEM.SUBSCRIBER_ID ");
        sql.append("WHERE S.SUBSCRIBER_ID = :subscriberId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("subscriberId", subscriberId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
            break;
        }

        return result;
    }
    //</editor-fold>

    @Override
    public boolean changeSalesManCode(String salesManCode, Long subscriberId) {
        StringBuilder sql = new StringBuilder("UPDATE SUBSCRIBER SET STAFF_CODE = :salesManCode WHERE SUBSCRIBER_ID = :subscriberId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("salesManCode", salesManCode);
        query.setParameter("subscriberId", subscriberId);

        Object queryResult = query.executeUpdate();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean updateBrokersPartnerCode(String brokersPartnerCode, Long subscriberId) {
        StringBuilder sql = new StringBuilder("UPDATE SUBSCRIBER SET BROKERS_PARTNER_CODE = :brokersPartnerCode WHERE SUBSCRIBER_ID = :subscriberId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("brokersPartnerCode", brokersPartnerCode);
        query.setParameter("subscriberId", subscriberId);

        Object queryResult = query.executeUpdate();

        return DataUtil.safeToLong(queryResult) > 0;
    }
}
