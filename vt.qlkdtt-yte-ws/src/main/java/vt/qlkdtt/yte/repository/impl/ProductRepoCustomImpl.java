package vt.qlkdtt.yte.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.dto.DocumentDTO;
import vt.qlkdtt.yte.dto.GlobalListValueDTO;
import vt.qlkdtt.yte.dto.PartnerProductFindDTO;
import vt.qlkdtt.yte.repository.*;
import vt.qlkdtt.yte.service.DocumentMappingService;
import vt.qlkdtt.yte.service.DocumentService;
import vt.qlkdtt.yte.service.PartnerRevenueShareService;
import vt.qlkdtt.yte.service.ProductCustSegmService;
import vt.qlkdtt.yte.service.sdi.*;
import vt.qlkdtt.yte.service.sdo.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;


@Slf4j
public class ProductRepoCustomImpl implements ProductRepoCustom {
    @Autowired
    EntityManager em;

    @Autowired
    DocumentRepoCustom documentRepoCustom;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    GlobalListRepo globalListRepo;

    @Autowired
    ProductCustSegmRepo productCustSegmRepo;

    @Autowired
    PartnerRevenueSharedRepo partnerRevenueSharedRepo;

    @Autowired
    PartnerRevenueShareRepo partnerRevenueShareRepo;

    @Autowired
    DocumentService documentService;

    @Autowired
    ProductCustSegmService productCustSegmService;

    @Autowired
    PartnerRevenueShareService partnerRevenueShareService;

    @Autowired
    DocumentMappingRepo documentMappingRepo;

    @Autowired
    DocumentMappingService documentMappingService;

    //<editor-fold desc="Get sale exp product find by id">
    private List<SalesExpProductFindSdo> getSaleExpProductFindById(Long productId) {
        List<SalesExpProductFindSdo> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("PRODUCT_SALE_EXP_ID, ");
        sql.append("SALE_CHANNEL, ");
        sql.append("RATE_SALESMAN, ");
        sql.append("RATE_BROKER, ");
        sql.append("STA_DATE, ");
        sql.append("END_DATE, ");
        sql.append("STATUS ");
        sql.append("FROM PRODUCT_SALE_EXPENSES ");
        sql.append("WHERE STATUS <> 2 AND PRODUCT_ID = :productId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        List<Object[]> queryResult = query.getResultList();

        SalesExpProductFindSdo sdo;
        for (Object[] item : queryResult) {
            sdo = new SalesExpProductFindSdo();
            sdo = DataUtil.convertObjectsToClass(item, sdo);

            result.add(sdo);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Search product by id">
    @Override
    public ProductFindByIdSdo searchProductById(long productId) {
        ProductFindByIdSdo productFindByIdSdo = getProductFindById(productId);

        List<GlobalListValueDTO> customerTypes = getCustomerTypesProductFindBydId(productId);
        List<DocumentDTO> documents = getDocumentsProductFindBydId(productId);
        List<PartnerProductFindDTO> partners = getPartnersProductFindBydId(productId);
        List<SalesExpProductFindSdo> saleExps = getSaleExpProductFindById(productId);

        productFindByIdSdo.setDocuments(documents);
        productFindByIdSdo.setCustomerType(customerTypes);
        productFindByIdSdo.setPartners(partners);
        productFindByIdSdo.setSaleExps(saleExps);

        return productFindByIdSdo;
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    public boolean changeStatus(long productId, String status) {
        Map<String, Object> result = new HashMap<>();
        StringBuilder sql = new StringBuilder("UPDATE PRODUCT SET STATUS = :status WHERE PRODUCT_ID = :productId AND STATUS <> :status");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("status", status);
        query.setParameter("productId", productId);

        Object queryResult = query.executeUpdate();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="Count all item search product">
    private Long countAllItemSearchProduct(ProductSearchSdi productSearchSdi) {
        List<ProductSearchSdo> listProductSearch = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("SELECT COUNT(*) ");
        sql.append("FROM PRODUCT PR ");
        sql.append("LEFT JOIN GLOBAL_LIST_VALUE GLV ON GLV.VALUE = PR.PRODUCT_TYPE ");
        sql.append("JOIN GLOBAL_LIST GL ON GL.GLOBAL_LIST_ID = GLV.GLOBAL_LIST_ID AND GL.CODE = 'PRODUCT_TYPE' ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getProductGroupId())) {
            sql.append("AND PR.PRODUCT_GROUP_ID = :groupId ");
            params.put("groupId", productSearchSdi.getProductGroupId());
        }

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getProductNameCode())) {
            sql.append("AND (UPPER(PR.PRODUCT_NAME) LIKE :prNameCode OR UPPER(PR.PRODUCT_CODE) LIKE :prNameCode)");
            params.put("prNameCode", "%" + productSearchSdi.getProductNameCode().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getFromDate()) && !DataUtil.isStringNullOrEmpty(productSearchSdi.getToDate())) {
            sql.append("AND ((PR.EFFECT_DATE BETWEEN :fromDate AND :toDate) OR (PR.EXPIRE_DATE BETWEEN :fromDate AND :toDate)) ");
            params.put("fromDate", productSearchSdi.getFromDate());
            params.put("toDate", productSearchSdi.getToDate());
        } else {
            if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getFromDate())) {
                sql.append("AND PR.EFFECT_DATE >= STR_TO_DATE( :fromDate ,'%Y-%m-%d') ");
                params.put("fromDate", productSearchSdi.getFromDate());
            }

            if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getToDate())) {
                sql.append("AND PR.EXPIRE_DATE <= STR_TO_DATE( :toDate ,'%Y-%m-%d') ");
                params.put("toDate", productSearchSdi.getToDate());
            }
        }

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getStatus())) {
            sql.append("AND PR.STATUS = :status ");
            params.put("status", productSearchSdi.getStatus());
        }

        sql.append("ORDER BY PR.CREATE_DATE DESC");

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult);
    }
    //</editor-fold>

    //<editor-fold desc="Search product by Group name, product name, product code">
    @Override
    public Page<ProductSearchSdo> searchProduct(ProductSearchSdi productSearchSdi, Pageable pageable) {
        List<ProductSearchSdo> listProductSearch = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        sql.append("SELECT ");
        sql.append("PR.PRODUCT_ID, ");
        sql.append("PR.PRODUCT_CODE, ");
        sql.append("PR.PRODUCT_NAME, ");
        sql.append("GLV.NAME PRODUCT_TYPE, ");
        sql.append("(SELECT GROUP_CONCAT(DISTINCT CONVERT (PN.CODE, CHAR (100))) ");
        sql.append("FROM ");
        sql.append("PARTNER_REVENUE_SHARED PRS, ");
        sql.append("PARTNER PN  ");
        sql.append("WHERE ");
        sql.append("PRS.PARTNER_ID = PN.PARTNER_ID ");
        sql.append("AND PRS.PRODUCT_ID = PR.PRODUCT_ID ");
        sql.append("AND PRS.STATUS = 1 ");
        sql.append(") LIST_PARTNER_CODE, ");
        sql.append("PR.STATUS, ");
        sql.append("PR.TELCO_SERVICE_ID, ");
        sql.append("PR.TELCO_PRODUCT_CODE ");
        sql.append("FROM PRODUCT PR ");
        sql.append("LEFT JOIN GLOBAL_LIST_VALUE GLV ON GLV.VALUE = PR.PRODUCT_TYPE ");
        sql.append("JOIN GLOBAL_LIST GL ON GL.GLOBAL_LIST_ID = GLV.GLOBAL_LIST_ID AND GL.CODE = 'PRODUCT_TYPE' ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getProductGroupId())) {
            sql.append("AND PR.PRODUCT_GROUP_ID = :groupId ");
            params.put("groupId", productSearchSdi.getProductGroupId());
        }

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getProductNameCode())) {
            sql.append("AND (UPPER(PR.PRODUCT_NAME) LIKE :prNameCode OR UPPER(PR.PRODUCT_CODE) LIKE :prNameCode)");
            params.put("prNameCode", "%" + productSearchSdi.getProductNameCode().toUpperCase() + "%");
        }

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getFromDate()) && !DataUtil.isStringNullOrEmpty(productSearchSdi.getToDate())) {
            sql.append("AND ((PR.EFFECT_DATE BETWEEN :fromDate AND :toDate) OR (PR.EXPIRE_DATE BETWEEN :fromDate AND :toDate)) ");
            params.put("fromDate", productSearchSdi.getFromDate());
            params.put("toDate", productSearchSdi.getToDate());
        } else {
            if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getFromDate())) {
                sql.append("AND PR.EFFECT_DATE >= STR_TO_DATE( :fromDate ,'%Y-%m-%d') ");
                params.put("fromDate", productSearchSdi.getFromDate());
            }

            if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getToDate())) {
                sql.append("AND PR.EXPIRE_DATE <= STR_TO_DATE( :toDate ,'%Y-%m-%d') ");
                params.put("toDate", productSearchSdi.getToDate());
            }
        }

        if (!DataUtil.isStringNullOrEmpty(productSearchSdi.getStatus())) {
            sql.append("AND PR.STATUS = :status ");
            params.put("status", productSearchSdi.getStatus());
        }

        sql.append("ORDER BY PR.CREATE_DATE DESC");

        Query query = em.createNativeQuery(sql.toString());
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List<Object[]> queryResult = query.getResultList();
        for (Object[] item : queryResult) {
            ProductSearchSdo sdo = new ProductSearchSdo();
            sdo = DataUtil.convertObjectsToClass(item, sdo);

            listProductSearch.add(sdo);
        }

        Long countAllItem = countAllItemSearchProduct(productSearchSdi);

        return new PageImpl<>(listProductSearch, pageable, countAllItem);
    }
    //</editor-fold>

    //<editor-fold desc="Get list partner - Product search connect">
    private List<PartnerProductSearchConnectSdo> getListPartnerProductSearchConnect(Long productId) {
        List<PartnerProductSearchConnectSdo> result = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ");
        sql.append("PN.PARTNER_ID, ");
        sql.append("PN.NAME, ");
        sql.append("PN.CODE  ");
        sql.append("FROM ");
        sql.append("PARTNER PN, ");
        sql.append("PARTNER_REVENUE_SHARED PRS ");
        sql.append("WHERE PN.STATUS = '1' AND PRS.STATUS = '1' ");
        sql.append("AND PN.PARTNER_ID = PRS.PARTNER_ID ");
        sql.append("AND PRS.PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        List queryResult = query.getResultList();

        for (Object item : queryResult) {
            Object[] objects = (Object[]) item;

            PartnerProductSearchConnectSdo sdo = new PartnerProductSearchConnectSdo();
            sdo = DataUtil.convertObjectsToClass(objects, sdo);

            result.add(sdo);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Search Product connect">
    @Override
    public List<ProductSearchConnectSdo> searchProductConnect(String productNameCode) {
        List<ProductSearchConnectSdo> result = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("P.PRODUCT_ID, ");
        sql.append("P.PRODUCT_NAME, ");
        sql.append("P.PRODUCT_CODE ");
        sql.append("FROM PRODUCT P ");
        sql.append("WHERE P.STATUS = '1' ");

        if (!DataUtil.isNullOrEmpty(productNameCode)) {
            sql.append("AND (UPPER( P.PRODUCT_NAME ) LIKE :productNameCode OR UPPER( P.PRODUCT_CODE ) LIKE :productNameCode) ");
            params.put("productNameCode", "%" + productNameCode.toUpperCase() + "%");
        }

        sql.append("ORDER BY P.CREATE_DATE DESC ");

        Query query = em.createNativeQuery(sql.toString());

        for (Map.Entry<String, Object> item : params.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }

        List queryResult = query.getResultList();

        for (Object item : queryResult) {
            Object[] objects = (Object[]) item;

            ProductSearchConnectSdo sdo = new ProductSearchConnectSdo();
            sdo = DataUtil.convertObjectsToClass(objects, sdo);

            List<PartnerProductSearchConnectSdo> listPartner = getListPartnerProductSearchConnect(sdo.getProductId());

            sdo.setListPartner(listPartner);

            result.add(sdo);
        }


        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get all info product find by id">
    private ProductFindByIdSdo getProductFindById(long productId) {
        ProductFindByIdSdo sdo = new ProductFindByIdSdo();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("PR.PRODUCT_ID, ");
        sql.append("PR.PRODUCT_CODE, ");
        sql.append("PR.PRODUCT_NAME, ");
        sql.append("PR.PRODUCT_GROUP_ID, ");
        sql.append("PR.PRODUCT_TYPE, ");
        sql.append("PR.STATUS, ");
        sql.append("PR.TELCO_SERVICE_ID, ");
        sql.append("PR.TELCO_PRODUCT_CODE, ");
        sql.append("PR.GL_CODE_1, ");
        sql.append("PR.GL_CODE_2, ");
        sql.append("PR.SALES_ITEM_CODE ");
        sql.append("FROM PRODUCT PR ");
        sql.append("WHERE PR.PRODUCT_ID = :productId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        List<Object[]> queryResult = query.getResultList();

        if (!DataUtil.isNullOrEmpty(queryResult)) {
            for (Object[] item : queryResult) {
                sdo = DataUtil.convertObjectsToClass(item, sdo);

                break;
            }
        }

        return sdo;
    }
    //</editor-fold>

    //<editor-fold desc="Get documents in product find by id">
    private List<DocumentDTO> getDocumentsProductFindBydId(long productId) {
        List<DocumentDTO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("DC.DOCUMENT_ID, ");
        sql.append("DC.CODE, ");
        sql.append("DC.NAME, ");
        sql.append("DC.DESCRIPTION, ");
        sql.append("DC.SIGN_DATE, ");
        sql.append("DC.EFFECT_DATE,  ");
        sql.append("DC.EXPIRE_DATE, ");
        sql.append("DC.FILE_NAME ");
        sql.append("FROM ");
        sql.append("DOCUMENT DC, ");
        sql.append("DOCUMENT_MAPPING DM ");
        sql.append("WHERE DM.STATUS = 1 ");
        sql.append("AND DC.DOCUMENT_ID = DM.DOCUMENT_ID ");
        sql.append("AND DM.OBJECT_TYPE = 'PRODUCT' ");
        sql.append("AND DM.MAPPING_OBJECT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        List queryResult = query.getResultList();

        for (Object item : queryResult) {
            Object[] objects = (Object[]) item;
            DocumentDTO dto = new DocumentDTO();

            dto.setId(DataUtil.safeToLong(objects[0]));
            dto.setCode(DataUtil.safeToString(objects[1]));
            dto.setName(DataUtil.safeToString(objects[2]));
            dto.setDescription(DataUtil.safeToString(objects[3]));
            dto.setSignDate(DataUtil.safeToDate(objects[4]));
            dto.setEffectDate(DataUtil.safeToDate(objects[5]));
            dto.setExpireDate(DataUtil.safeToDate(objects[6]));

            result.add(dto);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get customerTypes in product find by id">
    private List<GlobalListValueDTO> getCustomerTypesProductFindBydId(long productId) {
        List<GlobalListValueDTO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("PCS.PRODUCT_CUST_SEGM_ID, ");
        sql.append("GLV.GLOBAL_LIST_VALUE_ID, ");
        sql.append("GLV.VALUE, ");
        sql.append("GLV.NAME, ");
        sql.append("GLV.DESCRIPTION ");
        sql.append("FROM ");
        sql.append("GLOBAL_LIST GL, ");
        sql.append("GLOBAL_LIST_VALUE GLV, ");
        sql.append("PRODUCT_CUST_SEGM PCS ");
        sql.append("WHERE ");
        sql.append("PCS.STATUS = 1 ");
        sql.append("AND GL.CODE = 'PRODUCT_CUST_SEGM' ");
        sql.append("AND GL.GLOBAL_LIST_ID = GLV.GLOBAL_LIST_ID ");
        sql.append("AND GLV.VALUE = PCS.GLOBAL_VALUE ");
        sql.append("AND PCS.PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        List queryResult = query.getResultList();

        for (Object item : queryResult) {
            Object[] objects = (Object[]) item;
            GlobalListValueDTO dto = new GlobalListValueDTO();

            dto.setProductCustSegmId(DataUtil.safeToLong(objects[0]));
            dto.setId(DataUtil.safeToLong(objects[1]));
            dto.setValue(DataUtil.safeToString(objects[2]));
            dto.setName(DataUtil.safeToString(objects[3]));
            dto.setDescription(DataUtil.safeToString(objects[4]));

            result.add(dto);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Get partners in product find by id">
    private List<PartnerProductFindDTO> getPartnersProductFindBydId(long productId) {
        List<PartnerProductFindDTO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("PRS.PARTNER_REVENUE_SHARED_ID, ");
        sql.append("DC.DOCUMENT_ID, ");
        sql.append("DC.NAME, ");
        sql.append("PN.PARTNER_ID, ");
        sql.append("PN.NAME PARTNER_NAME, ");
        sql.append("PN.TIN, ");
        sql.append("PRS.PARTNER_PERCENT, ");
        sql.append("GLV.NAME SHARE_TYPE_NAME, ");
        sql.append("GLV.VALUE SHARE_TYPE_VALUE, ");
        sql.append("PRS.DESCRIPTION ");
        sql.append("FROM PARTNER_REVENUE_SHARED PRS ");
        sql.append("LEFT JOIN PARTNER PN ON PN.PARTNER_ID = PRS.PARTNER_ID ");
        sql.append("LEFT JOIN DOCUMENT_MAPPING DM ON PRS.PARTNER_REVENUE_SHARED_ID = DM.MAPPING_OBJECT_ID AND DM.OBJECT_TYPE = 'PARTNER_REVENUE_SHARE' ");
        sql.append("LEFT JOIN DOCUMENT DC ON DM.DOCUMENT_ID = DC.DOCUMENT_ID ");
        sql.append("LEFT JOIN GLOBAL_LIST_VALUE GLV ON PRS.SHARE_TYPE = GLV.VALUE ");
        sql.append("JOIN GLOBAL_LIST GL ON GLV.GLOBAL_LIST_ID = GL.GLOBAL_LIST_ID AND GL.CODE = 'PARTNER_SHARE_TYPE' ");
        sql.append("WHERE PRS.STATUS = 1 ");
        sql.append("AND PRS.PRODUCT_ID = :productId ");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        List queryResult = query.getResultList();

        for (Object item : queryResult) {
            Object[] objects = (Object[]) item;
            PartnerProductFindDTO dto = new PartnerProductFindDTO();
            dto = DataUtil.convertObjectsToClass(objects, dto);

            result.add(dto);
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product with customer type">
    @Override
    public void mappingInsertProductWithCustomerType(long productId, List<String> customerTypes) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO PRODUCT_CUST_SEGM (GLOBAL_VALUE, PRODUCT_ID, STATUS, CREATE_USER, CREATE_DATETIME) ");
        sql.append("VALUES (:customerTypeValue, :productId, 1, 'ADMIN', NOW())");

        if (customerTypes != null && customerTypes.size() > 0) {
            for (String customerType : customerTypes) {
                Query query = em.createNativeQuery(sql.toString());
                query.setParameter("customerTypeValue", customerType);
                query.setParameter("productId", productId);

                query.executeUpdate();
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="isExist">
    @Override
    public boolean isExist(String productCode) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PRODUCT WHERE PRODUCT_CODE = :productCode");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productCode", productCode);

        Object queryResult = query.getSingleResult();

        if (DataUtil.safeToInt(queryResult) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isExist(long productId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM PRODUCT WHERE PRODUCT_ID = :productId");

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productId", productId);

        Object queryResult = query.getSingleResult();

        if (DataUtil.safeToLong(queryResult) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isExist(Long productId, String productCode) {
        String sql = "SELECT COUNT(*) FROM PRODUCT WHERE PRODUCT_CODE = :productCode AND PRODUCT_ID != :productId";

        Query query = em.createNativeQuery(sql.toString());
        query.setParameter("productCode", productCode);
        query.setParameter("productId", productId);

        Object queryResult = query.getSingleResult();

        return DataUtil.safeToLong(queryResult) > 0;
    }
    //</editor-fold>
}
