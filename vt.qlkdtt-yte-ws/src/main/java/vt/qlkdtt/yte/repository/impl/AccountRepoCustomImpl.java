package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.CustomerAccount;
import vt.qlkdtt.yte.repository.AccountRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class AccountRepoCustomImpl implements AccountRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="isHadAccount">
    @Override
    public boolean isHadAccount(Long customerId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_ACCOUNT WHERE CUSTOMER_ID = :customerId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerId", customerId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>

//    @Override
//    public CustomerAccount findByCustomerId(Long customerId) {
//        CustomerAccount result = new CustomerAccount();
//
//        StringBuilder sql = new StringBuilder("SELECT * FROM CUSTOMER_ACCOUNT WHERE CUSTOMER_ID = :customerId");
//
//        Query query = em.createNativeQuery(sql.toString());
//        query.setParameter("customerId", customerId);
//
//        List<Object[]> queryResult = query.getResultList();
//
//        for (Object[] item : queryResult) {
//            result = DataUtil.convertObjectsToClass(item, result);
//        }
//
//        return result;
//    }
}
