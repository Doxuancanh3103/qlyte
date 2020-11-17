package vt.qlkdtt.yte.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Partner;
import vt.qlkdtt.yte.dto.RevenueSharedDTO;
import vt.qlkdtt.yte.service.sdi.PartnerSearchSdi;
import vt.qlkdtt.yte.service.sdo.PartnerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.PartnerSearchSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;


@Repository
public class PartnerRepoCustomImpl implements PartnerRepoCustom {
    @Autowired
    EntityManager em;

    //<editor-fold desc="Search partner full">
    @Override
    public List<Partner> searchPartnerFull(Partner partner) {
        List<Partner> result = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();
        strQuery.append(" SELECT");
        strQuery.append(" PARTNER_ID,CODE,NAME,TIN,PARTNER_TYPE,AREA_CODE,PROVINCE");
        strQuery.append(" ,DISTRICT,PRECINCT,STREET_BLOCK,STREET_NAME,HOME");
        strQuery.append(" ,ADDRESS,TEL,FAX,EMAIL,BANK_CODE,BRANCH_BANK_CODE");
        strQuery.append(" ,DESCRIPTION,STATUS,CREATE_USER,CREATE_DATETIME,UPDATE_USER");
        strQuery.append(" ,UPDATE_DATETIME,REPRESENT_NAME,REPRESENT_ID_NO,REPRESENT_ID_TYPE");
        strQuery.append(" ,REPRESENT_ISSUE_PLACE,REPRESENT_ISSUE_DATE,REPRESENT_EXPIRE_DATE");
        strQuery.append(" ,REPRESENT_TITLE,REPRESENT_TEL,REPRESENT_EMAIL");
        strQuery.append(" FROM ");
        strQuery.append(" PARTNER p");
        strQuery.append(" WHERE 1=1 ");

        if (!DataUtil.isNullOrEmpty(partner.getStatus())) {
            strQuery.append(" AND STATUS = :status");
            parameterMap.put("status", partner.getStatus());
        }

        if (!DataUtil.isNullOrZero(partner.getPartnerId())) {
            strQuery.append(" AND PARTNER_ID = :partnerId");
            parameterMap.put("partnerId", partner.getPartnerId());
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getPartnerCode())) {
            strQuery.append(" AND UPPER(CODE) = :partnerCode");
            parameterMap.put("partnerCode", partner.getPartnerCode().toUpperCase());
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getName())) {
            strQuery.append(" AND UPPER(NAME) LIKE :name");
            parameterMap.put("name", "%" + partner.getName().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getTin())) {
            strQuery.append(" AND UPPER(TIN) LIKE :tin");
            parameterMap.put("tin", "%" + partner.getTin().toUpperCase() + "%");
        }

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        List<Object[]> queryResult = query.getResultList();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] objects : queryResult) {
                Partner dto = new Partner();

                dto.setPartnerId(DataUtil.safeToLong(objects[0]));
                dto.setPartnerCode(DataUtil.safeToString(objects[1]));
                dto.setName(DataUtil.safeToString(objects[2]));
                dto.setTin(DataUtil.safeToString(objects[3]));
                dto.setPartnerType(DataUtil.safeToString(objects[4]));
                dto.setAreaCode(DataUtil.safeToString(objects[5]));
                dto.setProvince(DataUtil.safeToString(objects[6]));
                dto.setDistrict(DataUtil.safeToString(objects[7]));
                dto.setPrecinct(DataUtil.safeToString(objects[8]));
                dto.setStreetBlock(DataUtil.safeToString(objects[9]));
                dto.setStreet(DataUtil.safeToString(objects[10]));
                dto.setHome(DataUtil.safeToString(objects[11]));
                dto.setAddress(DataUtil.safeToString(objects[12]));
                dto.setTel(DataUtil.safeToString(objects[13]));
                dto.setFax(DataUtil.safeToString(objects[14]));
                dto.setEmail(DataUtil.safeToString(objects[15]));
                dto.setBankCode(DataUtil.safeToString(objects[16]));
                dto.setBranchBankCode(DataUtil.safeToString(objects[17]));
                dto.setDescription(DataUtil.safeToString(objects[18]));
                dto.setStatus(DataUtil.safeToString(objects[19]));
                dto.setCreateUser(DataUtil.safeToString(objects[20]));
                dto.setCreateDate(DataUtil.safeToDate(objects[21]));
                dto.setLastUpDateUser(DataUtil.safeToString(objects[22]));
                dto.setLastUpDateDate(DataUtil.safeToDate(objects[23]));
                dto.setRepresentativeName(DataUtil.safeToString(objects[24]));
                dto.setRepresentativeIdNo(DataUtil.safeToString(objects[25]));
                dto.setRepresentativeIdType(DataUtil.safeToString(objects[26]));
                dto.setRepresentativeIssuePlace(DataUtil.safeToString(objects[27]));
                dto.setRepresentativeIssueDate(DataUtil.safeToDate(objects[28]));
                dto.setRepresentativeExpireDate(DataUtil.safeToDate(objects[29]));
                dto.setRepresentativeTitle(DataUtil.safeToString(objects[30]));
                dto.setRepresentativeTel(DataUtil.safeToString(objects[31]));
                dto.setRepresentativeEmail(DataUtil.safeToString(objects[32]));

                result.add(dto);
            }
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="is exist">
    @Override
    public boolean isExist(String partnerCode, long partnerId) {
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();
        strQuery.append(" SELECT");
        strQuery.append(" COUNT(1)");
        strQuery.append(" FROM ");
        strQuery.append(" PARTNER p");
        strQuery.append(" WHERE 1=1 AND STATUS = '1' ");
        strQuery.append(" AND UPPER(CODE) = :partnerCode");
        parameterMap.put("partnerCode", partnerCode.toUpperCase());

        if (!DataUtil.isNullOrZero(partnerId)) {
            strQuery.append(" AND PARTNER_ID != :partnerId");
            parameterMap.put("partnerId", partnerId);
        }

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.longValue() > 0L;
    }

    @Override
    public boolean isExist(Long partnerId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PARTNER WHERE PARTNER_ID = :partnerId");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("partnerId", partnerId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExist(String partnerCode) {
        String sql = "SELECT COUNT(*) FROM PARTNER WHERE CODE = :partnerCode";

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("partnerCode", partnerCode);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }


    //</editor-fold>

    //<editor-fold desc="Is exist tin">
    @Override
    public boolean isExistTin(String tin) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PARTNER WHERE UPPER(TIN) = :tin");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("tin", tin.toUpperCase());

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExistTin(String tin, Long partnerId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PARTNER WHERE UPPER(TIN) = :tin AND PARTNER_ID != :partnerId");
        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("tin", tin.toUpperCase());
        query.setParameter("partnerId", partnerId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @Override
    public PartnerFindByIdSdo findById(long partnerId) {
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();
        strQuery.append(" SELECT");
        strQuery.append(" PARTNER_ID,	CODE,	NAME,	TIN,	PROVINCE,	DISTRICT,	PRECINCT,	ADDRESS,	TEL,	EMAIL, ");
        strQuery.append(" REPRESENT_NAME, REPRESENT_TITLE,	REPRESENT_ID_NO, REPRESENT_TEL,	REPRESENT_EMAIL , FAX , STATUS , REPRESENT_ID_TYPE ");
        strQuery.append(" FROM ");
        strQuery.append(" PARTNER p");
        strQuery.append(" WHERE 1=1 AND PARTNER_ID = :partnerId ");

        parameterMap.put("partnerId", partnerId);

        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        List<Object[]> queryResult = query.getResultList();
        PartnerFindByIdSdo dto = new PartnerFindByIdSdo();
        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] objects : queryResult) {
                dto.setPartnerId(DataUtil.safeToLong(objects[0]));
                dto.setPartnerCode(DataUtil.safeToString(objects[1]));
                dto.setName(DataUtil.safeToString(objects[2]));
                dto.setTin(DataUtil.safeToString(objects[3]));
                dto.setProvince(DataUtil.safeToString(objects[4]));
                dto.setDistrict(DataUtil.safeToString(objects[5]));
                dto.setPrecinct(DataUtil.safeToString(objects[6]));
                dto.setAddress(DataUtil.safeToString(objects[7]));
                dto.setTel(DataUtil.safeToString(objects[8]));
                dto.setEmail(DataUtil.safeToString(objects[9]));
                dto.setRepresentName(DataUtil.safeToString(objects[10]));
                dto.setRepresentTitle(DataUtil.safeToString(objects[11]));
                dto.setRepresentIdNo(DataUtil.safeToString(objects[12]));
                dto.setRepresentTel(DataUtil.safeToString(objects[13]));
                dto.setRepresentEmail(DataUtil.safeToString(objects[14]));
                dto.setFax(DataUtil.safeToString(objects[15]));
                dto.setStatus(DataUtil.safeToString(objects[16]));
                dto.setRepresentIdType(DataUtil.safeToString(objects[17]));
            }
        }

        if (!DataUtil.isNullOrZero(dto.getPartnerId())) {
            List<RevenueSharedDTO> listRevenueSharedDTOS = new ArrayList<>();
            strQuery = new StringBuilder();
            parameterMap = new HashMap<>();
            strQuery.append(" SELECT ");
            strQuery.append(" prs.PARTNER_REVENUE_SHARED_ID , pr.PRODUCT_CODE , pr.PRODUCT_NAME , prs.PARTNER_PERCENT , prs.SHARE_TYPE");
            strQuery.append(" ,prs.STA_DATE , prs.END_DATE , prs.STATUS ");
            strQuery.append(" FROM ");
            strQuery.append(" PARTNER_REVENUE_SHARED  prs , PRODUCT pr");
            strQuery.append(" WHERE ");
            strQuery.append(" prs.PARTNER_ID = :partnerId AND prs.STATUS = '1'");
            strQuery.append(" AND prs.PRODUCT_ID = pr.PRODUCT_ID ");
//            strQuery.append(" AND prs.PRODUCT_ID = pr.PRODUCT_ID AND pr.PRODUCT_TYPE <> 10 ");

            parameterMap.put("partnerId", partnerId);

            query = em.createNativeQuery(strQuery.toString());
            for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
                query.setParameter(p.getKey(), p.getValue());
            }
            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object obj : queryResult) {
                    Object[] objects = (Object[]) obj;
                    RevenueSharedDTO sharedDTO = new RevenueSharedDTO();
                    sharedDTO.setPartnerRevenueSharedId(DataUtil.safeToLong(objects[0]));
                    sharedDTO.setProductCode(DataUtil.safeToString(objects[1]));
                    sharedDTO.setProductName(DataUtil.safeToString(objects[2]));
                    sharedDTO.setPartnerPercent(DataUtil.safeToLong(objects[3]));
                    sharedDTO.setShareType(DataUtil.safeToString(objects[4]));
                    sharedDTO.setStaDate(DataUtil.safeToString(objects[5]));
                    sharedDTO.setEndDate(DataUtil.safeToString(objects[6]));
                    sharedDTO.setStatus(DataUtil.safeToString(objects[7]));

                    listRevenueSharedDTOS.add(sharedDTO);
                }
            }
            dto.setLstRevenueShared(listRevenueSharedDTOS);
        }

        if (!DataUtil.isNullOrZero(dto.getPartnerId())) {
            List<RevenueSharedDTO> listRevenueSharedDTOSpecial = new ArrayList<>();
            strQuery = new StringBuilder();
            parameterMap = new HashMap<>();
            strQuery.append(" SELECT ");
            strQuery.append(" prs.PARTNER_REVENUE_SHARED_ID , pr.PRODUCT_CODE , pr.PRODUCT_NAME , prs.PARTNER_PERCENT , prs.SHARE_TYPE");
            strQuery.append(" ,prs.STA_DATE , prs.END_DATE , prs.STATUS ");
            strQuery.append(" FROM ");
            strQuery.append(" PARTNER_REVENUE_SHARED  prs, PRODUCT_OFFER po , PRODUCT pr");
            strQuery.append(" WHERE ");
            strQuery.append(" prs.PARTNER_ID = :partnerId AND prs.STATUS = '1'");
            strQuery.append(" AND prs.PRODUCT_OFFER_ID = po.PRODUCT_OFFER_ID ");
            strQuery.append(" AND po.PRODUCT_ID = pr.PRODUCT_ID ");

            parameterMap.put("partnerId", partnerId);

            query = em.createNativeQuery(strQuery.toString());
            for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
                query.setParameter(p.getKey(), p.getValue());
            }
            queryResult = query.getResultList();

            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object obj : queryResult) {
                    Object[] objects = (Object[]) obj;
                    RevenueSharedDTO sharedDTO = new RevenueSharedDTO();
                    sharedDTO.setPartnerRevenueSharedId(DataUtil.safeToLong(objects[0]));
                    sharedDTO.setProductCode(DataUtil.safeToString(objects[1]));
                    sharedDTO.setProductName(DataUtil.safeToString(objects[2]));
                    sharedDTO.setPartnerPercent(DataUtil.safeToLong(objects[3]));
                    sharedDTO.setShareType(DataUtil.safeToString(objects[4]));
                    sharedDTO.setStaDate(DataUtil.safeToString(objects[5]));
                    sharedDTO.setEndDate(DataUtil.safeToString(objects[6]));
                    sharedDTO.setStatus(DataUtil.safeToString(objects[7]));

                    listRevenueSharedDTOSpecial.add(sharedDTO);
                }
            }
            dto.setLstRevenueSharedSpecial(listRevenueSharedDTOSpecial);
        }

        return dto;
    }
    //</editor-fold>

    //<editor-fold desc="Count search partner page">
    public long countSearchPartnerPage(PartnerSearchSdi partner) {
        StringBuilder strQuery = new StringBuilder();
        Map<String, Object> parameterMap = new HashMap<>();

        strQuery.append(" SELECT COUNT(1) ");
        strQuery.append(" FROM ");
        strQuery.append(" PARTNER p");
        strQuery.append(" WHERE 1=1 ");

        if (!DataUtil.isStringNullOrEmpty(partner.getCode())) {
            strQuery.append(" AND UPPER(CODE) LIKE :code");
            parameterMap.put("code", "%" + partner.getCode().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getName())) {
            strQuery.append(" AND UPPER(NAME) LIKE :name");
            parameterMap.put("name", "%" + partner.getName().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getIdNo())) {
            strQuery.append(" AND (UPPER(REPRESENT_ID_NO) LIKE :idNo OR UPPER(TIN) LIKE :idNo)");
            parameterMap.put("idNo", "%" + partner.getIdNo().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getIdType())) {
            strQuery.append(" AND REPRESENT_ID_TYPE = :idType");
            parameterMap.put("idType", partner.getIdType());
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getStatus())) {
            strQuery.append(" AND STATUS = :status");
            parameterMap.put("status", partner.getStatus());
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getFromDate())) {
            strQuery.append(" AND CREATE_DATETIME >= :fromDate");
            parameterMap.put("fromDate", DateUtil.string2Date(partner.getFromDate(), Const.DATE_FORMAT));
        }

        if (!DataUtil.isStringNullOrEmpty(partner.getToDate())) {
            strQuery.append(" AND CREATE_DATETIME <= :toDate");
            parameterMap.put("toDate", DateUtil.string2Date(partner.getToDate(), Const.DATE_FORMAT));
        }
        Query query = em.createNativeQuery(strQuery.toString());
        for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
            query.setParameter(p.getKey(), p.getValue());
        }
        Object queryResult = query.getSingleResult();
        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Search partner page">
    @Override
    public Page<PartnerSearchSdo> searchPartnerPage(PartnerSearchSdi partner, Pageable page) {
        long count = countSearchPartnerPage(partner);
        List<PartnerSearchSdo> result = new ArrayList<>();
        if (count > 0) {
            StringBuilder strQuery = new StringBuilder();
            Map<String, Object> parameterMap = new HashMap<>();
            strQuery.append(" SELECT");
            strQuery.append(" PARTNER_ID, CODE , NAME, TIN , REPRESENT_ID_NO , REPRESENT_ID_TYPE , REPRESENT_NAME , STATUS ");
            strQuery.append(" 	,( SELECT");
            strQuery.append(" 		GROUP_CONCAT(");
            strQuery.append(" 			DISTINCT CONVERT(pr.PRODUCT_CODE, CHAR(100))");
            strQuery.append(" 			ORDER BY");
            strQuery.append(" 				pr.PRODUCT_CODE SEPARATOR ','");
            strQuery.append(" 		)");
            strQuery.append(" 	FROM");
            strQuery.append(" 		PRODUCT pr,");
            strQuery.append(" 		PARTNER_REVENUE_SHARED prs");
            strQuery.append(" 	WHERE");
            strQuery.append(" 		p.PARTNER_ID = prs.PARTNER_ID		AND prs. STATUS = '1'");
            strQuery.append(" 	AND prs.PRODUCT_ID = pr.PRODUCT_ID		AND pr. STATUS = '1'");
            strQuery.append(" ) LIST_PRODUCT_CODE");
            strQuery.append(" FROM ");
            strQuery.append(" PARTNER p");
            strQuery.append(" WHERE 1=1 ");

            if (!DataUtil.isStringNullOrEmpty(partner.getCode())) {
                strQuery.append(" AND UPPER(CODE) LIKE :code ");
                parameterMap.put("code", "%" + partner.getCode().toUpperCase() + "%");
            }

            if (!DataUtil.isStringNullOrEmpty(partner.getName())) {
                strQuery.append(" AND UPPER(NAME) LIKE :name ");
                parameterMap.put("name", "%" + partner.getName().toUpperCase() + "%");
            }

            if (!DataUtil.isStringNullOrEmpty(partner.getIdNo())) {
                strQuery.append(" AND (UPPER(REPRESENT_ID_NO) LIKE :idNo OR UPPER(TIN) LIKE :idNo) ");
                parameterMap.put("idNo", "%" + partner.getIdNo().toUpperCase() + "%");
            }

            if (!DataUtil.isStringNullOrEmpty(partner.getIdType())) {
                strQuery.append(" AND REPRESENT_ID_TYPE = :idType ");
                parameterMap.put("idType", partner.getIdType());
            }

            if (!DataUtil.isStringNullOrEmpty(partner.getStatus())) {
                strQuery.append(" AND STATUS = :status ");
                parameterMap.put("status", partner.getStatus());
            }

            if (!DataUtil.isStringNullOrEmpty(partner.getFromDate())) {
                strQuery.append(" AND CREATE_DATETIME >= STR_TO_DATE(:fromDate,'%Y-%m-%d') ");
                parameterMap.put("fromDate", partner.getFromDate());
            }

            if (!DataUtil.isStringNullOrEmpty(partner.getToDate())) {
                strQuery.append(" AND CREATE_DATETIME <= STR_TO_DATE(:toDate,'%Y-%m-%d') ");
                parameterMap.put("toDate", partner.getToDate());
            }

            strQuery.append("ORDER BY p.CREATE_DATETIME DESC ");

            Query query = em.createNativeQuery(strQuery.toString());
            query.setMaxResults(page.getPageSize());
            query.setFirstResult((page.getPageNumber()) * page.getPageSize());

            for (Map.Entry<String, Object> p : parameterMap.entrySet()) {
                query.setParameter(p.getKey(), p.getValue());
            }
            List<Object[]> queryResult = query.getResultList();
            if (!DataUtil.isNullOrEmpty(queryResult)) {
                for (Object[] objects : queryResult) {
                    PartnerSearchSdo dto = new PartnerSearchSdo();

                    dto.setPartnerId(DataUtil.safeToLong(objects[0]));
                    dto.setCode(DataUtil.safeToString(objects[1]));
                    dto.setName(DataUtil.safeToString(objects[2]));
                    dto.setTin(DataUtil.safeToString(objects[3]));
                    dto.setIdNo(DataUtil.safeToString(objects[4]));
                    dto.setIdType(DataUtil.safeToString(objects[5]));
                    dto.setRepresentName(DataUtil.safeToString(objects[6]));
                    dto.setStatus(DataUtil.safeToString(objects[7]));
                    dto.setListProductCode(DataUtil.safeToString(objects[8]));

                    result.add(dto);
                }
            }
        }
        return new PageImpl<>(result, page, count);
    }
    //</editor-fold>



}
