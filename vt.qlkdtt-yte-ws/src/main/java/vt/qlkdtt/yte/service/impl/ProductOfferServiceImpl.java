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
import vt.qlkdtt.yte.service.*;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.*;
import vt.qlkdtt.yte.service.sdo.ProOfferProductSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferInsertSdo;
import vt.qlkdtt.yte.service.sdo.ProductOfferSearchSdo;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductOfferServiceImpl implements ProductOfferService {
    @Autowired
    EntityManager em;

    @Autowired
    PartnerRepo partnerRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    GlobalListRepo globalListRepo;

    @Autowired
    DocumentRepoCustom documentRepoCustom;

    @Autowired
    AreaRepo areaRepo;

    @Autowired
    ProductOfferRepo productOfferRepo;

    @Autowired
    ProductOfferAreaRepo productOfferAreaRepo;

    @Autowired
    CustomerPropectiveRepo customerPropectiveRepo;

    @Autowired
    DocumentMappingRepo documentMappingRepo;

    @Autowired
    PartnerRevenueSharedRepo partnerRevenueShareRepo;

    @Autowired
    ProductCustSegmRepo productCustSegmRepo;

    @Autowired
    PartnerRevenueSharedRepo partnerRevenueSharedRepo;

    private void validInputSearch(ProductOfferSearchSdi sdi) {
        //fromDate
        if (!DataUtil.isNullOrEmpty(sdi.getFromDate())) {
            if (!DateUtil.isDate(sdi.getFromDate(), Const.DATE_FORMAT)) {
                throw new AppException("API-PO001", "fromDate format must be yyyy-mm-dd");
            }
        }

        //toDate
        if (!DataUtil.isNullOrEmpty(sdi.getFromDate())) {
            if (!DateUtil.isDate(sdi.getFromDate(), Const.DATE_FORMAT)) {
                throw new AppException("API-PO002", "toDate format must be yyyy-mm-dd");
            }
        }
    }

    @Override
    public Page<ProductOfferSearchSdo> searchProductOffer(ProductOfferSearchSdi sdi, Pageable pageRequest) {
        validInputSearch(sdi);
        return productOfferRepo.searchProductOffer(sdi, pageRequest);
    }

    private void validInputSearchConnect(Long productId, String provisionType){
        if (DataUtil.isNullOrZero(productId)) {
            throw new AppException("API-PO076", "Product id is required");
        }
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new AppException("API-PR042", "Product id not exist");
        }

        if (DataUtil.isNullOrEmpty(provisionType)) {
            throw new AppException("API-PO077", "Provision type is required");
        }
        boolean isExist = globalListRepo.isExist(provisionType, Const.GLOBAL_LIST_CODE.PROVISION_TYPE);
        if (!isExist) {
            throw new AppException("API-PO078", "Provision type not exist");
        }
    }

    @Override
    public List<ProOfferProductSearchConnectSdo> searchProductOfferConnect(Long productId, String provisionType) {
        validInputSearchConnect(productId, provisionType);

        return productOfferRepo.searchProductOfferConnect(productId, provisionType);
    }

    @Override
    public ProductOfferFindByIdSdo findById(long productOfferId) {
        return productOfferRepo.findProductOfferById(productOfferId);
    }

    @Override
    public boolean changeStatus(long id, String status) {
        return productOfferRepo.changeStatus(id, status);
    }

    //<editor-fold desc="valid input insert">
    private void validInputInsert(ProductOfferInsertSdi dataInsert) {
        List<String> listError = new ArrayList<>();

        //check productId
        if (DataUtil.isNullOrZero(dataInsert.getProductId())) {
            throw new AppException("API-PO003", "productId is required");
        }

        boolean isExistProductId = productRepo.isExist(dataInsert.getProductId());
        if (!isExistProductId) {
            throw new AppException("API-PO004", "productId not exist");
        }

        //check productOfferCode
        if (!DataUtil.isStringNullOrEmpty(dataInsert.getProductOfferCode())) {
            boolean isExistProductOfferCode = productOfferRepo.isExist(dataInsert.getProductOfferCode());
            if (isExistProductOfferCode) {
                throw new AppException("API-PO005", "productOfferCode already exist");
            }

            if (dataInsert.getProductOfferCode().length() > 50) {
                throw new AppException("API-PO006", "productOfferCode length cannot exceed 50 characters");
            }
        }

        //product offer name
        if (!DataUtil.isNullOrEmpty(dataInsert.getProductOfferName())) {
            if (dataInsert.getProductOfferName().length() > 255) {
                throw new AppException("API-PO007", "productOfferName length cannot exceed 255 characters");
            }
        }

        //accounting code
        if (!DataUtil.isNullOrEmpty(dataInsert.getAccountingCode())) {
            if (dataInsert.getAccountingCode().length() > 50) {
                throw new AppException("API-PO008", "accountingCode length cannot exceed 50 characters");
            }
        }

        //check telco product offer id
        if (!DataUtil.isNullOrEmpty(dataInsert.getTelcoProductOfferId()) && dataInsert.getTelcoProductOfferId().length() > 10) {
            throw new AppException("API-PO068", "telcoProductOfferId length cannot exceed 10 characters");
        }

        //check productOfferType
        if (!DataUtil.isNullOrEmpty(dataInsert.getProductOfferType())) {
            boolean isExistProductOfferType = globalListRepo.isExist(dataInsert.getProductOfferType(), Const.GLOBAL_LIST_CODE.PRODUCT_OFFER_TYPE);
            if (!isExistProductOfferType) {
                throw new AppException("API-PO010", "productOfferType not exist");
            }

            if (dataInsert.getProductOfferType().length() > 2) {
                throw new AppException("API-PO011", "productOfferType length cannot exceed 2 characters");
            }
        }

        //check priceBase, priceMin, priceMax
        if (DataUtil.isNullObject(dataInsert.getUnitPriceBase())) {
            throw new AppException("API-PO069", "Price base is required");
        }
        if (DataUtil.isNullObject(dataInsert.getUnitPriceMin())) {
            throw new AppException("API-PO070", "Price min is required");
        }
        if (DataUtil.isNullObject(dataInsert.getUnitPriceMin())) {
            throw new AppException("API-PO071", "Price max is required");
        }
        if (dataInsert.getProductOfferType().equals(Const.PRODUCT_OFFER_TYPE.ACCORDING_PRICE_BRACKET)) {
            if (DataUtil.isNullOrZero(dataInsert.getUnitPriceMax()) && dataInsert.getUnitPriceMax() < dataInsert.getUnitPriceMin()) {
                throw new AppException("API-PO072", "Price max must not be smaller than price min");
            }
            if (dataInsert.getUnitPriceBase() < dataInsert.getUnitPriceMin() || dataInsert.getUnitPriceBase() > dataInsert.getUnitPriceMax()) {
                throw new AppException("API-PO073", "Price base must be between price min and price max");
            }
        }

        //check vat type
        if (!DataUtil.isStringNullOrEmpty(dataInsert.getVatType())) {
            boolean isExistVatType = globalListRepo.isExist(dataInsert.getVatType(), Const.GLOBAL_LIST_CODE.VAT_TYPE);
            if (!isExistVatType) {
                throw new AppException("API-PO012", "vatType not exist");
            }

            if (dataInsert.getVatType().length() > 2) {
                throw new AppException("API-PO013", "vatType length cannot exceed 2 characters");
            }
        }


        //check vol limitation type
        if (!DataUtil.isStringNullOrEmpty(dataInsert.getVolLimitType())) {
            boolean isExistVolLimitType = globalListRepo.isExist(dataInsert.getVolLimitType(), Const.GLOBAL_LIST_CODE.VOL_LIMITATION_TYPE);
            if (!isExistVolLimitType) {
                throw new AppException("API-PO014", "volLimitType not exist");
            }

            if (dataInsert.getVolLimitType().length() > 2) {
                throw new AppException("API-PO015", "volLimitType length cannot exceed 2 characters");
            }
        }

        //check status
        if (!dataInsert.getStatus().equals(Const.STATUS.ACTIVE) && !dataInsert.getStatus().equals(Const.STATUS.INACTIVE)) {
            throw new AppException("API-PO016", "status product offer must be 0 or 1");
        }

        //check format staDate, endDate
        if (!DataUtil.isStringNullOrEmpty(dataInsert.getStaDate())) {
            if (!DateUtil.isDate(dataInsert.getStaDate(), Const.DATE_FORMAT)) {
                throw new AppException("API-PO017", "staDate product offer format must be yyyy-mm-dd");
            }
        }
        if (!DataUtil.isStringNullOrEmpty(dataInsert.getEndDate())) {
            if (!DateUtil.isDate(dataInsert.getEndDate(), Const.DATE_FORMAT)) {
                throw new AppException("API-PO018", "endDate product offer format must be yyyy-mm-dd");
            }
        }

        //check documentId
        if (!DataUtil.isNullOrZero(dataInsert.getDocumentId())) {
            boolean isExistDocumentId = documentRepoCustom.isExist(dataInsert.getDocumentId());
            if (!isExistDocumentId) {
                throw new AppException("API-PO019", "documentId not exist");
            }
        }

        //hình thức cung cấp
        if (DataUtil.isNullOrEmpty(dataInsert.getProvisionType())) {
            throw new AppException("API-PO074", "Provision type is required");
        }
        boolean isExistProvisionType = globalListRepo.isExist(dataInsert.getProvisionType(), Const.GLOBAL_LIST_CODE.PROVISION_TYPE);
        if (!isExistProvisionType) {
            listError.add(dataInsert.getProvisionType());
            throw new AppException("API-PO075", "Provision type " + dataInsert.getProvisionType() + " not exist", listError);
        }

        //mã tài chính
        if (!DataUtil.isNullOrEmpty(dataInsert.getGlCode1()) && dataInsert.getGlCode1().length() > 50) {
            throw new AppException("API-PO079", "GL CODE 1 length cannot exceed 50 characters");
        }

        //mã vụ việc tài chính
        if (!DataUtil.isNullOrEmpty(dataInsert.getGlCode2()) && dataInsert.getGlCode2().length() > 50) {
            throw new AppException("API-PO080", "GL CODE 2 length cannot exceed 50 characters");
        }

        //mã sản phẩm
        if (!DataUtil.isNullOrEmpty(dataInsert.getSalesItemCode()) && dataInsert.getSalesItemCode().length() > 50) {
            throw new AppException("API-PO081", "GL CODE 1 length cannot exceed 50 characters");
        }

        //Danh sách đối tượng khách hàng
        for (String item : dataInsert.getListCustomerType()) {
            boolean isExistCustomerType = globalListRepo.isExist(item, Const.GLOBAL_LIST_CODE.PRODUCT_CUST_SEGM);
            listError.add(item);

            if (!isExistCustomerType) {
                throw new AppException("API-PO020", "customerType " + item + " not exist", listError);
            }
        }

        //Danh sách địa bàn áp dụng
        for (PoaInsertProductOfferSdi item : dataInsert.getListProductOfferArea()) {
            if (!DataUtil.isNullOrEmpty(item.getAreaCodeProvince())) {
                boolean isExistAreaCodeProvince = areaRepo.isExist(item.getAreaCodeProvince());
                List<String> lstError = new ArrayList<>();
                lstError.add(item.getAreaCodeProvince());

                if (!isExistAreaCodeProvince) {
                    throw new AppException("API-PO021", "areaCodeProvince " + item.getAreaCodeProvince() + " not exist");
                }
            }

            if (!DataUtil.isNullOrEmpty(item.getAreaCodeDistrict())) {
                boolean isExistAreaCodeDistrict = areaRepo.isExist(item.getAreaCodeDistrict());
                List<String> lstError = new ArrayList<>();
                lstError.add(item.getAreaCodeDistrict());

                if (!isExistAreaCodeDistrict) {
                    throw new AppException("API-PO022", "areaCodeDistrict " + item.getAreaCodeDistrict() + " not exist", lstError);
                }
            }
        }

        //Danh sách khách hàng áp dụng
        for (CustomerPropectInsertProductOffer item : dataInsert.getListProductOfferCustomer()) {
            //customer name
            if (!DataUtil.isNullOrEmpty(item.getCustomerName())) {
                if (item.getCustomerName().length() > 255) {
                    throw new AppException("API-PO023", "volLimitType length cannot exceed 255 characters");
                }
            }

            //customer identity no
            if (!DataUtil.isNullOrEmpty(item.getCustomerIdentityNo())) {
                if (item.getCustomerIdentityNo().length() > 30) {
                    throw new AppException("API-PO024", "customerIdentityNo length cannot exceed 30 characters");
                }
            }

            //medical permission no
            if (!DataUtil.isNullOrEmpty(item.getMedicalNo())) {
                if (item.getMedicalNo().length() > 50) {
                    throw new AppException("API-PO025", "medicalPermissionNo length cannot exceed 50 characters");
                }
            }

            //check format staDate, endDate
            if (!DataUtil.isNullOrEmpty(item.getStaDate())) {
                if (!DateUtil.isDate(item.getStaDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PO026", "staDate customer propective format must be yyyy-mm-dd");
                }
            }

            if (!DataUtil.isNullOrEmpty(item.getDueDate())) {
                if (!DateUtil.isDate(item.getDueDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PO027", "dueDate customer propective format must be yyyy-mm-dd");
                }
            }

            //check status
            if (!item.getStatus().equals(Const.STATUS.ACTIVE) && !item.getStatus().equals(Const.STATUS.INACTIVE)) {
                throw new AppException("API-PO028", "status product offer customer must be 0 or 1");
            }
        }

        //Danh sách chia sẻ với đối tác đặc thù
        for (PartnerShareInsertProductOfferSdi item : dataInsert.getListPartnerRevenueShareSpecific()) {
            if (DataUtil.isNullOrZero(item.getPartnerId())) {
                throw new AppException("API-PO029", "partnerId is required");
            }

            boolean isExistPartner = partnerRepo.isExist(item.getPartnerId());
            if (!isExistPartner) {
                List<String> lstError = new ArrayList<>();
                lstError.add(DataUtil.safeToString(item.getPartnerId()));

                throw new AppException("API-PO030", "partnerId " + item.getPartnerId() + " not exist", lstError);
            }

            if (DataUtil.isNullObject(item.getPartnerPercent())) {
                throw new AppException("API-PO031", "partnerPercent is required");
            }

            if (!DataUtil.isNullOrZero(item.getDocumentId())) {
                boolean isExistDocument = documentRepoCustom.isExist(item.getDocumentId());

                if (!isExistDocument) {
                    List<String> lstError = new ArrayList<>();
                    lstError.add(DataUtil.safeToString(item.getDocumentId()));

                    throw new AppException("API-PO032", "documentId " + item.getDocumentId() + " not exist", lstError);
                }
            }

            if (!DataUtil.isStringNullOrEmpty(item.getShareType())) {
                boolean isExistShareType = globalListRepo.isExist(item.getShareType(), Const.GLOBAL_LIST_CODE.PARTNER_SHARE_TYPE);

                if (!isExistShareType) {
                    List<String> lstError = new ArrayList<>();
                    lstError.add(DataUtil.safeToString(item.getShareType()));

                    throw new AppException("API-PO033", "shareType " + item.getShareType() + " not exist", lstError);
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Insert product offer">
    private ProductOfferInsertSdo insertProductOffer(ProductOfferInsertSdi dataInsert) {
        ProductOfferInsertSdo result = new ProductOfferInsertSdo();

        ProductOffer productOffer = dataInsert.toProductOffer();

        productOffer = productOfferRepo.save(productOffer);

        result.setProductOfferId(productOffer.getProductOfferId());
        result.setProductOfferCode(productOffer.getProductOfferCode());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product offer with area">
    private void mappingInsertProductOfferWithArea(List<PoaInsertProductOfferSdi> listProductOfferArea, long productOfferId) {
        for (PoaInsertProductOfferSdi item : listProductOfferArea) {
            ProductOfferArea poa = new ProductOfferArea();
            poa.setProductOfferId(productOfferId);

            String areaCode = null;

            if (!DataUtil.isNullOrEmpty(item.getAreaCodePrecinct())) {
                areaCode = item.getAreaCodePrecinct();
            } else if (!DataUtil.isNullOrEmpty(item.getAreaCodeDistrict())) {
                areaCode = item.getAreaCodeDistrict();
            } else {
                areaCode = item.getAreaCodeProvince();
            }

            poa.setAreaCode(areaCode);
            poa.setCreateUser("ADMIN");
            poa.setCreateDatetime(new Date());
            poa.setStatus(Const.STATUS.ACTIVE);

            productOfferAreaRepo.save(poa);

            if (DataUtil.isNullOrEmpty(item.getAreaCodeProvince())) break;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product offer with customer">
    private void mappingInsertProductOfferWithCustomerPropective(List<CustomerPropectInsertProductOffer> listCustomerPropective, long productOfferId, Long productId) {
        for (CustomerPropectInsertProductOffer item : listCustomerPropective) {
            CustomerPropective cp = new CustomerPropective();
            cp.setProductOfferId(productOfferId);
            cp.setProductId(productId);
            cp.setCustomerBusType(item.getCustomerBusType());
            cp.setIdentityType(item.getCustomerIdentityType());
            cp.setIdentityNo(item.getCustomerIdentityNo());
            cp.setMedicalNo(item.getMedicalNo());
            cp.setProvince(item.getProvinceCode());
            cp.setDistrict(item.getDistrictCode());
            cp.setStatus(item.getStatus());
            cp.setCustomerName(item.getCustomerName());
            cp.setCreateUser("ADMIN");
            cp.setCreateDatetime(new Date());
            cp.setStaDatetime(DateUtil.string2Date(item.getStaDate(), Const.DATE_FORMAT));
            cp.setDueDate(DateUtil.string2Date(item.getDueDate(), Const.DATE_FORMAT));

            customerPropectiveRepo.save(cp);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping insert product offer with document">
    private void mappingInsertProductOfferWithDocument(long productOfferId, long documentId) {
        if (DataUtil.isNullOrZero(documentId)) return;

        DocumentMapping dm = new DocumentMapping();
        dm.setDocumentId(documentId);
        dm.setMappingObjectId(productOfferId);
        dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PRODUCT_OFFER);
        dm.setCreateUser("ADMIN");
        dm.setCreateDatetime(new Date());
        dm.setStatus(Const.STATUS.ACTIVE);

        documentMappingRepo.save(dm);
    }
    //</editor-fold>

    //<editor-fold desc="Mapping product offer with partner">
    private void mappingInsertProductOfferWithPartner(List<PartnerShareInsertProductOfferSdi> listPartnerShareSpecific, long productOfferId) {
        for (PartnerShareInsertProductOfferSdi item : listPartnerShareSpecific) {
            PartnerRevenueShared prs = item.toPrs(productOfferId);

            prs = partnerRevenueShareRepo.save(prs);

            if (!DataUtil.isNullOrZero(item.getDocumentId())) {
                DocumentMapping dm = item.toDocumentMapping(prs.getPartnerRevenueSharedId());

                documentMappingRepo.save(dm);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Create product">
    @Override
    public ProductOfferInsertSdo createProductOffer(ProductOfferInsertSdi dataInsert) {
        validInputInsert(dataInsert);
        ProductOfferInsertSdo result = insertProductOffer(dataInsert);

        productOfferRepo.mappingInsertProductOfferWithCustomerType(dataInsert.getListCustomerType(), result.getProductOfferId());
        mappingInsertProductOfferWithArea(dataInsert.getListProductOfferArea(), result.getProductOfferId());
        mappingInsertProductOfferWithCustomerPropective(dataInsert.getListProductOfferCustomer(), result.getProductOfferId(), dataInsert.getProductId());
        mappingInsertProductOfferWithDocument(result.getProductOfferId(), dataInsert.getDocumentId());
        mappingInsertProductOfferWithPartner(dataInsert.getListPartnerRevenueShareSpecific(), result.getProductOfferId());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Valid input update">
    private void validInputUpdate(ProductOfferUpdateSdi dataUpdate) {
        List<String> listError = new ArrayList<>();

        //check productOfferId
        boolean isExistProductOfferId = productOfferRepo.isExist(dataUpdate.getProductOfferId());
        if (!isExistProductOfferId) {
            throw new AppException("API-PO034", "productOfferId not exist");
        }

        //check productId
        boolean isExistProductId = productRepo.isExist(dataUpdate.getProductId());
        if (!isExistProductId) {
            throw new AppException("API-PO035", "productId not exist");
        }

        //check productOfferCode
        if (!DataUtil.isNullOrEmpty(dataUpdate.getProductOfferCode())) {
            boolean isExistProductOfferCode = productOfferRepo.isExist(dataUpdate.getProductOfferCode(), dataUpdate.getProductOfferId());
            if (isExistProductOfferCode) {
                throw new AppException("API-PO036", "productOfferCode already exist");
            }
            if (dataUpdate.getProductOfferCode().length() > 50) {
                throw new AppException("API-PO037", "productOfferCode length cannot exceed 50 characters");
            }
        }

        //product offer name
        if (!DataUtil.isNullOrEmpty(dataUpdate.getProductOfferName())) {
            if (dataUpdate.getProductOfferName().length() > 255) {
                throw new AppException("API-PO038", "productOfferName length cannot exceed 255 characters");
            }
        }

        //accounting code
        if (!DataUtil.isNullOrEmpty(dataUpdate.getAccountingCode())) {
            if (dataUpdate.getAccountingCode().length() > 50) {
                throw new AppException("API-PO039", "accountingCode length cannot exceed 50 characters");
            }
        }

        //check productOfferType
        if (!DataUtil.isNullOrEmpty(dataUpdate.getProductOfferType())) {
            boolean isExistProductOfferType = globalListRepo.isExist(dataUpdate.getProductOfferType(), Const.GLOBAL_LIST_CODE.PRODUCT_OFFER_TYPE);
            if (!isExistProductOfferType) {
                throw new AppException("API-PO040", "productOfferType not exist");
            }

            if (dataUpdate.getProductOfferType().length() > 2) {
                throw new AppException("API-PO041", "productOfferType length cannot exceed 2 characters");
            }
        }


        //check vat type
        if (!DataUtil.isNullOrEmpty(dataUpdate.getVatType())) {
            boolean isExistVatType = globalListRepo.isExist(dataUpdate.getVatType(), Const.GLOBAL_LIST_CODE.VAT_TYPE);
            if (!isExistVatType) {
                throw new AppException("API-PO042", "vatType not exist");
            }
            if (dataUpdate.getVatType().length() > 2) {
                throw new AppException("API-PO043", "vatType length cannot exceed 2 characters");
            }
        }

        //check vol limitation type
        if (!DataUtil.isNullOrEmpty(dataUpdate.getVolLimitType())) {
            boolean isExistVolLimitType = globalListRepo.isExist(dataUpdate.getVolLimitType(), Const.GLOBAL_LIST_CODE.VOL_LIMITATION_TYPE);
            if (!isExistVolLimitType) {
                throw new AppException("API-PO044", "volLimitType not exist");
            }
            if (dataUpdate.getVolLimitType().length() > 2) {
                throw new AppException("API-PO045", "volLimitType length cannot exceed 2 characters");
            }
        }


        //check status
        if (!dataUpdate.getStatus().equals(Const.STATUS.ACTIVE) && !dataUpdate.getStatus().equals(Const.STATUS.INACTIVE)) {
            throw new AppException("API-PO046", "status product offer must be 0 or 1");
        }

        //check format staDate, endDate
        if (!DataUtil.isNullOrEmpty(dataUpdate.getStaDate())) {
            if (!DateUtil.isDate(dataUpdate.getStaDate(), Const.DATE_FORMAT)) {
                throw new AppException("API-PO047", "staDate product offer format must be yyyy-mm-dd");
            }
        }
        if (!DataUtil.isNullOrEmpty(dataUpdate.getEndDate())) {
            if (!DateUtil.isDate(dataUpdate.getEndDate(), Const.DATE_FORMAT)) {
                throw new AppException("API-PO048", "endDate product offer format must be yyyy-mm-dd");
            }
        }

        //check documentId exist
        if (!DataUtil.isNullOrZero(dataUpdate.getDocumentId())) {
            boolean isExistDocumentId = documentRepoCustom.isExist(dataUpdate.getDocumentId());
            if (!isExistDocumentId) {
                throw new AppException("API-PO049", "documentId not exist");
            }
        }

        //hình thức cung cấp
        if (DataUtil.isNullOrEmpty(dataUpdate.getProvisionType())) {
            throw new AppException("API-PO074", "Provision type is required");
        }
        boolean isExistProvisionType = globalListRepo.isExist(dataUpdate.getProvisionType(), Const.GLOBAL_LIST_CODE.PROVISION_TYPE);
        if (!isExistProvisionType) {
            listError.add(dataUpdate.getProvisionType());
            throw new AppException("API-PO075", "Provision type " + dataUpdate.getProvisionType() + " not exist", listError);
        }

        //mã tài chính
        if (!DataUtil.isNullOrEmpty(dataUpdate.getGlCode1()) && dataUpdate.getGlCode1().length() > 50) {
            throw new AppException("API-PO079", "GL CODE 1 length cannot exceed 50 characters");
        }

        //mã vụ việc tài chính
        if (!DataUtil.isNullOrEmpty(dataUpdate.getGlCode2()) && dataUpdate.getGlCode2().length() > 50) {
            throw new AppException("API-PO080", "GL CODE 2 length cannot exceed 50 characters");
        }

        //mã sản phẩm
        if (!DataUtil.isNullOrEmpty(dataUpdate.getSalesItemCode()) && dataUpdate.getSalesItemCode().length() > 50) {
            throw new AppException("API-PO081", "GL CODE 1 length cannot exceed 50 characters");
        }

        //Danh sách đối tượng khách hàng
        for (String item : dataUpdate.getListCustomerType()) {
            boolean isExistCustomerType = globalListRepo.isExist(item, Const.GLOBAL_LIST_CODE.PRODUCT_CUST_SEGM);

            if (!isExistCustomerType) {
                throw new AppException("API-PO050", "customerType " + item + " not exist");
            }
        }

        //Danh sách địa bàn áp dụng
        for (ProductOfferAreaUpdateProductOffer item : dataUpdate.getListProductOfferArea()) {
            // check productOfferAreaId exist
            if (!DataUtil.isNullOrZero(item.getProductOfferAreaId())) {
                boolean isExistProductOfferAreaId = productOfferAreaRepo.isExist(item.getProductOfferAreaId());
                if (!isExistProductOfferAreaId) {
                    throw new AppException("API-PO051", "productOfferAreaId not exist");
                }
            }

            if (!DataUtil.isNullOrEmpty(item.getAreaCodeProvince())) {
                boolean isExistAreaCodeProvince = areaRepo.isExist(item.getAreaCodeProvince());

                if (!isExistAreaCodeProvince) {
                    listError.add(DataUtil.safeToString(item.getAreaCodeProvince()));

                    throw new AppException("API-PO052", "areaCodeProvince " + item.getAreaCodeProvince() + " not exist", listError);
                }
            }

            if (!DataUtil.isNullOrEmpty(item.getAreaCodeDistrict())) {
                boolean isExistAreaCodeDistrict = areaRepo.isExist(item.getAreaCodeDistrict());

                if (!isExistAreaCodeDistrict) {
                    List<String> lstError = new ArrayList<>();
                    lstError.add(DataUtil.safeToString(item.getAreaCodeDistrict()));

                    throw new AppException("API-PO053", "areaCodeDistrict " + item.getAreaCodeDistrict() + " not exist", lstError);
                }
            }
        }

        //Danh sách khách hàng áp dụng
        for (CustomerPropectUpdateProductOffer item : dataUpdate.getListProductOfferCustomer()) {
            //check customerPropectiveId exist
            if (!DataUtil.isNullOrZero(item.getCustomerPropectiveId())) {
                boolean isExistCustomerPropectiveId = customerPropectiveRepo.isExist(item.getCustomerPropectiveId());
                if (!isExistCustomerPropectiveId) {
                    throw new AppException("API-PO054", "customerPropectiveId not exist");
                }
            }

            //customer name
            if (!DataUtil.isNullOrEmpty(item.getCustomerName())) {
                if (item.getCustomerName().length() > 255) {
                    throw new AppException("API-PO055", "volLimitType length cannot exceed 255 characters");
                }
            }

            //customer identity no
            if (!DataUtil.isNullOrEmpty(item.getCustomerIdentityNo())) {
                if (item.getCustomerIdentityNo().length() > 30) {
                    throw new AppException("API-PO056", "customerIdentityNo length cannot exceed 30 characters");
                }
            }

            //medical permission no
            if (!DataUtil.isNullOrEmpty(item.getMedicalNo())) {
                if (item.getMedicalNo().length() > 50) {
                    throw new AppException("API-PO057", "medicalPermissionNo length cannot exceed 50 characters");
                }
            }

            //check format staDate, endDate
            if (!DataUtil.isNullOrEmpty(item.getStaDate())) {
                if (!DateUtil.isDate(item.getStaDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PO058", "staDate customer propective format must be yyyy-mm-dd");
                }
            }

            if (!DataUtil.isNullOrEmpty(item.getDueDate())) {
                if (!DateUtil.isDate(item.getDueDate(), Const.DATE_FORMAT)) {
                    throw new AppException("API-PO059", "dueDate customer propective format must be yyyy-mm-dd");
                }
            }


            //check status
            if (!item.getStatus().equals(Const.STATUS.ACTIVE) && !item.getStatus().equals(Const.STATUS.INACTIVE)) {
                throw new AppException("API-PO060", "status customer propective must be 0 or 1");
            }
        }

        //Danh sách chia sẻ với đối tác đặc thù
        for (PartnerShareUpdateProductOfferSdi item : dataUpdate.getListPartnerRevenueShareSpecific()) {
            if (!DataUtil.isNullOrZero(item.getPartnerRevenueSharedId())) {
                boolean isExistPrs = partnerRevenueSharedRepo.isExist(item.getPartnerRevenueSharedId());

                if (!isExistPrs) {
                    List<String> lstError = new ArrayList<>();
                    lstError.add(DataUtil.safeToString(item.getPartnerRevenueSharedId()));

                    throw new AppException("API-PO061", "partnerRevenueSharedId " + item.getPartnerRevenueSharedId() + " not exist", lstError);
                }
            }

            if (DataUtil.isNullOrZero(item.getPartnerId())) {
                throw new AppException("API-PO062", "partnerId is required");
            }

            boolean isExistPartner = partnerRepo.isExist(item.getPartnerId());
            if (!isExistPartner) {
                List<String> lstError = new ArrayList<>();
                lstError.add(DataUtil.safeToString(item.getPartnerId()));

                throw new AppException("API-PO063", "partnerId " + item.getPartnerId() + " not exist", lstError);
            }

            if (DataUtil.isNullObject(item.getPartnerPercent())) {
                throw new AppException("API-PO064", "partnerPercent is required");
            }

            if (!DataUtil.isNullOrZero(item.getDocumentMappingId())) {
                boolean isExistDocMapping = documentMappingRepo.isExist(item.getDocumentMappingId());

                if (!isExistDocMapping) {
                    List<String> lstError = new ArrayList<>();
                    lstError.add(DataUtil.safeToString(item.getDocumentMappingId()));

                    throw new AppException("API-PO065", "documentMappingId " + item.getDocumentMappingId() + " not exist", lstError);
                }
            }

            if (!DataUtil.isNullOrZero(item.getDocumentId()) || !DataUtil.isNullOrZero(item.getDocumentMappingId())) {
                boolean isExistDocument = documentRepoCustom.isExist(item.getDocumentId());

                if (!isExistDocument) {
                    List<String> lstError = new ArrayList<>();
                    lstError.add(DataUtil.safeToString(item.getDocumentId()));

                    throw new AppException("API-PO066", "documentId " + item.getDocumentId() + " not exist", lstError);
                }
            }

            if (!DataUtil.isStringNullOrEmpty(item.getShareType())) {
                boolean isExistShareType = globalListRepo.isExist(item.getShareType(), Const.GLOBAL_LIST_CODE.PARTNER_SHARE_TYPE);

                if (!isExistShareType) {
                    List<String> lstError = new ArrayList<>();
                    lstError.add(DataUtil.safeToString(item.getShareType()));

                    throw new AppException("API-PO067", "shareType " + item.getShareType() + " not exist", lstError);
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product offer with customer type">
    private void mappingUpdateProductOfferWithCustomerType(List<String> listCustomerTypeId, long productOfferId) {
        productCustSegmRepo.changeStatusByProductOfferId(Const.STATUS.INACTIVE, productOfferId);

        for (String customerType : listCustomerTypeId) {
            ProductCustSegm pcsSearch = productCustSegmRepo.findByValueAndProductOffer(customerType, productOfferId);

            ProductCustSegm pcs = new ProductCustSegm();

            if (DataUtil.isNullOrZero(pcsSearch.getProductCustSegmId())) {
                pcs.setGlobalValue(customerType);
                pcs.setProductOfferId(productOfferId);
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
    //</editor-fold>

    //<editor-fold desc="Mapping update product offer with area">
    private void mappingUpdateProductOfferWithArea(List<ProductOfferAreaUpdateProductOffer> listProductOfferArea, long productOfferId) {
        boolean isNationWide = productOfferAreaRepo.isNationWide(productOfferId);

        //Thay đổi trạng thái các ProductOfferArea về inactive theo productOfferId
        productOfferAreaRepo.changeStatusByProductOffer(Const.STATUS.CANCELED, productOfferId);

        if (isNationWide) {
            //đang là toàn quốc
            if (listProductOfferArea.size() > 1) {
                for (ProductOfferAreaUpdateProductOffer item : listProductOfferArea) {
                    if (!DataUtil.isNullOrEmpty(item.getAreaCodeProvince())) {
                        ProductOfferArea poa = item.toProductOfferArea(productOfferId);
                        productOfferAreaRepo.save(poa);
                    }
                }
            } else if (listProductOfferArea.size() == 1) {
                ProductOfferArea poa = listProductOfferArea.get(0).toProductOfferArea(productOfferId);
                productOfferAreaRepo.save(poa);
            }
        } else {
            // không phải toàn quốc
            for (ProductOfferAreaUpdateProductOffer item : listProductOfferArea) {
                if (DataUtil.isNullOrEmpty(item.getAreaCodeProvince()) && DataUtil.isNullOrEmpty(item.getAreaCodeDistrict())) {
                    productOfferAreaRepo.changeStatusByProductOffer(Const.STATUS.CANCELED, productOfferId);

                    ProductOfferArea poa = item.toProductOfferArea(productOfferId);
                    productOfferAreaRepo.save(poa);

                    return;
                } else {
                    ProductOfferArea poa = item.toProductOfferArea(productOfferId);
                    productOfferAreaRepo.save(poa);
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product offer with customer">
    private void mappingUpdateProductOfferWithCustomerPropective(List<CustomerPropectUpdateProductOffer> listCustomerPropective, long productOfferId, Long productId) {
        //Thay đổi trạng thái customer propective thành inactive theo productOfferId
        customerPropectiveRepo.changeStatusByProductOffer(Const.STATUS.INACTIVE, productOfferId);

        for (CustomerPropectUpdateProductOffer item : listCustomerPropective) {
            CustomerPropective cp = new CustomerPropective();

            if (DataUtil.isNullOrZero(item.getCustomerPropectiveId())) {
                //insert
                cp = item.toCustomerPropective(productOfferId, productId);
                cp.setProductOfferId(productOfferId);
            } else {
                //update
                Optional<CustomerPropective> optionalCP = customerPropectiveRepo.findById(item.getCustomerPropectiveId());

                if (optionalCP.isPresent()) {
                    cp = optionalCP.get();
                    cp = item.updateCustomerPropective(cp, productId);
                }
            }

            customerPropectiveRepo.save(cp);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product offer with document">
    private void mappingUpdateProductOfferWithDocument(Long productOfferId, Long documentId) {
        //change status thành inactive theo productOfferI và type mapping 'PRODUCT_OFFER'
        documentMappingRepo.changeChangeStatusByObjectMapId(Const.STATUS.INACTIVE, productOfferId, Const.DOC_OBJ_MAP_TYPE.PRODUCT_OFFER);

        if (DataUtil.isNullOrZero(documentId)) return;

        DocumentMapping dm = documentMappingRepo.findByDocumentIdAndObjectId(documentId, productOfferId, Const.DOC_OBJ_MAP_TYPE.PRODUCT_OFFER);

        if (DataUtil.isNullOrZero(dm.getDocumentMappingId())) {
            //insert
            dm.setDocumentId(documentId);
            dm.setMappingObjectId(productOfferId);
            dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.PRODUCT_OFFER);
            dm.setCreateUser("ADMIN");
            dm.setCreateDatetime(new Date());
        } else {
            //update
            dm.setUpdateUser("ADMIN");
            dm.setUpdateDatetime(new Date());
        }

        dm.setStatus(Const.STATUS.ACTIVE);

        documentMappingRepo.save(dm);
    }
    //</editor-fold>

    //<editor-fold desc="Mapping update product ofer with partner">
    private void mappingUpdateProductOfferWithPartner(List<PartnerShareUpdateProductOfferSdi> listPartnerShareSpecific, long productOfferId) {
        partnerRevenueShareRepo.changeStatusByProductOfferId(Const.STATUS.INACTIVE, productOfferId);

        for (PartnerShareUpdateProductOfferSdi item : listPartnerShareSpecific) {
            PartnerRevenueShared prs = new PartnerRevenueShared();

            if (DataUtil.isNullOrZero(item.getPartnerRevenueSharedId())) {
                prs = item.toPrs(productOfferId);

                partnerRevenueShareRepo.save(prs);
            } else {
                Optional<PartnerRevenueShared> optionalPrs = partnerRevenueShareRepo.findById(item.getPartnerRevenueSharedId());

                if (optionalPrs.isPresent()) {
                    prs = optionalPrs.get();
                    prs = item.updatePrs(prs);
                    partnerRevenueShareRepo.save(prs);

                    documentMappingRepo.changeChangeStatusByObjectMapId(Const.STATUS.INACTIVE, item.getPartnerRevenueSharedId(), Const.DOC_OBJ_MAP_TYPE.PARTNER_REVENUE_SHARE);
                }
            }

            if (DataUtil.isNullOrZero(item.getDocumentId())) continue;
            DocumentMapping dm = new DocumentMapping();
            if (DataUtil.isNullOrZero(item.getDocumentMappingId())) {
                dm = item.toDocumentMapping(prs.getPartnerRevenueSharedId());
            } else {
                Optional<DocumentMapping> optionalDm = documentMappingRepo.findById(item.getDocumentMappingId());
                if (optionalDm.isPresent()) {
                    dm = optionalDm.get();
                    dm = item.updateDocumentMapping(dm);
                }
            }

            documentMappingRepo.save(dm);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update product offer">
    @Override
    public ProductOfferInsertSdo updateProductOffer(ProductOfferUpdateSdi dataUpdate) {
        validInputUpdate(dataUpdate);

        ProductOfferInsertSdo result = new ProductOfferInsertSdo();

        Optional<ProductOffer> optionalProductOffer = productOfferRepo.findById(dataUpdate.getProductOfferId());

        if (optionalProductOffer.isPresent()) {
            ProductOffer productOffer = optionalProductOffer.get();
            productOffer = dataUpdate.updateProductOffer(productOffer);

            productOfferRepo.save(productOffer);

            mappingUpdateProductOfferWithCustomerType(dataUpdate.getListCustomerType(), productOffer.getProductOfferId());
            mappingUpdateProductOfferWithArea(dataUpdate.getListProductOfferArea(), productOffer.getProductOfferId());
            mappingUpdateProductOfferWithCustomerPropective(dataUpdate.getListProductOfferCustomer(), productOffer.getProductOfferId(), dataUpdate.getProductId());
            mappingUpdateProductOfferWithDocument(productOffer.getProductOfferId(), dataUpdate.getDocumentId());
            mappingUpdateProductOfferWithPartner(dataUpdate.getListPartnerRevenueShareSpecific(), productOffer.getProductOfferId());

            result.setProductOfferId(productOffer.getProductOfferId());
            result.setProductOfferCode(productOffer.getProductOfferCode());
        }

        return result;
    }
    //</editor-fold>
}
