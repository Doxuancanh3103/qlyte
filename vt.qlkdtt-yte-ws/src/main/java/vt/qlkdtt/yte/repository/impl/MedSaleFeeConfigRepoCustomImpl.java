package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;
import vt.qlkdtt.yte.repository.MedSaleFeeConfigRepoCustom;
import vt.qlkdtt.yte.service.sdi.MedSaleFeeConfigSearchSdi;
import vt.qlkdtt.yte.service.sdo.MedSaleFeeConfigSearchSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedSaleFeeConfigRepoCustomImpl implements MedSaleFeeConfigRepoCustom {
    @Autowired
    EntityManager em;
    @Override
    public List<MedSaleFeeConfig> search(String feeConfigCode, String feeConfigName, Long telecomServiceId,
                                   String productOfferCode, String staffCode, Long channelTypeId,
                                   String provinceCode, String status, String fromDate, String toDate) {
        Map<String,Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT FEE_CONFIG_ID,FEE_CONFIG_CODE,FEE_CONFIG_NAME,FEE_CONFIG_DESCRIPTION,TELECOM_SERVICE_ID," +
                   " PRODUCT_OFFER_CODE,STAFF_CODE,CHANNEL_TYPE_ID,PROVINCE_CODE,SALE_LEVEL,FEE_VALUE,FEE_TYPE,STA_DATE," +
                   " END_DATE,STATUS,CREATE_USER,CREATE_DATE FROM med_sale_fee_config");
        sql.append(" WHERE 1 = 1");
        sql.append(" AND TELECOM_SERVICE_ID = :telecomServiceId");
        sql.append(" AND CHANNEL_TYPE_ID = :channelTypeId");
        sql.append(" AND STATUS = :status");
        sql.append(" AND DATE(STA_DATE) >= :fromDate");
        sql.append(" AND DATE(END_DATE) <= :toDate");

        map.put("telecomServiceId",telecomServiceId);
        map.put("channelTypeId",channelTypeId);
        map.put("status",status);
        map.put("fromDate",fromDate);
        map.put("toDate",toDate);

        if (!DataUtil.isNullOrEmpty(feeConfigCode)){
            sql.append(" AND FEE_CONFIG_CODE LIKE :feeConfigCode");
            map.put("feeConfigCode",feeConfigCode);
        }

        if (!DataUtil.isNullOrEmpty(feeConfigName)){
            sql.append(" AND FEE_CONFIG_NAME LIKE :feeConfigName");
            map.put("feeConfigName",feeConfigName);
        }

        if (!DataUtil.isNullOrEmpty(productOfferCode)){
            sql.append(" AND PRODUCT_OFFER_CODE =:productOfferCode");
            map.put("productOfferCode",productOfferCode);
        }

        if (!DataUtil.isNullOrEmpty(staffCode)){
            sql.append(" AND STAFF_CODE =:staffCode");
            map.put("staffCode",staffCode);
        }

        if (!DataUtil.isNullOrEmpty(provinceCode)){
            sql.append(" AND PROVINCE_CODE =:provinceCode");
            map.put("provinceCode",provinceCode);
        }

        Query query = em.createNativeQuery(sql.toString(),MedSaleFeeConfig.class);

        for(Map.Entry<String,Object> entry: map.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }

        return query.getResultList();
    }

    @Override
    public boolean isExistFeeForInsert(Long telecomServiceId, String productOfferCode, String channelTypeId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) FROM med_sale_fee_config");
        sql.append(" WHERE 1 = 1");
        sql.append(" AND STATUS = '1'");
        sql.append(" AND TELECOM_SERVICE_ID =:telecomServiceId");
        sql.append(" AND PRODUCT_OFFER_CODE =:productOfferCode");
        sql.append(" AND CHANNEL_TYPE_ID =:channelTypeId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("telecomServiceId",telecomServiceId);
        query.setParameter("productOfferCode",productOfferCode);
        query.setParameter("channelTypeId",channelTypeId);
        return DataUtil.safeToLong(query.getSingleResult()) > 0;
    }

    @Override
    public boolean isExistFeeForUpdate(Long feeConfigId, Long telecomServiceId, String productOfferCode, String channelTypeId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) FROM med_sale_fee_config");
        sql.append(" WHERE 1 = 1");
        sql.append(" AND STATUS = '1'");
        sql.append(" AND TELECOM_SERVICE_ID =:telecomServiceId");
        sql.append(" AND PRODUCT_OFFER_CODE =:productOfferCode");
        sql.append(" AND CHANNEL_TYPE_ID =:channelTypeId");
        sql.append(" AND FEE_CONFIG_ID!=:feeConfigId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("telecomServiceId",telecomServiceId);
        query.setParameter("productOfferCode",productOfferCode);
        query.setParameter("channelTypeId",channelTypeId);
        query.setParameter("feeConfigId",feeConfigId);
        return DataUtil.safeToLong(query.getSingleResult()) > 0;
    }
    @Override
    public Page<MedSaleFeeConfigSearchSdo> searchMedSaleFeeConfigSdo(MedSaleFeeConfigSearchSdi medSaleFeeConfigSearchSdi, Pageable page) {
        StringBuilder sql = new StringBuilder();
        List<MedSaleFeeConfigSearchSdo> result = new ArrayList<>();
        Map<String,Object> parameterMap = new HashMap<>();
        sql.append(" SELECT FEE_CONFIG_CODE, FEE_CONFIG_NAME,FEE_CONFIG_DESCRIPTION,TELECOM_SERVICE_ID," +
                   "        PRODUCT_OFFER_CODE,STAFF_CODE,CHANNEL_TYPE_ID,PROVINCE_CODE,SALE_LEVEL,FEE_VALUE," +
                   "        FEE_TYPE,STA_DATE,END_DATE,STATUS,CREATE_USER,UPDATE_USER,UPDATE_DATE,CREATE_DATE " +
                   " FROM med_sale_fee_config ");
        sql.append(" WHERE 1 = 1");
        if (!DataUtil.isNullOrZero(medSaleFeeConfigSearchSdi.getTelecomServiceId())){
            sql.append(" AND TELECOM_SERVICE_ID =:telecomServiceId");
            parameterMap.put("telecomServiceId",medSaleFeeConfigSearchSdi.getTelecomServiceId());
        }
        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getChannelTypeId())){
            sql.append(" AND CHANNEL_TYPE_ID =:channelTypeId");
            parameterMap.put("channelTypeId",medSaleFeeConfigSearchSdi.getChannelTypeId());
        }
        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getProductOfferCode())){
            sql.append(" AND PRODUCT_OFFER_CODE =:productOfferCode");
            parameterMap.put("productOfferCode", medSaleFeeConfigSearchSdi.getProductOfferCode());
        }

        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getCreateUser())){
            sql.append(" AND CREATE_USER =:createUser");
            parameterMap.put("createUser", medSaleFeeConfigSearchSdi.getCreateUser());
        }

        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getStatus())){
            sql.append(" AND STATUS =:status");
            parameterMap.put("status", medSaleFeeConfigSearchSdi.getStatus());
        }

        sql.append(" AND ((END_DATE is null and STR_TO_DATE(:toDate,'%Y-%m-%d') > STA_DATE)");
        sql.append(" OR (END_DATE is not null and STR_TO_DATE(:fromDate,'%Y-%m-%d') BETWEEN STA_DATE and END_DATE)");
        sql.append(" OR (END_DATE is not null and STR_TO_DATE(:toDate,'%Y-%m-%d') BETWEEN STA_DATE and END_DATE)");
        sql.append(" OR (END_DATE is not null and STR_TO_DATE(:fromDate,'%Y-%m-%d') < STA_DATE and STR_TO_DATE(:toDate,'%Y-%m-%d') > END_DATE))");
        sql.append("ORDER BY CREATE_DATE DESC, UPDATE_DATE DESC");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("fromDate",medSaleFeeConfigSearchSdi.getFromDate());
        query.setParameter("toDate",medSaleFeeConfigSearchSdi.getToDate());
        query.setFirstResult((page.getPageNumber()) * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }

        List<Object[]> queryResult = query.getResultList();
        MedSaleFeeConfigSearchSdo dto;
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] objects : queryResult) {
                dto = new MedSaleFeeConfigSearchSdo();
                dto = DataUtil.convertObjectsToClass(objects, dto);

                result.add(dto);
            }
        }

        Long countAll = countAllItem(medSaleFeeConfigSearchSdi);
        return new PageImpl<>(result, page, countAll);
    }

    private Long countAllItem(MedSaleFeeConfigSearchSdi medSaleFeeConfigSearchSdi){
        StringBuilder sql = new StringBuilder();
        List<MedSaleFeeConfigSearchSdo> result = new ArrayList<>();
        Map<String,Object> parameterMap = new HashMap<>();
        sql.append(" SELECT COUNT(*)"+
                " FROM med_sale_fee_config ");
        sql.append(" WHERE 1 = 1");
        if (!DataUtil.isNullOrZero(medSaleFeeConfigSearchSdi.getTelecomServiceId())){
            sql.append(" AND TELECOM_SERVICE_ID =:telecomServiceId");
            parameterMap.put("telecomServiceId",medSaleFeeConfigSearchSdi.getTelecomServiceId());
        }
        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getChannelTypeId())){
            sql.append(" AND CHANNEL_TYPE_ID =:channelTypeId");
            parameterMap.put("channelTypeId",medSaleFeeConfigSearchSdi.getChannelTypeId());
        }
        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getProductOfferCode())){
            sql.append(" AND PRODUCT_OFFER_CODE =:productOfferCode");
            parameterMap.put("productOfferCode", medSaleFeeConfigSearchSdi.getProductOfferCode());
        }

        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getCreateUser())){
            sql.append(" AND CREATE_USER =:createUser");
            parameterMap.put("createUser", medSaleFeeConfigSearchSdi.getCreateUser());
        }

        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getStatus())){
            sql.append(" AND STATUS =:status");
            parameterMap.put("status", medSaleFeeConfigSearchSdi.getStatus());
        }

//        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getFromDate())){
//            sql.append(" AND STA_DATE =:fromDate");
//            parameterMap.put("fromDate",medSaleFeeConfigSearchSdi.getFromDate());
//        }
//
//        if (!DataUtil.isStringNullOrEmpty(medSaleFeeConfigSearchSdi.getToDate())){
//            sql.append(" AND END_DATE =:toDate");
//            parameterMap.put("toDate",medSaleFeeConfigSearchSdi.getToDate());
//        }

        sql.append(" AND (" +
                " case" +
                " when ISNULL(END_DATE) = 1 then " +
                " (" +
                " case " +
                " when ISNULL(:toDate) = 1 then :fromDate > STA_DATE" +
                " else :toDate > STA_DATE" +
                " end" +
                " )" +
                " else" +
                " (" +
                " case" +
                " when ISNULL(:toDate) = 1 then :fromDate > STA_DATE and :fromDate < END_DATE" +
                " when ISNULL(:fromDate) = 1 then :toDate > STA_DATE and :toDate < END_DATE" +
                " when ISNULL(:toDate) = 1 and ISNULL(:fromDate) = 1 then FALSE" +
                " else (:toDate > STA_DATE and :toDate < END_DATE) or (:fromDate > STA_DATE AND :fromDate < END_DATE) or(:toDate <=STA_DATE and :fromDate >= END_DATE)" +
                " end " +
                " )" +
                " end" +
                " )");

        sql.append("ORDER BY CREATE_DATE DESC, UPDATE_DATE DESC");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("fromDate",medSaleFeeConfigSearchSdi.getFromDate());
        query.setParameter("toDate",medSaleFeeConfigSearchSdi.getToDate());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        Object queryResult = query.getSingleResult();
        System.out.println(DataUtil.safeToLong(queryResult));
        return DataUtil.safeToLong(queryResult);
    }
}
