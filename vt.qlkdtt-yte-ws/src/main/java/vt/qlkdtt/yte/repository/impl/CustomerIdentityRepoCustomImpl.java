package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.CustomerIdentityRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;

@Transactional
public class CustomerIdentityRepoCustomImpl implements CustomerIdentityRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public boolean isExist(String customerIdentityNo) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_IDENTITY WHERE UPPER(ID_NO) = :customerIdentityNo");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("customerIdentityNo", customerIdentityNo.toUpperCase());

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public void updateIdentityNo(String identityNo, Long customerId) {
        StringBuilder sql = new StringBuilder("UPDATE CUSTOMER_IDENTITY SET ID_NO = :identityNo, UPDATE_USER = :updateUser, UPDATE_DATETIME = :updateDatetime where CUSTOMER_ID = :customerId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("identityNo", identityNo);
        query.setParameter("customerId", customerId);
        query.setParameter("updateUser", Const.ADMIN);
        query.setParameter("updateDatetime", new Date());

        query.executeUpdate();
    }
}
