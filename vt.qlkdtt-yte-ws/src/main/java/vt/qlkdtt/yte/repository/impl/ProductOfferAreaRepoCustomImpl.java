package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.ProductOfferAreaRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Transactional
public class ProductOfferAreaRepoCustomImpl implements ProductOfferAreaRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public void changeStatusByProductOffer(String status, long productOfferId) {
        StringBuilder sql = new StringBuilder("UPDATE PRODUCT_OFFER_AREA SET STATUS = :status WHERE PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productOfferId", productOfferId);

        query.executeUpdate();
    }

    @Override
    public boolean isExist(long productOfferAreaId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PRODUCT_OFFER_AREA WHERE PRODUCT_OFFER_AREA_ID = :productOfferAreaId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferAreaId", productOfferAreaId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isNationWide(Long productOfferId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PRODUCT_OFFER_AREA WHERE (AREA_CODE IS NULL OR AREA_CODE = '') AND STATUS <> 2 AND PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferId", productOfferId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
}
