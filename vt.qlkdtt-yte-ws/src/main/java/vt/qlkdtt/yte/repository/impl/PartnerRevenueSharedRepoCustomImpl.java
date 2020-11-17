package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.PartnerRevenueSharedRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Transactional
public class PartnerRevenueSharedRepoCustomImpl implements PartnerRevenueSharedRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public void changeStatusByProductId(String status, long productId) {
        StringBuilder sql = new StringBuilder("UPDATE PARTNER_REVENUE_SHARED SET STATUS = :status WHERE PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productId", productId);

        query.executeUpdate();
    }

    @Override
    public void changeStatusByProductOfferId(String status, long productOfferId) {
        StringBuilder sql = new StringBuilder("UPDATE PARTNER_REVENUE_SHARED SET STATUS = :status WHERE PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productOfferId", productOfferId);

        query.executeUpdate();
    }

    @Override
    public boolean isExist(long prsId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PARTNER_REVENUE_SHARED WHERE PARTNER_REVENUE_SHARED_ID = :prsId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("prsId", prsId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
}
