package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.repository.ProductSaleExpRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Transactional
public class ProductSaleExpRepoCustomImpl implements ProductSaleExpRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public void changeStatusByProduct(String status, Long productId) {
        String sql = "UPDATE PRODUCT_SALE_EXPENSES SET STATUS = :status where PRODUCT_ID = :productId";

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productId", productId);

        query.executeUpdate();
    }
}
