package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.CustomerOrderConfig;
import vt.qlkdtt.yte.repository.CustomerOrderConfigRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CustomerOrderConfigRepoCustomImpl implements CustomerOrderConfigRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public CustomerOrderConfig search(String orderType, String actionTypeId, Long productId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM CUSTOMER_ORDER_CONFIG WHERE ORDER_TYPE_ID = :orderType AND ACTION_TYPE_ID = :actionTypeId AND PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("orderType", orderType);
        query.setParameter("actionTypeId", actionTypeId);
        query.setParameter("productId", productId);

        List<Object[]> queryResult = query.getResultList();

        CustomerOrderConfig result = new CustomerOrderConfig();

        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
            break;
        }

        return result;
    }
}
