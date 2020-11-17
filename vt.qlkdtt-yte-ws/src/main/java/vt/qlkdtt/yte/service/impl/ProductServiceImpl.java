package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.*;
import vt.qlkdtt.yte.repository.*;
import vt.qlkdtt.yte.service.ProductService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.*;
import vt.qlkdtt.yte.service.sdo.ProductFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductInsertSdo;
import vt.qlkdtt.yte.service.sdo.ProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductSearchSdo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    GlobalListRepo globalListRepo;

    @Autowired
    PartnerRepo partnerRepo;

    @Autowired
    DocumentMappingRepo documentMappingRepo;

    @Autowired
    PartnerRevenueSharedRepo partnerRevenueSharedRepo;

    @Autowired
    ProductCustSegmRepo productCustSegmRepo;

    @Autowired
    ProductSaleExpRepo productSaleExpRepo;

    @Autowired
    GlobalParamRepo globalParamRepo;

    @Override
    public Page<ProductSearchSdo> searchProduct(ProductSearchSdi productSearchSdi, Pageable pageable) {
        return productRepo.searchProduct(productSearchSdi, pageable);
    }

    @Override
    public List<ProductSearchConnectSdo> searchProductConnect(String productNameCode) {
        return productRepo.searchProductConnect(productNameCode);
    }

    @Override
    public ProductFindByIdSdo searchProductById(long productId) {
        return productRepo.searchProductById(productId);
    }

    @Override
    public boolean changeStatus(long productId, String status) {
        return productRepo.changeStatus(productId, status);
    }

    //<editor-fold desc="Valid product insert input">
    private void validProductInsertInput(ProductInsertSdi sdi) {
        List<String> lstError = new ArrayList<>();

        if (DataUtil.isStringNullOrEmpty(sdi.getProductGroupId())) {
            throw new AppException("API-PR001", "productGroupId is required");
        }

        if (sdi.getProductGroupId().length() > 10) {
            throw new AppException("API-PR002", "productGroupId length cannot exceed 10 characters");
        }

        if (DataUtil.isNullOrEmpty(sdi.getProductTypeId())) {
            throw new AppException("API-PR003", "productTypeId is required");
        }

        if (sdi.getProductTypeId().length() > 2) {
            throw new AppException("API-PR004", "productType length cannot exceed 2 characters");
        }

        if (DataUtil.isStringNullOrEmpty(sdi.getProductCode())) {
            throw new AppException("API-PR005", "productCode is required");
        }

        if (sdi.getProductCode().length() > 30) {
            throw new AppException("API-PR006", "productCode length cannot exceed 30 characters");
        }

        if (DataUtil.isStringNullOrEmpty(sdi.getProductName())) {
            throw new AppException("API-PR007", "productName is required");
        }

        if (sdi.getProductName().length() > 100) {
            throw new AppException("API-PR008", "productName length cannot exceed 100 characters");
        }

        if (DataUtil.isNullObject(sdi.getServiceBccsId())) {
            throw new AppException("API-PR009", "serviceBccsId is required");
        }

        if (!DataUtil.isStringNullOrEmpty(sdi.getEffectDate())) {
            if (!DateUtil.isDate(sdi.getEffectDate(), Const.DATE_FORMAT)) {
                throw new AppException("API-PR011", "effectDate format must be yyyy-MM-dd");
            }
        }

        boolean isExistProductGroup = globalListRepo.isExist(sdi.getProductGroupId(), Const.GLOBAL_LIST_CODE.PRODUCT_GROUP);
        boolean isExistProductType = globalListRepo.isExist(sdi.getProductTypeId(), Const.GLOBAL_LIST_CODE.PRODUCT_TYPE);

        if (!isExistProductGroup) {
            throw new AppException("API-PR012", "productGroupId not exist");
        }

        if (!isExistProductType) {
            throw new AppException("API-PR013", "productTypeId not exist");
        }

        List<String> listCustomerType = sdi.getLstCustomerType();
        if (listCustomerType != null && listCustomerType.size() > 0) {
            for (String item : listCustomerType) {
                if (!DataUtil.isNullOrEmpty(item)) {
                    boolean isExistCustomerType = globalListRepo.isExist(item, Const.GLOBAL_LIST_CODE.PRODUCT_CUST_SEGM);

                    if (!isExistCustomerType) {
                        lstError.add(item);

                        throw new AppException("API-PR014", "customerType " + item + " not exist", lstError);
                    }
                }
            }
        }

        List<DocumentInsertProductSdi> lstDocument = sdi.getLstDocument();
        if (lstDocument != null && lstDocument.size() > 0) {
            if (lstDocument != null && lstDocument.size() > 0) {
                for (DocumentInsertProductSdi item : lstDocument) {
                    if (!DataUtil.isNullOrZero(item.getDocumentId())) {
                        Optional<Document> optionalDoc = documentRepo.findById(item.getDocumentId());

                        if (!optionalDoc.isPresent()) {
                            throw new AppException("API-015", "document not exist");
                        }
                    }

                    if (DataUtil.isStringNullOrEmpty(item.getCode())) {
                        throw new AppException("API-PR016", "document code is required");
                    }

                    if (item.getCode().length() > 50) {
                        throw new AppException("API-PR017", "document code length cannot exceed 50 characters");
                    }

                    if (DataUtil.isStringNullOrEmpty(item.getName())) {
                        throw new AppException("API-PR018", "document name is required");
                    }

                    if (item.getName().length() > 200) {
                        throw new AppException("API-PR019", "document name length cannot exceed 200 characters");
                    }

                    if (!DataUtil.isStringNullOrEmpty(item.getSignDate())) {
                        if (!DateUtil.isDate(item.getSignDate(), Const.DATE_FORMAT)) {
                            throw new AppException("API-PR020", "signDate document format must be yyyy-MM-dd");
                        }
                    }

                    if (!DataUtil.isStringNullOrEmpty(item.getEffectDate())) {
                        if (!DateUtil.isDate(item.getEffectDate(), Const.DATE_FORMAT)) {
                            throw new AppException("API-PR021", "effectDate document format must be yyyy-MM-dd");
                        }
                    }
                }
            }
        }


        List<PartnerInsertProductSdi> lstPartner = sdi.getLstPartner();
        if (lstPartner != null && lstPartner.size() > 0) {
            for (PartnerInsertProductSdi item : lstPartner) {
                if (!DataUtil.isNullOrZero(item.getDocumentId())) {
                    Optional<Document> optionalDoc = documentRepo.findById(item.getDocumentId());

                    if (!optionalDoc.isPresent()) {
                        throw new AppException("API-022", "document not exist");
                    }
                }


                if (DataUtil.isNullOrZero(item.getPartnerId())) {
                    throw new AppException("API-PR023", "partnerId is required");
                }

                if (DataUtil.isNullObject(item.getPartnerPercent())) {
                    throw new AppException("API-PR024", "partnerPercent is required");
                }

                if (DataUtil.isNullObject(item.getShareType())) {
                    throw new AppException("API-PR025", "share type is required");
                }

                if (item.getShareType().length() > 10) {
                    throw new AppException("API-PR026", "shareType length cannot exceed 10 characters");
                }

                boolean isExistPartner = partnerRepo.isExist(item.getPartnerId());
                if (!isExistPartner) {
                    throw new AppException("API-PR027", "partnerId not exist");
                }
            }
        }

        List<SaleExpensesInsertProductSdi> lstSaleExpenses = sdi.getLstSalesExpenses();
        if (lstSaleExpenses != null && lstSaleExpenses.size() > 0) {
            for (SaleExpensesInsertProductSdi item : lstSaleExpenses) {
                //sale chanel
                if (DataUtil.isNullOrEmpty(item.getSaleChanel())) {
                    throw new AppException("API-PR058", "Sale chanel is required");
                }
                boolean isExistSaleChanel = globalListRepo.isExist(item.getSaleChanel(), Const.GLOBAL_LIST_CODE.SALE_CHANNEL);
                if (!isExistSaleChanel) {
                    lstError.add(item.getSaleChanel());
                    throw new AppException("API-PR059", "Sale chanel not exist", lstError);
                }

                //rate sales man
                if (DataUtil.isNullOrZero(item.getRateSalesMan())) {
                    throw new AppException("API-PR060", "Rate sales man is required");
                }

                //rate broker
                if (DataUtil.isNullOrZero(item.getRateBroker())) {
                    throw new AppException("API-PR061", "Rate broker is required");
                }

                //start date
                if (!DataUtil.isNullOrEmpty(item.getStaDate()) && !DateUtil.isDate(item.getStaDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PR062", "Start date format must be yyyy-mm-dd");
                }

                //end date
                if (!DataUtil.isNullOrEmpty(item.getEndDate()) && !DateUtil.isDate(item.getEndDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PR063", "End date format must be yyyy-mm-dd");
                }
            }
        }

        boolean productIsExist = productRepo.isExist(sdi.getProductCode());
        if (productIsExist) {
            throw new AppException("API-PR028", "product is existed");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product with document">
    private void mappingInsertProductWithDocument(long productId, List<DocumentInsertProductSdi> lstDocument) {
        if (lstDocument != null && lstDocument.size() > 0) {
            for (DocumentInsertProductSdi sdiItem : lstDocument) {
                Optional<Document> optionalDoc = documentRepo.findById(DataUtil.safeToLong(sdiItem.getDocumentId()));

                Document document = new Document();
                if (optionalDoc.isPresent()) {
                    document = optionalDoc.get();
                }

                document.setCode(sdiItem.getCode());
                document.setName(sdiItem.getName());
                document.setUpdateDatetime(new Date());
                document.setUpdateUser("ADMIN");
                if (!DataUtil.isStringNullOrEmpty(sdiItem.getSignDate())) {
                    document.setSignDate(DateUtil.string2Date(sdiItem.getSignDate(), Const.DATE_FORMAT));
                }
                if (!DataUtil.isStringNullOrEmpty(sdiItem.getEffectDate())) {
                    document.setEffectDate(DateUtil.string2Date(sdiItem.getEffectDate(), Const.DATE_FORMAT));
                }
                document.setProductId(productId);

                document = documentRepo.save(document);

                DocumentMapping dm = new DocumentMapping();
                dm.setDocumentId(document.getId());
                dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PRODUCT);
                dm.setMappingObjectId(productId);
                dm.setStatus(Const.STATUS.ACTIVE);
                dm.setCreateUser("ADMIN");
                dm.setCreateDatetime(new Date());

                documentMappingRepo.save(dm);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product with partner">
    private void mappingInsertProductWithPartner(long productId, List<PartnerInsertProductSdi> lstPartner) {
        if (lstPartner != null && lstPartner.size() > 0) {
            for (PartnerInsertProductSdi item : lstPartner) {
                //insert partner_revenue_shared
                PartnerRevenueShared prs = new PartnerRevenueShared();
                prs.setPartnerId(item.getPartnerId());
                prs.setProductId(productId);
                prs.setPartnerPercent(item.getPartnerPercent());
                prs.setViettelPercent(100 - item.getPartnerPercent());
                prs.setShareType(item.getShareType());
                prs.setStatus(Const.STATUS.ACTIVE);
                prs.setDescription(item.getDescription());
                prs.setCreateUser("ADMIN");
                prs.setCreateDatetime(new Date());

                prs = partnerRevenueSharedRepo.save(prs);

                //insert document
                if (DataUtil.isNullOrZero(item.getDocumentId())) return;
                Optional<Document> optionalDoc = documentRepo.findById(item.getDocumentId());

                if (optionalDoc.isPresent()) {
                    Document document = optionalDoc.get();
                    document.setUpdateUser("ADMIN");
                    document.setUpdateDatetime(new Date());
                    document.setPartnerRevenueShareId(prs.getPartnerRevenueSharedId());
                    document.setStatus(Const.STATUS.ACTIVE);

                    documentRepo.save(document);

                    DocumentMapping dm = new DocumentMapping();
                    dm.setDocumentId(document.getId());
                    dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PARTNER_REVENUE_SHARE);
                    dm.setMappingObjectId(prs.getPartnerRevenueSharedId());
                    dm.setStatus(Const.STATUS.ACTIVE);
                    dm.setCreateUser("ADMIN");
                    dm.setCreateDatetime(new Date());

                    documentMappingRepo.save(dm);
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Insert product">
    private ProductInsertSdo insertProduct(ProductInsertSdi sdi) {
        ProductInsertSdo result = new ProductInsertSdo();

        Product productReturn = productRepo.save(sdi.toProduct());

        result.setProductId(productReturn.getProductId());
        result.setProductCode(productReturn.getProductCode());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product with sale expenses">
    private void mappingInsertProductWithSaleExp(Long productId, List<SaleExpensesInsertProductSdi> lstSaleExp) {
        if (lstSaleExp != null && lstSaleExp.size() > 0) {
            for (SaleExpensesInsertProductSdi item : lstSaleExp) {
                ProductSaleExpenses pse = item.toProductSaleExp(productId);

                productSaleExpRepo.save(pse);
            }
        }
    }
    //</editor-fold>

    private void insertGlobalParam(String productGroupId, Long productId){
        GlobalParam paramAccountNo = new GlobalParam();
        paramAccountNo.setType("2");
        paramAccountNo.setCode("SUBSCRIBER.ACCOUNT_NO.FORMAT");
        paramAccountNo.setName("Cau hinh AccountNo");
        paramAccountNo.setValue("<areaCodeProvince>_<productCode>_<customerName>_<seq>");
        paramAccountNo.setDescription("h004_ytehis_dnh_1");
        paramAccountNo.setProductGroupId(productGroupId);
        paramAccountNo.setProductId(productId);
        paramAccountNo.setStatus(Const.STATUS.ACTIVE);
        paramAccountNo.setCreateDate(new Date());
        paramAccountNo.setCreateUser(Const.ADMIN);
        globalParamRepo.save(paramAccountNo);

        GlobalParam paramAppAccountNo = new GlobalParam();
        paramAppAccountNo.setType("2");
        paramAppAccountNo.setCode("SUBSCRIBER.APP_ACCOUNT_NO.FORMAT");
        paramAppAccountNo.setName("Cau hinh format AccountSericeNo");
        paramAppAccountNo.setValue("<areaCodeProvince>_[<medicalNo><medicalPermissionNo>]_admin");
        paramAppAccountNo.setDescription("h004_186836435_admin");
        paramAppAccountNo.setProductGroupId(productGroupId);
        paramAppAccountNo.setProductId(productId);
        paramAppAccountNo.setStatus(Const.STATUS.ACTIVE);
        paramAppAccountNo.setCreateDate(new Date());
        paramAppAccountNo.setCreateUser(Const.ADMIN);
        globalParamRepo.save(paramAppAccountNo);
    }

    //<editor-fold desc="Insert">
    @Override
    public ProductInsertSdo insert(ProductInsertSdi sdi) {
        validProductInsertInput(sdi);
        ProductInsertSdo result = insertProduct(sdi);

        productRepo.mappingInsertProductWithCustomerType(result.getProductId(), sdi.getLstCustomerType());
        mappingInsertProductWithDocument(result.getProductId(), sdi.getLstDocument());
        mappingInsertProductWithPartner(result.getProductId(), sdi.getLstPartner());
        mappingInsertProductWithSaleExp(result.getProductId(), sdi.getLstSalesExpenses());

        insertGlobalParam(sdi.getProductGroupId(), result.getProductId());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Valid product update input">
    private void validProductUpdateInput(ProductUpdateSdi sdi) {
        List<String> lstError = new ArrayList<>();

        if (DataUtil.isStringNullOrEmpty(sdi.getProductId())) {
            throw new AppException("API-PR029", "productId is required");
        }

        if (DataUtil.isStringNullOrEmpty(sdi.getProductGroupId())) {
            throw new AppException("API-PR030", "productGroupId is required");
        }

        if (sdi.getProductGroupId().length() > 10) {
            throw new AppException("API-PR031", "productGroupId length cannot exceed 10 characters");
        }

        if (DataUtil.isNullOrEmpty(sdi.getProductTypeId())) {
            throw new AppException("API-PR032", "productTypeId is required");
        }

        if (sdi.getProductTypeId().length() > 2) {
            throw new AppException("API-PR033", "productType length cannot exceed 2 characters");
        }

        if (DataUtil.isStringNullOrEmpty(sdi.getProductCode())) {
            throw new AppException("API-PR034", "productCode is required");
        }

        if (sdi.getProductCode().length() > 30) {
            throw new AppException("API-PR035", "productCode length cannot exceed 30 characters");
        }
        boolean isExistProductCode = productRepo.isExist(sdi.getProductId(), sdi.getProductCode());
        if (isExistProductCode) {
            throw new AppException("API-PR057", "Product code already exist");
        }

        if (DataUtil.isStringNullOrEmpty(sdi.getProductName())) {
            throw new AppException("API-PR036", "productName is required");
        }

        if (sdi.getProductName().length() > 100) {
            throw new AppException("API-PR037", "productName length cannot exceed 100 characters");
        }

        if (DataUtil.isNullObject(sdi.getServiceBccsId())) {
            throw new AppException("API-PR038", "serviceBccsId is required");
        }

        boolean isExistProductGroup = globalListRepo.isExist(sdi.getProductGroupId(), Const.GLOBAL_LIST_CODE.PRODUCT_GROUP);
        boolean isExistProductType = globalListRepo.isExist(sdi.getProductTypeId(), Const.GLOBAL_LIST_CODE.PRODUCT_TYPE);

        if (!isExistProductGroup) {
            throw new AppException("API-PR040", "productGroupId not exist");
        }

        if (!isExistProductType) {
            throw new AppException("API-PR041", "productTypeId not exist");
        }

        Optional<Product> optionProduct = productRepo.findById(sdi.getProductId());
        if (!optionProduct.isPresent()) {
            throw new AppException("API-PR042", "productId not exist");
        }

        List<String> listCustomerType = sdi.getLstCustomerType();
        for (String item : listCustomerType) {
            if (!DataUtil.isNullOrEmpty(item)) {
                boolean isExistCustomerType = globalListRepo.isExist(item, Const.GLOBAL_LIST_CODE.PRODUCT_CUST_SEGM);

                if (!isExistCustomerType) {
                    lstError.add(item);
                    throw new AppException("API-PR043", "customerType " + item + " not exist", lstError);
                }
            }
        }

        List<DocumentUpdateProductSdi> lstDocument = sdi.getLstDocument();
        if (lstDocument != null && lstDocument.size() > 0) {
            for (DocumentUpdateProductSdi item : lstDocument) {
                if (!DataUtil.isNullOrZero(item.getDocumentId())) {
                    Optional<Document> optionalDoc = documentRepo.findById(item.getDocumentId());

                    if (!optionalDoc.isPresent()) {
                        throw new AppException("API-044", "document not exist");
                    }
                }

                if (DataUtil.isStringNullOrEmpty(item.getCode())) {
                    throw new AppException("API-PR045", "document code is required");
                }

                if (item.getCode().length() > 50) {
                    throw new AppException("API-PR046", "document code length cannot exceed 50 characters");
                }

                if (DataUtil.isStringNullOrEmpty(item.getName())) {
                    throw new AppException("API-PR047", "document name is required");
                }

                if (item.getName().length() > 200) {
                    throw new AppException("API-PR048", "document name length cannot exceed 200 characters");
                }

                if (!DataUtil.isStringNullOrEmpty(item.getSignDate())) {
                    if (!DateUtil.isDate(item.getSignDate(), Const.DATE_FORMAT)) {
                        throw new AppException("API-PR049", "signDate document format must be yyyy-MM-dd");
                    }
                }

                if (!DataUtil.isStringNullOrEmpty(item.getEffectDate())) {
                    if (!DateUtil.isDate(item.getEffectDate(), Const.DATE_FORMAT)) {
                        throw new AppException("API-PR050", "effectDate document format must be yyyy-MM-dd");
                    }
                }
            }
        }

        List<PartnerUpdateProductSdi> lstPartner = sdi.getLstPartner();
        if (lstPartner != null && lstPartner.size() > 0) {
            for (PartnerUpdateProductSdi item : lstPartner) {
                if (!DataUtil.isNullOrZero(item.getDocumentId())) {
                    Optional<Document> optionalDoc = documentRepo.findById(item.getDocumentId());

                    if (!optionalDoc.isPresent()) {
                        throw new AppException("API-051", "document not exist");
                    }
                }

                if (DataUtil.isNullOrZero(item.getPartnerId())) {
                    throw new AppException("API-PR052", "partnerId is required");
                }

                if (DataUtil.isNullObject(item.getPartnerPercent())) {
                    throw new AppException("API-PR053", "partnerPercent is required");
                }

                if (DataUtil.isNullOrEmpty(item.getShareType())) {
                    throw new AppException("API-PR054", "share type is required");
                }

                if (item.getShareType().length() > 10) {
                    throw new AppException("API-PR055", "shareType length cannot exceed 10 characters");
                }

                boolean isExistPartner = partnerRepo.isExist(item.getPartnerId());
                if (!isExistPartner) {
                    throw new AppException("API-PR056", "partnerId not exist");
                }
            }
        }


        List<SaleExpensesUpdateProductSdi> lstSaleExpenses = sdi.getLstSalesExpenses();
        if (lstSaleExpenses != null && lstSaleExpenses.size() > 0) {
            for (SaleExpensesUpdateProductSdi item : lstSaleExpenses) {
                if (!DataUtil.isNullOrZero(item.getProductSaleExpId())) {
                    Optional<ProductSaleExpenses> optionalPSE = productSaleExpRepo.findById(item.getProductSaleExpId());
                    if (!optionalPSE.isPresent()) {
                        lstError.add(String.valueOf(item.getProductSaleExpId()));
                        throw new AppException("API-PR064", "Product sales expenses not exist", lstError);
                    }
                }

                // product id
                if (!DataUtil.isNullOrZero(item.getProductId())) {
                    Optional<Product> optionalProduct = productRepo.findById(item.getProductId());
                    if (!optionalProduct.isPresent()) {
                        lstError.add(String.valueOf(item.getProductId()));
                        throw new AppException("API-CN026", "Product id not exist", lstError);
                    }
                }

                //sale chanel
                if (DataUtil.isNullOrEmpty(item.getSaleChanel())) {
                    throw new AppException("API-PR058", "Sale chanel is required");
                }
                boolean isExistSaleChanel = globalListRepo.isExist(item.getSaleChanel(), Const.GLOBAL_LIST_CODE.SALE_CHANNEL);
                if (!isExistSaleChanel) {
                    lstError.add(item.getSaleChanel());
                    throw new AppException("API-PR059", "Sale chanel not exist", lstError);
                }

                //rate sales man
                if (DataUtil.isNullOrZero(item.getRateSalesMan())) {
                    throw new AppException("API-PR060", "Rate sales man is required");
                }

                //rate broker
                if (DataUtil.isNullOrZero(item.getRateBroker())) {
                    throw new AppException("API-PR061", "Rate broker is required");
                }

                //start date
                if (!DataUtil.isNullOrEmpty(item.getStaDate()) && !DateUtil.isDate(item.getStaDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PR062", "Start date format must be yyyy-mm-dd");
                }

                //end date
                if (!DataUtil.isNullOrEmpty(item.getEndDate()) && !DateUtil.isDate(item.getEndDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PR063", "End date format must be yyyy-mm-dd");
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update product">
    private ProductInsertSdo updateProduct(ProductUpdateSdi sdi) {
        ProductInsertSdo result = new ProductInsertSdo();

        Optional<Product> optionalProduct = productRepo.findById(sdi.getProductId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            product.setProductCode(sdi.getProductCode());
            product.setProductName(sdi.getProductName());
            product.setProductGroupId(sdi.getProductGroupId());
            product.setProductType(sdi.getProductTypeId());
//            product.setTelcoProductCode(sdi.getProductBccsCode());
            product.setTelcoServiceId(sdi.getServiceBccsId());
            product.setGlCode1(sdi.getGlCode1());
            product.setGlCode2(sdi.getGlCode2());
            product.setSalesItemCode(sdi.getSalesItemCode());

            productRepo.save(product);

            result.setProductId(product.getProductId());
            result.setProductCode(product.getProductCode());
        }

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product with customer type">
    private void mappingUpdateProductWithCustomerType(long productId, List<String> customerTypes) {
        //set status inactive product_cust_segm where productId
        productCustSegmRepo.changeStatusByProductId(Const.STATUS.INACTIVE, productId);

        if (customerTypes != null && customerTypes.size() > 0) {
            for (String customerType : customerTypes) {
                ProductCustSegm pcsSearch = productCustSegmRepo.findByValueAndProduct(customerType, productId);

                ProductCustSegm pcs = new ProductCustSegm();


                if (DataUtil.isNullOrZero(pcsSearch.getProductCustSegmId())) {
                    pcs.setGlobalValue(customerType);
                    pcs.setProductId(productId);
                    pcs.setCreateUser("ADMIN");
                    pcs.setCreateDatetime(new Date());
                } else {
                    pcs = pcsSearch;
                    pcs.setUpdateUser("ADMIN");
                    pcs.setUpdateDatetime(new Date());
                }

                pcs.setStatus(Const.STATUS.ACTIVE);

                productCustSegmRepo.save(pcs);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product with document">
    private void mappingUpdateProductWithDocument(long productId, List<DocumentUpdateProductSdi> lstDocument) {
        //change status document_mapping = inactive
        documentMappingRepo.changeChangeStatusByObjectMapId(Const.STATUS.INACTIVE, productId, Const.DOC_OBJ_MAP_TYPE.PRODUCT);

        if (lstDocument != null && lstDocument.size() > 0) {
            for (DocumentUpdateProductSdi sdi : lstDocument) {
                Optional<Document> optionalDoc = documentRepo.findById(sdi.getDocumentId());

                Document document = new Document();

                if (optionalDoc.isPresent()) {
                    document = optionalDoc.get();

                    document.setUpdateUser("ADMIN");
                    document.setUpdateDatetime(new Date());
                } else {
                    document.setCreateUser("ADMIN");
                    document.setCreateDatetime(new Date());
                }

                document.setStatus(Const.STATUS.ACTIVE);
                document.setCode(sdi.getCode());
                document.setName(sdi.getName());

                if (!DataUtil.isNullOrEmpty(sdi.getSignDate())) {
                    document.setSignDate(DateUtil.string2Date(sdi.getSignDate(), Const.DATE_FORMAT));
                }

                if (!DataUtil.isNullOrEmpty(sdi.getEffectDate())) {
                    document.setEffectDate(DateUtil.string2Date(sdi.getEffectDate(), Const.DATE_FORMAT));
                }

                document = documentRepo.save(document);


                DocumentMapping dm = documentMappingRepo.findByDocumentIdAndObjectId(document.getId(), productId, Const.DOC_OBJ_MAP_TYPE.PRODUCT);

                if (dm != null && dm.getDocumentMappingId() != null) {
                    dm.setStatus(Const.STATUS.ACTIVE);
                    dm.setUpdateDatetime(new Date());
                    dm.setUpdateUser("ADMIN");
                } else {
                    dm.setDocumentId(document.getId());
                    dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PRODUCT);
                    dm.setMappingObjectId(productId);
                    dm.setStatus(Const.STATUS.ACTIVE);
                    dm.setCreateUser("ADMIN");
                    dm.setCreateDatetime(new Date());
                }

                documentMappingRepo.save(dm);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product with partner">
    private void mappingUpdateProductWithPartner(long productId, List<PartnerUpdateProductSdi> lstPartner) {
        partnerRevenueSharedRepo.changeStatusByProductId(Const.STATUS.INACTIVE, productId);

        if (lstPartner != null && lstPartner.size() > 0) {
            for (PartnerUpdateProductSdi sdi : lstPartner) {
                //partner revenue shared
                long partnerRevenueSharedId = sdi.getPartnerRevenueSharedId();

                PartnerRevenueShared prs = new PartnerRevenueShared();
                prs.setPartnerId(sdi.getPartnerId());
                prs.setProductId(productId);
                prs.setPartnerPercent(sdi.getPartnerPercent());
                prs.setViettelPercent(100 - sdi.getPartnerPercent());
                prs.setShareType(sdi.getShareType());
                prs.setStatus(Const.STATUS.ACTIVE);
                prs.setDescription(sdi.getDescription());

                if (DataUtil.isNullOrZero(partnerRevenueSharedId)) {
                    //insert
                    prs.setCreateUser("ADMIN");
                    prs.setCreateDatetime(new Date());

                    PartnerRevenueShared prsResultInsert = partnerRevenueSharedRepo.save(prs);

                    partnerRevenueSharedId = prsResultInsert.getPartnerRevenueSharedId();
                } else {
                    // update
                    prs.setPartnerRevenueSharedId(partnerRevenueSharedId);
                    prs.setUpdateUser("ADMIN");
                    prs.setUpdateDatetime(new Date());

                    partnerRevenueSharedRepo.save(prs);
                }


                //document
                documentMappingRepo.changeChangeStatusByObjectMapId(Const.STATUS.INACTIVE, partnerRevenueSharedId, Const.DOC_OBJ_MAP_TYPE.PARTNER_REVENUE_SHARE);

                DocumentMapping dm = documentMappingRepo.findByDocumentIdAndObjectId(sdi.getDocumentId(), partnerRevenueSharedId, Const.DOC_OBJ_MAP_TYPE.PARTNER_REVENUE_SHARE);

                if (dm != null && dm.getDocumentMappingId() != null) {
                    dm.setStatus(Const.STATUS.ACTIVE);
                    dm.setUpdateDatetime(new Date());
                    dm.setUpdateUser("ADMIN");
                } else {
                    dm.setDocumentId(sdi.getDocumentId());
                    dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PARTNER_REVENUE_SHARE);
                    dm.setMappingObjectId(partnerRevenueSharedId);
                    dm.setStatus(Const.STATUS.ACTIVE);
                    dm.setCreateUser("ADMIN");
                    dm.setCreateDatetime(new Date());
                }

                documentMappingRepo.save(dm);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product with sale exp">
    private void mappingUpdateProductWithSaleExp(Long productId, List<SaleExpensesUpdateProductSdi> lstSaleExp) {
        productSaleExpRepo.changeStatusByProduct(Const.STATUS.CANCELED, productId);

        if (lstSaleExp != null && lstSaleExp.size() > 0) {
            for (SaleExpensesUpdateProductSdi item : lstSaleExp) {
                ProductSaleExpenses pse = new ProductSaleExpenses();
                if (DataUtil.isNullOrZero(item.getProductSaleExpId())) {
                    pse = item.toProductSaleExp(productId);
                    productSaleExpRepo.save(pse);
                } else {
                    Optional<ProductSaleExpenses> optionalPSE = productSaleExpRepo.findById(item.getProductSaleExpId());

                    if (optionalPSE.isPresent()) {
                        pse = optionalPSE.get();
                        pse = item.updateProductSaleExp(pse, productId);

                        productSaleExpRepo.save(pse);
                    }
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update">
    @Override
    public ProductInsertSdo update(ProductUpdateSdi sdi) {
        validProductUpdateInput(sdi);
        ProductInsertSdo result = updateProduct(sdi);

        mappingUpdateProductWithCustomerType(result.getProductId(), sdi.getLstCustomerType());
        mappingUpdateProductWithDocument(result.getProductId(), sdi.getLstDocument());
        mappingUpdateProductWithPartner(result.getProductId(), sdi.getLstPartner());
        mappingUpdateProductWithSaleExp(result.getProductId(), sdi.getLstSalesExpenses());

        return result;
    }
    //</editor-fold>

    @Override
    public boolean isExist(long productId) {
        return productRepo.isExist(productId);
    }
}
