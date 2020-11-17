package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.CustomerExtMedicalRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
@Transactional
public class CustomerExtMedicalRepoCustomImpl implements CustomerExtMedicalRepoCustom {
    @Autowired
    EntityManager em;

    @Override
    public boolean isExist(String medicalPermissionNo) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM CUSTOMER_EXT_MEDICAL WHERE UPPER(MEDICAL_PERMISSION_NO) = :medicalPermissionNo");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("medicalPermissionNo", medicalPermissionNo.toUpperCase());

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public void updateMedicalPermissionNo(String medicalPermissionNo, Long customerId) {
        StringBuilder sql = new StringBuilder("UPDATE CUSTOMER_EXT_MEDICAL SET MEDICAL_PERMISSION_NO = :medicalPermissionNo where CUSTOMER_ID = :customerId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("medicalPermissionNo", medicalPermissionNo);
        query.setParameter("customerId", customerId);

        query.executeUpdate();
    }
}
