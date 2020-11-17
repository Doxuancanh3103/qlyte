package vt.qlkdtt.yte.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.dto.AreaProductOfferFindDTO;
import vt.qlkdtt.yte.dto.CustomerProductOfferFindDTO;
import vt.qlkdtt.yte.dto.PartnerRevenueShareSpecificDTO;
import vt.qlkdtt.yte.dto.PcsProductOfferFindDTO;
import vt.qlkdtt.yte.repository.*;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.ProductOfferSearchSdi;
import vt.qlkdtt.yte.service.sdo.ProOfferProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferSearchSdo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class ProductOfferRepoCustomImpl implements ProductOfferRepoCustom {
    @Autowired
    EntityManager em;

    @Autowired
    ProductOfferAreaRepo productOfferAreaRepo;

    @Autowired
    CustomerPropectiveRepo customerPropectiveRepo;

    @Autowired
    ProductCustSegmRepo productCustSegmRepo;

    @Autowired
    DocumentMappingRepo documentMappingRepo;

    @Autowired
    PartnerRevenueShareRepo partnerRevenueShareRepo;

    @Autowired
    AreaRepo areaRepo;

    //<editor-fold desc="Get count all item">
    private Long getCountAllItem (ProductOfferSearchSdi sdi){
        Long productGroupId = sdi.getProductGroupId();
        String productNameCode = sdi.getProductNameCode();
        String status = sdi.getStatus();
        String provinceCode = sdi.getProvinceCode();
        String districtCode = sdi.getDistrictCode();
        String fromDate = sdi.getFromDate();
        String toDate = sdi.getToDate();

        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("SELECT COUNT(*) FROM ");
        sql.append("(SELECT DISTINCT ");
        sql.append("PO.PRODUCT_OFFER_ID, ");
        sql.append("P.PRODUCT_CODE, ");
        sql.append("PO.CODE, ");
        sql.append("PO.NAME, ");
        sql.append("PO.TELCO_PRODUCT_OFFER_ID, ");
        sql.append("PO.STATUS, ");
        sql.append("( SELECT GROUP_CONCAT( DISTINCT ");
        sql.append("CONCAT( ");
        sql.append("IFNULL((SELECT CONCAT(NAME, '-') FROM AREA WHERE AREA_CODE = SUBSTR(POA.AREA_CODE, 1, 7) AND LENGTH(POA.AREA_CODE) = 7), ''), ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = SUBSTR(POA.AREA_CODE, 1, 4)) ");
        sql.append(") SEPARATOR ';') ");
        sql.append("FROM PRODUCT_OFFER_AREA POA, AREA A ");
        sql.append("WHERE POA.STATUS <> 2 AND POA.AREA_CODE = A.AREA_CODE ");
        sql.append("AND POA.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID ");
        sql.append(") PRODUCT_OFFER_AREAS, ");
        sql.append("(SELECT GROUP_CONCAT(DISTINCT CONVERT (GLV.NAME,CHAR ( 100 ))) FROM ");
        sql.append("PRODUCT_CUST_SEGM PCS, GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE ");
        sql.append("PCS.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID ");
        sql.append("AND PCS.GLOBAL_VALUE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'CUSTOMER_TYPE' ");
        sql.append(") CUSTOMER_TYPE_NAMES, ");
        sql.append("(SELECT GLV.NAME FROM ");
        sql.append("GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE ");
        sql.append("PO.PRODUCT_OFFER_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'PRODUCT_OFFER_TYPE' ");
        sql.append(") PRODUCT_OFFER_TYPE, ");
        sql.append("PO.UNIT_PRICE_BASE, ");
        sql.append("PO.UNIT_PRICE_MIN, ");
        sql.append("PO.UNIT_PRICE_MAX, ");
        sql.append("PO.VOL_BASE, ");
        sql.append("PO.VOL_PROMOTION, ");
        sql.append("PO.VAT_TYPE, ");
        sql.append("PO.STA_DATE, ");
        sql.append("PO.END_DATE  ");
        sql.append("FROM ");
        sql.append("PRODUCT_OFFER PO ");
        sql.append("LEFT JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID ");
        sql.append("LEFT JOIN PRODUCT_OFFER_AREA POA ON POA.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID AND POA.STATUS <> 2 ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrZero(productGroupId)) {
            sql.append("AND P.PRODUCT_GROUP_ID = :productGroupId ");
            params.put("productGroupId", productGroupId);
        }

        if (!DataUtil.isStringNullOrEmpty(productNameCode)) {
            sql.append("AND ( UPPER( P.PRODUCT_CODE ) LIKE :productNameCode OR UPPER( P.PRODUCT_NAME ) LIKE :productNameCode) ");
            params.put("productNameCode", "%" + productNameCode.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(status)) {
            sql.append("AND PO.STATUS = :status ");
            params.put("status", status);
        }

        if (!DataUtil.isStringNullOrEmpty(districtCode)) {
            sql.append("AND (POA.AREA_CODE = :districtCode OR POA.AREA_CODE IS NULL OR POA.AREA_CODE = '') ");
            params.put("districtCode", provinceCode + districtCode);
        } else if (!DataUtil.isStringNullOrEmpty(provinceCode)) {
            sql.append("AND (POA.AREA_CODE LIKE :provinceCode OR POA.AREA_CODE IS NULL OR POA.AREA_CODE = '') ");
            params.put("provinceCode", provinceCode + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(fromDate) && !DataUtil.isStringNullOrEmpty(toDate)) {
            sql.append("AND PO.CREATE_DATETIME BETWEEN :fromDate AND :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else {
            if (!DataUtil.isStringNullOrEmpty(fromDate)) {
                sql.append("AND PO.CREATE_DATETIME >= :fromDate ");
                params.put("fromDate", fromDate);
            }

            if (!DataUtil.isStringNullOrEmpty(toDate)) {
                sql.append("AND PO.CREATE_DATETIME <= :toDate ");
                params.put("toDate", toDate);
            }
        }

        sql.append(") PRODUCT_OFFER ");
        sql.append("ORDER BY PRODUCT_OFFER_ID DESC");

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Search product offer">
    @Override
    public Page<ProductOfferSearchSdo> searchProductOffer(ProductOfferSearchSdi sdi, Pageable pageRequest) {
        Long productGroupId = sdi.getProductGroupId();
        String productNameCode = sdi.getProductNameCode();
        String status = sdi.getStatus();
        String provinceCode = sdi.getProvinceCode();
        String districtCode = sdi.getDistrictCode();
        String fromDate = sdi.getFromDate();
        String toDate = sdi.getToDate();

        List<ProductOfferSearchSdo> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("SELECT * FROM ");
        sql.append("(SELECT DISTINCT ");
        sql.append("PO.PRODUCT_OFFER_ID, ");
        sql.append("P.PRODUCT_CODE, ");
        sql.append("PO.CODE, ");
        sql.append("PO.NAME, ");
        sql.append("PO.TELCO_PRODUCT_OFFER_ID, ");
        sql.append("PO.STATUS, ");
        sql.append("( SELECT GROUP_CONCAT( DISTINCT ");
        sql.append("CONCAT( ");
        sql.append("IFNULL((SELECT CONCAT(NAME, '-') FROM AREA WHERE AREA_CODE = SUBSTR(POA.AREA_CODE, 1, 7) AND LENGTH(POA.AREA_CODE) = 7), ''), ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA_CODE = SUBSTR(POA.AREA_CODE, 1, 4)) ");
        sql.append(") SEPARATOR ';') ");
        sql.append("FROM PRODUCT_OFFER_AREA POA, AREA A ");
        sql.append("WHERE POA.STATUS <> 2 AND POA.AREA_CODE = A.AREA_CODE ");
        sql.append("AND POA.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID ");
        sql.append(") PRODUCT_OFFER_AREAS, ");
        sql.append("(SELECT GROUP_CONCAT(DISTINCT CONVERT (GLV.NAME,CHAR ( 100 ))) FROM ");
        sql.append("PRODUCT_CUST_SEGM PCS, GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE ");
        sql.append("PCS.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID ");
        sql.append("AND PCS.GLOBAL_VALUE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'PRODUCT_CUST_SEGM' ");
        sql.append(") CUSTOMER_TYPE_NAMES, ");
        sql.append("(SELECT GLV.NAME FROM ");
        sql.append("GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE ");
        sql.append("PO.PRODUCT_OFFER_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'PRODUCT_OFFER.PRODUCT_OFFER_TYPE' ");
        sql.append(") PRODUCT_OFFER_TYPE, ");
        sql.append("PO.UNIT_PRICE_BASE, ");
        sql.append("PO.UNIT_PRICE_MIN, ");
        sql.append("PO.UNIT_PRICE_MAX, ");
        sql.append("PO.VOL_BASE, ");
        sql.append("PO.VOL_PROMOTION, ");
        sql.append("PO.VAT_TYPE, ");
        sql.append("(SELECT GLV.NAME FROM ");
        sql.append("GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE ");
        sql.append("PO.VAT_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'VAT_TYPE' ");
        sql.append(") VAT_TYPE_NAME, ");
        sql.append("PO.STA_DATE, ");
        sql.append("PO.END_DATE  ");
        sql.append("FROM ");
        sql.append("PRODUCT_OFFER PO ");
        sql.append("LEFT JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID ");
        sql.append("LEFT JOIN PRODUCT_OFFER_AREA POA ON POA.PRODUCT_OFFER_ID = PO.PRODUCT_OFFER_ID AND POA.STATUS <> 2 ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrZero(productGroupId)) {
            sql.append("AND P.PRODUCT_GROUP_ID = :productGroupId ");
            params.put("productGroupId", productGroupId);
        }

        if (!DataUtil.isStringNullOrEmpty(productNameCode)) {
            sql.append("AND ( UPPER( P.PRODUCT_CODE ) LIKE :productNameCode OR UPPER( P.PRODUCT_NAME ) LIKE :productNameCode) ");
            params.put("productNameCode", "%" + productNameCode.toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(status)) {
            sql.append("AND PO.STATUS = :status ");
            params.put("status", status);
        }

        if (!DataUtil.isStringNullOrEmpty(districtCode)) {
            sql.append("AND (POA.AREA_CODE = :districtCode OR POA.AREA_CODE IS NULL OR POA.AREA_CODE = '') ");
            params.put("districtCode", provinceCode + districtCode);
        } else if (!DataUtil.isStringNullOrEmpty(provinceCode)) {
            sql.append("AND (POA.AREA_CODE LIKE :provinceCode OR POA.AREA_CODE IS NULL OR POA.AREA_CODE = '') ");
            params.put("provinceCode", provinceCode + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(fromDate) && !DataUtil.isStringNullOrEmpty(toDate)) {
            sql.append("AND PO.CREATE_DATETIME BETWEEN :fromDate AND :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else {
            if (!DataUtil.isStringNullOrEmpty(fromDate)) {
                sql.append("AND PO.CREATE_DATETIME >= :fromDate ");
                params.put("fromDate", fromDate);
            }

            if (!DataUtil.isStringNullOrEmpty(toDate)) {
                sql.append("AND PO.CREATE_DATETIME <= :toDate ");
                params.put("toDate", toDate);
            }
        }

        sql.append(") PRODUCT_OFFER ");
        sql.append("ORDER BY PRODUCT_OFFER_ID DESC");

        Query query = em.createNativeQuery(sql.toString());
        query.setFirstResult(pageRequest.getPageNumber());
        query.setFirstResult(pageRequest.getPageSize() * pageRequest.getPageNumber());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            ProductOfferSearchSdo sdo = new ProductOfferSearchSdo();

            sdo = DataUtil.convertObjectsToClass(item, sdo);

            boolean isNationWide = productOfferAreaRepo.isNationWide(sdo.getProductOfferId());
            if (isNationWide) {
                sdo.setProductOfferAreas("Toàn quốc");
            }

            result.add(sdo);
        }

        Long countAllItem = getCountAllItem(sdi);

        return new PageImpl<>(result, pageRequest, countAllItem);
    }

    @Override
    public List<ProOfferProductSearchConnectSdo> searchProductOfferConnect(Long productId, String provisionType) {
        List<ProOfferProductSearchConnectSdo> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("PRODUCT_OFFER_ID, ");
        sql.append("CODE, ");
        sql.append("NAME, ");
        sql.append("PRODUCT_OFFER_TYPE, ");
        sql.append("UNIT_PRICE_BASE, ");
        sql.append("UNIT_PRICE_MIN, ");
        sql.append("UNIT_PRICE_MAX, ");
        sql.append("VOL_BASE, ");
        sql.append("VOL_PROMOTION ");
        sql.append("FROM PRODUCT_OFFER ");
        sql.append("WHERE STATUS = '1' ");
        sql.append("AND PRODUCT_ID = :productId ");
        sql.append("AND PROVISION_TYPE = :provisionType ");
        sql.append("ORDER BY PRODUCT_OFFER_ID DESC, UPDATE_DATETIME DESC ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);
        query.setParameter("provisionType", provisionType);

        List<Object[]> queryResult = query.getResultList();

        ProOfferProductSearchConnectSdo sdo;
        for (Object[] item : queryResult) {
            sdo = new ProOfferProductSearchConnectSdo();
            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get product offer information - Find by id">
    private ProductOfferFindByIdSdo getProductOfferInfoFindById(long productOfferId) {
        ProductOfferFindByIdSdo result = new ProductOfferFindByIdSdo();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("PO.PRODUCT_OFFER_ID, ");
        sql.append("P.PRODUCT_ID, ");
        sql.append("P.PRODUCT_CODE, ");
        sql.append("P.PRODUCT_NAME, ");
        sql.append("PO.CODE PO_CODE, ");
        sql.append("PO.NAME PO_NAME, ");
        sql.append("PO.ACCOUNTING_CODE, ");
        sql.append("PO.TELCO_PRODUCT_OFFER_ID, ");
        sql.append("PO.PRODUCT_OFFER_TYPE, ");
        sql.append("(SELECT GLV.NAME ");
        sql.append("FROM GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE PO.PRODUCT_OFFER_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'PRODUCT_OFFER_TYPE' ");
        sql.append(") PRODUCT_OFFER_TYPE_NAME, ");
        sql.append("PO.UNIT_PRICE_BASE, ");
        sql.append("PO.UNIT_PRICE_MIN, ");
        sql.append("PO.UNIT_PRICE_MAX, ");
        sql.append("PO.VAT_TYPE, ");
        sql.append("(SELECT GLV.NAME ");
        sql.append("FROM GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE PO.VAT_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'VAT_TYPE' ");
        sql.append(") VAT_TYPE_NAME, ");
        sql.append("PO.VOL_LIMITATION_TYPE, ");
        sql.append("(SELECT GLV.NAME ");
        sql.append("FROM GLOBAL_LIST_VALUE GLV, GLOBAL_LIST GL ");
        sql.append("WHERE PO.VOL_LIMITATION_TYPE = GLV.VALUE ");
        sql.append("AND GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID ");
        sql.append("AND GL.CODE = 'VOL_LIMITATION_TYPE' ");
        sql.append(") VOL_LIMITATION_TYPE_NAME, ");
        sql.append("PO.VOL_BASE, ");
        sql.append("PO.VOL_PROMOTION, ");
        sql.append("PO.STATUS, ");
        sql.append("PO.STA_DATE, ");
        sql.append("PO.END_DATE, ");
        sql.append("D.DOCUMENT_ID DOCUMENT_ID, ");
        sql.append("D.CODE DOCUMENT_CODE, ");
        sql.append("D.NAME DOCUMENT_NAME, ");
        sql.append("PO.PROVISION_TYPE, ");
        sql.append("PO.GL_CODE_1, ");
        sql.append("PO.GL_CODE_2, ");
        sql.append("PO.SALES_ITEM_CODE ");
        sql.append("FROM PRODUCT_OFFER PO ");
        sql.append("LEFT JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID ");
        sql.append("LEFT JOIN DOCUMENT_MAPPING DM ON DM.MAPPING_OBJECT_ID = PO.PRODUCT_OFFER_ID AND DM.OBJECT_TYPE = 'PRODUCT_OFFER' ");
        sql.append("LEFT JOIN DOCUMENT D ON D.DOCUMENT_ID = DM.DOCUMENT_ID ");
        sql.append("WHERE PO.PRODUCT_OFFER_ID = :productOfferId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferId", productOfferId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            result = DataUtil.convertObjectsToClass(item, result);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get list customer type - Find by id">
    private List<PcsProductOfferFindDTO> getListCustomerTypeFindById(long productOfferId) {
        List<PcsProductOfferFindDTO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("PCS.PRODUCT_CUST_SEGM_ID, ");
        sql.append("GLV.GLOBAL_LIST_VALUE_ID, ");
        sql.append("GLV.NAME, ");
        sql.append("GLV.VALUE, ");
        sql.append("GLV.DESCRIPTION ");
        sql.append("FROM ");
        sql.append("PRODUCT_CUST_SEGM PCS, ");
        sql.append("GLOBAL_LIST_VALUE GLV, ");
        sql.append("GLOBAL_LIST GL ");
        sql.append("WHERE PCS.STATUS = 1 ");
        sql.append("AND GL.CODE = 'PRODUCT_CUST_SEGM' ");
        sql.append("AND GL.GLOBAL_LIST_ID = GLV.GLOBAL_LIST_ID ");
        sql.append("AND GLV.VALUE = PCS.GLOBAL_VALUE ");
        sql.append("AND PCS.PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferId", productOfferId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            PcsProductOfferFindDTO pcsDTO = new PcsProductOfferFindDTO();
            pcsDTO.setProductCustSegmId(DataUtil.safeToLong(item[0]));
            pcsDTO.setId(DataUtil.safeToLong(item[1]));
            pcsDTO.setName(DataUtil.safeToString(item[2]));
            pcsDTO.setValue(DataUtil.safeToString(item[3]));
            pcsDTO.setDescription(DataUtil.safeToString(item[4]));

            result.add(pcsDTO);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get list product offer area - Find by id">
    private List<AreaProductOfferFindDTO> getListProductOfferAreaFindById(long productOfferId) {
        final int AREA_CODE_PRECINCT_LENGTH = 10;
        final int AREA_CODE_DISTRICT_LENGTH = 7;
        final int AREA_CODE_PROVINCE_LENGTH = 4;

        List<AreaProductOfferFindDTO> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("POA.PRODUCT_OFFER_AREA_ID, ");
        sql.append("POA.AREA_CODE, ");
        sql.append("A.PROVINCE, ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA.PROVINCE = A.PROVINCE AND AREA.DISTRICT IS NULL) PROVINCE_NAME, ");
        sql.append("A.DISTRICT, ");
        sql.append("(SELECT NAME FROM AREA WHERE AREA.PROVINCE = A.PROVINCE AND AREA.DISTRICT = A.DISTRICT AND AREA.PRECINCT IS NULL) DISTRICT_NAME, ");
        sql.append("POA.STATUS, ");
        sql.append("POA.STA_DATE, ");
        sql.append("POA.END_DATE ");
        sql.append("FROM ");
        sql.append("PRODUCT_OFFER_AREA POA ");
        sql.append("LEFT JOIN AREA A ON POA.AREA_CODE = A.AREA_CODE ");
        sql.append("WHERE 1 = 1 ");

        boolean isNationWide = productOfferAreaRepo.isNationWide(productOfferId);

        if (isNationWide) {
            sql.append("AND (POA.AREA_CODE IS NULL OR POA.AREA_CODE = '') ");
        } else {
            sql.append("AND POA.AREA_CODE = A.AREA_CODE ");
        }

        sql.append("AND POA.STATUS <> 2 ");
        sql.append("AND POA.PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferId", productOfferId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            AreaProductOfferFindDTO dto = new AreaProductOfferFindDTO();
            dto = DataUtil.convertObjectsToClass(item, dto);

            if (!DataUtil.isNullOrEmpty(dto.getAreaCode())) {
                if (dto.getAreaCode().length() == AREA_CODE_PRECINCT_LENGTH) {
                    dto.setAreaCodePrecinct(dto.getAreaCode());
                    dto.setAreaCodeDistrict(dto.getAreaCode().substring(4, 7));
                    dto.setAreaCodeProvince(dto.getAreaCode().substring(0, 4));
                } else if (dto.getAreaCode().length() == AREA_CODE_DISTRICT_LENGTH) {
                    dto.setAreaCodeDistrict(dto.getAreaCode());
                    dto.setAreaCodeProvince(dto.getAreaCode().substring(0, 4));
                } else if (dto.getAreaCode().length() == AREA_CODE_PROVINCE_LENGTH) {
                    dto.setAreaCodeProvince(dto.getAreaCode());
                }
            }

            result.add(dto);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get list product offer customer - Find by id">
    private List<CustomerProductOfferFindDTO> getListProductOfferCustomerFindById(long productOfferId) {
        List<CustomerProductOfferFindDTO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT ");
        sql.append("CP.PROPECTIVE_CUSTOMER_ID, ");
        sql.append("( SELECT DISTINCT AREA_CODE FROM AREA WHERE PROVINCE = CP.PROVINCE AND DISTRICT IS NULL ) AREA_CODE_PROVINCE, ");
        sql.append("( SELECT DISTINCT AREA_CODE FROM AREA WHERE DISTRICT = CP.DISTRICT AND PROVINCE = CP.PROVINCE AND PRECINCT IS NULL ) AREA_CODE_DISTRICT, ");
        sql.append("CP.PROVINCE, ");
        sql.append("(SELECT DISTINCT NAME FROM AREA WHERE PROVINCE = CP.PROVINCE AND DISTRICT IS NULL) PROVINCE_NAME, ");
        sql.append("CP.DISTRICT, ");
        sql.append("(SELECT DISTINCT NAME FROM AREA WHERE DISTRICT = CP.DISTRICT AND PROVINCE = CP.PROVINCE AND PRECINCT IS NULL) DISTRICT_NAME, ");
        sql.append("CP.CUSTOMER_NAME, ");
        sql.append("CP.IDENTITY_TYPE, ");
        sql.append("CP.IDENTITY_NO, ");
        sql.append("CP.MEDICAL_NO, ");
        sql.append("CP.STATUS, ");
        sql.append("CP.STA_DATETIME, ");
        sql.append("CP.DUE_DATE, ");
        sql.append("CP.CUSTOMER_BUS_TYPE ");
        sql.append("FROM ");
        sql.append("CUSTOMER_PROPECTIVE CP ");
        sql.append("WHERE ");
        sql.append("CP.PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferId", productOfferId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            CustomerProductOfferFindDTO dto = new CustomerProductOfferFindDTO();
            dto = DataUtil.convertObjectsToClass(item, dto);

            result.add(dto);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get list partner revenue shared specific - Find by id">
    private List<PartnerRevenueShareSpecificDTO> getListPartnerRevenueShareSpecificFindById(long productOfferId) {
        List<PartnerRevenueShareSpecificDTO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("PRS.PARTNER_REVENUE_SHARED_ID, ");
        sql.append("PN.PARTNER_ID, ");
        sql.append("PN.NAME, ");
        sql.append("PN.TIN, ");
        sql.append("PRS.PARTNER_PERCENT, ");
        sql.append("D.DOCUMENT_ID, ");
        sql.append("D.CODE, ");
        sql.append("GLV.VALUE SHARE_TYPE_VALUE, ");
        sql.append("GLV.NAME SHARE_TYPE_NAME ");
        sql.append("FROM PARTNER_REVENUE_SHARED PRS ");
        sql.append("JOIN PARTNER PN ON PRS.PARTNER_ID = PN.PARTNER_ID ");
        sql.append("LEFT JOIN DOCUMENT_MAPPING DM ON DM.MAPPING_OBJECT_ID = PRS.PARTNER_REVENUE_SHARED_ID AND DM.OBJECT_TYPE = 'PARTNER_REVENUE_SHARE' AND DM.STATUS = 1 ");
        sql.append("LEFT JOIN DOCUMENT D ON DM.DOCUMENT_ID = D.DOCUMENT_ID ");
        sql.append("LEFT JOIN GLOBAL_LIST_VALUE GLV ON PRS.SHARE_TYPE = GLV.VALUE ");
        sql.append("JOIN GLOBAL_LIST GL ON GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID AND GL.CODE = 'PARTNER_SHARE_TYPE' ");
        sql.append("WHERE PRS.PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferId", productOfferId);

        List<Object[]> queryResult = query.getResultList();

        for (Object[] item : queryResult) {
            PartnerRevenueShareSpecificDTO dto = new PartnerRevenueShareSpecificDTO();
            dto = DataUtil.convertObjectsToClass(item, dto);

            result.add(dto);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Find by id">
    @Override
    public ProductOfferFindByIdSdo findProductOfferById(long productOfferId) {
        ProductOfferFindByIdSdo result = getProductOfferInfoFindById(productOfferId);

        if (DataUtil.isNullOrZero(result.getProductOfferId())) {
            throw new AppException("API-PO082", "productOfferId " + productOfferId + " not exist");
        }

        List<PcsProductOfferFindDTO> listCustomerType = getListCustomerTypeFindById(productOfferId);
        List<AreaProductOfferFindDTO> listProductOfferArea = getListProductOfferAreaFindById(productOfferId);
        List<CustomerProductOfferFindDTO> listProductOfferCustomer = getListProductOfferCustomerFindById(productOfferId);
        List<PartnerRevenueShareSpecificDTO> listPartnerRevenueShareSpecific = getListPartnerRevenueShareSpecificFindById(productOfferId);

        result.setListCustomerType(listCustomerType);
        result.setListProductOfferArea(listProductOfferArea);
        result.setListProductOfferCustomer(listProductOfferCustomer);
        result.setListPartnerRevenueShareSpecific(listPartnerRevenueShareSpecific);

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    public boolean changeStatus(long id, String status) {
        StringBuilder sql = new StringBuilder("UPDATE PRODUCT_OFFER SET STATUS = :status WHERE PRODUCT_OFFER_ID = :id");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("id", id);

        Object queryResult = query.executeUpdate();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product offer with customer type">
    @Override
    public void mappingInsertProductOfferWithCustomerType(List<String> listCustomerType, long productOfferId) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO PRODUCT_CUST_SEGM (GLOBAL_VALUE, PRODUCT_OFFER_ID, STATUS, CREATE_USER, CREATE_DATETIME) ");
        sql.append("VALUES (:customerType, :productOfferId, 1, :createUser, NOW())");

        for (String customerType : listCustomerType) {
            Query query = em.createNativeQuery(sql.toString());
            query.setParameter("customerType", customerType);
            query.setParameter("productOfferId", productOfferId);
            query.setParameter("createUser", "ADMIN");

            query.executeUpdate();
        }
    }
    //</editor-fold>

    //<editor-fold desc="is Exist">
    @Override
    public boolean isExist(String productOfferCode) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PRODUCT_OFFER WHERE CODE = :productOfferCode");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferCode", productOfferCode);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExist(long productOfferId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PRODUCT_OFFER WHERE PRODUCT_OFFER_ID = :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferId", productOfferId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }

    @Override
    public boolean isExist(String productOfferCode, long productOfferId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PRODUCT_OFFER WHERE CODE = :productOfferCode AND PRODUCT_OFFER_ID <> :productOfferId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productOfferCode", productOfferCode);
        query.setParameter("productOfferId", productOfferId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>
}
