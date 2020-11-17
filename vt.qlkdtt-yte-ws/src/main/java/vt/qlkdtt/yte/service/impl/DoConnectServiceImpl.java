package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.*;
import vt.qlkdtt.yte.dto.AreaFullDTO;
import vt.qlkdtt.yte.repository.*;
import vt.qlkdtt.yte.service.*;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.*;
import vt.qlkdtt.yte.service.sdo.DoConnectSdo;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@Transactional
public class DoConnectServiceImpl implements DoConnectService {
    @Autowired
    DoConnectRepo doConnectRepo;

    @Autowired
    CustomerExtMedicalService customerExtMedicalService;

    @Autowired
    CustomerIdentityService customerIdentityService;

    @Autowired
    ProductService productService;

    @Autowired
    AreaRepo areaRepo;

    @Autowired
    PartnerService partnerService;

    @Autowired
    ProductOfferRepo productOfferRepo;

    @Autowired
    DocumentService documentService;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerIdentityRepo customerIdentityRepo;

    @Autowired
    CustomerExtMedicalRepo customerExtMedicalRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    SubscriberRepo subscriberRepo;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    DocumentMappingRepo documentMappingRepo;

    @Autowired
    CustomerOrderRepo customerOrderRepo;

    @Autowired
    CustomerOrderConfigRepo customerOrderConfigRepo;

    @Autowired
    SubscriberExtMedicalRepo subscriberExtMedicalRepo;

    @Autowired
    GlobalListRepo globalListRepo;

    @Autowired
    CustomerPropectiveRepo customerPropectiveRepo;

    @Autowired
    DocumentDetailTempRepo documentDetailTempRepo;

    @Autowired
    DocumentDetailRepo documentDetailRepo;

    //<editor-fold desc="Valid customer info">
    private void validCustomerInfo(CustomerInfoDoConnectSdi customerInfo) {
        List<String> lstError = new ArrayList<>();
        if (!DataUtil.isNullOrZero(customerInfo.getCustomerId())) {
            try {
                Customer c = customerRepo.getOne(customerInfo.getCustomerId());
                Long customerId = c.getCustomerId();
            } catch (EntityNotFoundException e) {
                lstError.add(String.valueOf(customerInfo.getCustomerId()));
                throw new AppException("API-CN072", "Customer id not exist", lstError);
            }
            return;
        }

        //customer bus type
        boolean isExistCustomerBusType = globalListRepo.isExist(customerInfo.getCustomerBusType(), Const.GLOBAL_LIST_CODE.CUSTOMER_BUS_TYPE);
        if (!isExistCustomerBusType) {
            lstError.add(customerInfo.getCustomerBusType());

            throw new AppException("API-CN001", "customerBusType " + customerInfo.getCustomerBusType() + " not exist", lstError);
        }

        //customer name
        if (DataUtil.isNullOrEmpty(customerInfo.getCustomerName())) {
            throw new AppException("API-CN002", "customerName is required");
        }

        if (customerInfo.getCustomerName().length() > 500) {
            throw new AppException("API-CN003", "customerName length cannot exceed 500 characters");
        }

        //customer identity
        if (!DataUtil.isNullOrEmpty(customerInfo.getCustomerIdentityNo())) {
            if (customerInfo.getCustomerIdentityNo().length() > 30) {
                throw new AppException("API-CN008", "customerIdentityNo length cannot exceed 30 characters");
            }
            boolean isExistCustomerIdentityNo = customerIdentityService.isExist(customerInfo.getCustomerIdentityNo());
            if (isExistCustomerIdentityNo) {
                lstError.add(customerInfo.getCustomerIdentityNo());

                throw new AppException("API-CN009", "customerIdentityNo " + customerInfo.getCustomerIdentityNo() + " already exist", lstError);
            }
        }

        //birthDate
        if (!DataUtil.isNullOrEmpty(customerInfo.getBirthDate()) && !DateUtil.isDate(customerInfo.getBirthDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-CN010", "birthDate format must be yyyy-mm-dd");
        }

        //precinct area code
        if (DataUtil.isNullOrEmpty(customerInfo.getAreaCode())) {
            throw new AppException("API-CN011", "areaCode is required");
        }
        boolean isPrecinctAreaCode = areaRepo.isPrecinctAreaCode(customerInfo.getAreaCode());
        if (!isPrecinctAreaCode) {
            lstError.add(customerInfo.getAreaCode());

            throw new AppException("API-CN012", "areaCode " + customerInfo.getAreaCode() + " is not precinct area code", lstError);
        }

        //address
        if (DataUtil.isNullOrEmpty(customerInfo.getAddress())) {
            throw new AppException("API-CN013", "address is required");
        }
        if (customerInfo.getAddress().length() > 500) {
            throw new AppException("API-CN014", "address length cannot exceed 500 characters");
        }

        //tel
        if (DataUtil.isNullOrEmpty(customerInfo.getTel())) {
            throw new AppException("API-CN015", "tel is required");
        }
        if (!DataUtil.checkPhone(customerInfo.getTel())) {
            lstError.add(customerInfo.getTel());

            throw new AppException("API-CN016", "tel " + customerInfo.getTel() + " is not tel number", lstError);
        }

        // email
        if (DataUtil.isNullOrEmpty(customerInfo.getEmail())) {
            throw new AppException("API-CN017", "email is required");
        }
        if (!DataUtil.isValidEmail(customerInfo.getEmail())) {
            lstError.add(customerInfo.getEmail());

            throw new AppException("API-CN018", "email " + customerInfo.getEmail() + " is not email address", lstError);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Valid product offer info">
    private void validProductOfferInfo(ProductOfferInfoDoConnectSdi poInfo) {
        List<String> lstError = new ArrayList<>();

        //product
        if (DataUtil.isNullOrZero(poInfo.getProductId())) {
            throw new AppException("API-CN024", "productId is required");
        }
        boolean isExistProduct = productService.isExist(poInfo.getProductId());
        if (!isExistProduct) {
            lstError.add(DataUtil.safeToString(poInfo.getProductId()));

            throw new AppException("API-CN025", "productId " + poInfo.getProductId() + " not exist", lstError);
        }

        //partner
        if (!DataUtil.isNullOrZero(poInfo.getPartnerId())) {
            boolean isExistPartner = partnerService.isExist(poInfo.getPartnerId());
            if (!isExistPartner) {
                lstError.add(DataUtil.safeToString(poInfo.getProductId()));

                throw new AppException("API-CN026", "partnerId " + poInfo.getPartnerId() + " not exist", lstError);
            }
        }

        //sale person
        if (DataUtil.isNullOrEmpty(poInfo.getSalePerson())) {
            throw new AppException("API-CN056", "Sale person is required");
        }
        if (poInfo.getSalePerson().length() > 30) {
            throw new AppException("API-CN027", "salePerson length cannot exceed 30 characters");
        }

        //sale chanel
        if (!DataUtil.isNullOrEmpty(poInfo.getSaleChanel())) {
            boolean isExistSaleChanel = globalListRepo.isExist(poInfo.getSaleChanel(), Const.GLOBAL_LIST_CODE.SALE_CHANNEL);
            if (!isExistSaleChanel) {
                lstError.add(DataUtil.safeToString(poInfo.getSaleChanel()));

                throw new AppException("API-CN028", "saleChanel " + poInfo.getSaleChanel() + " not exist", lstError);
            }
        }

        //product offer
        if (DataUtil.isNullOrZero(poInfo.getProductOfferId())) {
            throw new AppException("API-CN029", "productOfferId is required");
        }
        Optional<ProductOffer> optionPo = productOfferRepo.findById(poInfo.getProductOfferId());
        if (!optionPo.isPresent()) {
            lstError.add(DataUtil.safeToString(poInfo.getProductOfferId()));

            throw new AppException("API-CN030", "productOfferId " + poInfo.getProductOfferId() + " not exist", lstError);
        }
        ProductOffer po = optionPo.get();
        if (poInfo.getProductId() != po.getProductId()) {
            lstError.add(DataUtil.safeToString(poInfo.getProductOfferId()));
            lstError.add(DataUtil.safeToString(poInfo.getProductId()));
            throw new AppException("API-CN031", "productOfferId " + poInfo.getProductOfferId() + " don't mapping with productId " + poInfo.getProductId(), lstError);
        }

        //project code
        if (!DataUtil.isNullOrEmpty(poInfo.getProjectCode()) && poInfo.getProjectCode().length() > 30) {
            throw new AppException("API-CN032", "projectCode length cannot exceed 30 characters");
        }

        // price sale
        if (DataUtil.isNullObject(poInfo.getPriceSale())) {
            throw new AppException("API-CN033", "priceSale is required");
        }

        // price min
        if (DataUtil.isNullObject(poInfo.getPriceMin())) {
            throw new AppException("API-CN034", "priceMin is required");
        }

        // price base
//        if (DataUtil.isNullOrZero(poInfo.getPriceBase())) {
//            throw new AppException("API-CNO001", "priceBase is required");
//        }

        if (poInfo.getPriceSale() < poInfo.getPriceMin()) {
            throw new AppException("API-CN035", "priceSale not less than priceMin");
        }

        //accountNo
        if (!DataUtil.isNullOrEmpty(poInfo.getAccountBccs()) && poInfo.getAccountBccs().length() > 30) {
            throw new AppException("API-CN036", "accountNo length cannot exceed 30 characters");
        }

        //accountServiceNo
        if (!DataUtil.isNullOrEmpty(poInfo.getAccountServiceNo()) && poInfo.getAccountServiceNo().length() > 30) {
            throw new AppException("API-CN037", "accountServiceNo length cannot exceed 30 characters");
        }

        //product offer type
        if (!DataUtil.isNullOrEmpty(poInfo.getProductOfferType())) {
            boolean isExistProductOfferType = globalListRepo.isExist(poInfo.getProductOfferType(), Const.GLOBAL_LIST_CODE.PRODUCT_OFFER_TYPE);
            if (!isExistProductOfferType) {
                lstError.add(poInfo.getProductOfferType());
                throw new AppException("API-CN073", "Product offer type {0} not exist", lstError);
            }
        }

        //provision type
        if (!DataUtil.isNullOrEmpty(poInfo.getProvisionType())) {
            boolean isExistProvisionType = globalListRepo.isExist(poInfo.getProvisionType(), Const.GLOBAL_LIST_CODE.PROVISION_TYPE);
            if (!isExistProvisionType) {
                lstError.add(poInfo.getProvisionType());
                throw new AppException("API-CN074", "Provision type {0} not exist", lstError);
            }
        }

        //medical org type
        if (!DataUtil.isNullOrEmpty(poInfo.getMedicalOrgType())) {
            boolean isExistMedicalOrgType = globalListRepo.isExist(poInfo.getMedicalOrgType(), Const.GLOBAL_LIST_CODE.CUSTOMER_EXT_MEDICAL_MEDICAL_ORG_TYPE);
            if (!isExistMedicalOrgType) {
                lstError.add(poInfo.getMedicalOrgType());
                throw new AppException("API-CN075", "Medical org type {0} not exist", lstError);
            }
        }

        //product cust segm id
        if (!DataUtil.isNullOrEmpty(poInfo.getProductCustSegmId())) {
            boolean isExistProductCustSegm = globalListRepo.isExist(poInfo.getProductCustSegmId(), Const.GLOBAL_LIST_CODE.PRODUCT_CUST_SEGM);
            if (!isExistProductCustSegm) {
                lstError.add(poInfo.getProductCustSegmId());
                throw new AppException("API-CN076", "Product cust segm {0} not exist", lstError);
            }
        }

        //telco pay type
        if (!DataUtil.isNullOrEmpty(poInfo.getTelcoPayType()) && poInfo.getTelcoPayType().length() > 10) {
            throw new AppException("API-CN077", "Telco pay type length cannot exceed 10 characters");
        }

        //broker partner code
        if (!DataUtil.isNullOrEmpty(poInfo.getBrokersPartnerCode()) && poInfo.getBrokersPartnerCode().length() > 50) {
            throw new AppException("API-CN067", "Brokers partner code length cannot exceed 50 characters");
        }

        //medical no
        if (!DataUtil.isNullOrEmpty(poInfo.getMedicalNo()) && poInfo.getMedicalNo().length() > 50) {
            throw new AppException("API-CN068", "Medical number length cannot exceed 50 characters");
        }

        //medical permission no
        if (!DataUtil.isNullOrEmpty(poInfo.getMedicalPermissionNo()) && poInfo.getMedicalPermissionNo().length() > 50) {
            throw new AppException("API-CN069", "Medical permission number length cannot exceed 50 characters");
        }

        //medical permission date
        if (!DataUtil.isNullOrEmpty(poInfo.getMedicalPermissionDate()) && !DateUtil.isDate(poInfo.getMedicalPermissionDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-CN070", "Medical permission date format must be yyyy-mm-dd");
        }
    }
    //</editor-fold>

    //<editor-fold desc="Valid contract info">
    private void validContractInfo(ContractInfoDoConnectSdi contractInfo, String customerBusType) {
        List<String> lstError = new ArrayList<>();

        //contract id
        if (!DataUtil.isNullOrZero(contractInfo.getContractId())) {
            try {
                CustomerContract cc = contractRepo.getOne(contractInfo.getContractId());
                Long contractId = cc.getContractId();
            } catch (EntityNotFoundException e) {
                lstError.add(String.valueOf(contractInfo.getContractId()));
                throw new AppException("API-CN071", "Contract id {0} not exist", lstError);
            }
            return;
        }

        //contract no
        if (DataUtil.isNullOrEmpty(contractInfo.getContractNo())) {
            throw new AppException("API-CN038", "contractNo is required");
        }
        if (contractInfo.getContractNo().length() > 100) {
            throw new AppException("API-CN039", "contractNo length cannot exceed 100 characters");
        }

        //sign date
        if (!DataUtil.isNullOrEmpty(contractInfo.getSignDate()) && !DateUtil.isDate(contractInfo.getSignDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-CN040", "signDate format must be yyyy-mm-dd");
        }

        //sale notification method
        if (!DataUtil.isNullOrEmpty(contractInfo.getSaleNotiMethod())) {
            boolean isSaleNotiMethod = globalListRepo.isExist(contractInfo.getSaleNotiMethod(), Const.GLOBAL_LIST_CODE.BILL_NOTIFICATION_METHOD);

            if (!isSaleNotiMethod) {
                lstError.add(contractInfo.getSaleNotiMethod());

                throw new AppException("API-CN041", "saleNotiMethod" + contractInfo.getSaleNotiMethod() + " not exist", lstError);
            }
        }

        //represent name
        if (customerBusType.equals(Const.CUSTOMER_BUS_TYPE.PERSONAL)) {
            if (!DataUtil.isNullOrEmpty(contractInfo.getRepresentName())) {
                if (contractInfo.getRepresentName().length() > 100) {
                    throw new AppException("API-CN042", "representName length cannot exceed 100 characters");
                }
            }
        } else if (customerBusType.equals(Const.CUSTOMER_BUS_TYPE.BUSINESS)) {
            if (DataUtil.isNullOrEmpty(contractInfo.getRepresentName())) {
                throw new AppException("API-CN043", "representName is required");
            }
            if (contractInfo.getRepresentName().length() > 100) {
                throw new AppException("API-CN044", "representName length cannot exceed 100 characters");
            }
        }

        //represent id no
        if (!DataUtil.isNullOrEmpty(contractInfo.getRepresentIdNo())) {
            if (contractInfo.getRepresentIdNo().length() > 30) {
                throw new AppException("API-CN045", "representIdNo length cannot exceed 30 characters");
            }
        }

        //represent title
        if (!DataUtil.isNullOrEmpty(contractInfo.getRepresentTitle())) {
            if (contractInfo.getRepresentTitle().length() > 50) {
                throw new AppException("API-CN046", "representTitle length cannot exceed 50 characters");
            }
        }

        //represent tel
        if (!DataUtil.isNullOrEmpty(contractInfo.getRepresentTel())) {
            boolean isTel = DataUtil.checkPhone(contractInfo.getRepresentTel());
            if (!isTel) {
                lstError.add(contractInfo.getRepresentTel());

                throw new AppException("API-CN047", "representTel " + contractInfo.getRepresentTel() + " is not phone number", lstError);
            }
        }

        //represent email
        if (!DataUtil.isNullOrEmpty(contractInfo.getRepresentEmail())) {
            boolean isEmail = DataUtil.isValidEmail(contractInfo.getRepresentEmail());
            if (!isEmail) {
                lstError.add(contractInfo.getRepresentEmail());

                throw new AppException("API-CN048", "representEmail " + contractInfo.getRepresentEmail() + " is not email", lstError);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Valid document">
    private void validDocument(List<DocumentDoConnectSdi> listDocument) {
        for (DocumentDoConnectSdi item : listDocument) {
            if (DataUtil.isNullOrZero(item.getDocumentId())) {
                throw new AppException("API-CN049", "documentId is required");
            }

            boolean isExist = documentService.isExist(item.getDocumentId());
            if (!isExist) {
                List<String> lstError = new ArrayList<>();
                lstError.add(DataUtil.safeToString(item.getDocumentId()));

                throw new AppException("API-CN050", "documentId " + item.getDocumentId() + " not exist", lstError);
            }

            if (!DataUtil.isNullOrEmpty(item.getDocumentCode()) && item.getDocumentCode().length() > 50) {
                throw new AppException("API-PR017", "Document code length cannot exceed 50 characters");
            }

            boolean isExistDocument = documentService.isExist(item.getDocumentId(), item.getDocumentCode());
            if (isExistDocument) {
                List<String> lstError = new ArrayList<>();
                lstError.add(DataUtil.safeToString(item.getDocumentCode()));

                throw new AppException("API-CN052", "documentCode " + item.getDocumentCode() + " already exist", lstError);
            }

            if (!DataUtil.isNullOrEmpty(item.getDocumentName()) && item.getDocumentName().length() > 200) {
                throw new AppException("API-PR019", "Document name length cannot exceed 200 characters");
            }

            if (DataUtil.isNullOrEmpty(item.getDocumentType())) {
                throw new AppException("API-CN054", "documentType is required");
            }
            boolean isExistDocumentType = globalListRepo.isExist(item.getDocumentType(), Const.GLOBAL_LIST_CODE.DOCUMENT_TYPE);
            if (!isExistDocumentType) {
                List<String> lstError = new ArrayList<>();
                lstError.add(DataUtil.safeToString(item.getDocumentType()));

                throw new AppException("API-CN055", "documentType " + item.getDocumentType() + " not exist", lstError);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="valid input doConnect">
    private void validInputDoConnect(DoConnectSdi dataConnect) {
        //valid customer info
        validCustomerInfo(dataConnect.getCustomerInfo());

        //valid product offer info
        validProductOfferInfo(dataConnect.getProductOfferInfo());

        //valid contract info
        validContractInfo(dataConnect.getContractInfo(), dataConnect.getCustomerInfo().getCustomerBusType());

        //valid fee: hiện tại chưa có trong hệ thống

        //valid document
        validDocument(dataConnect.getListDocument());
    }
    //</editor-fold>

    //<editor-fold desc="Customer DoConnect">
    private DoConnectSdo customerDoConnect(CustomerInfoDoConnectSdi customerInfo, ContractInfoDoConnectSdi contractInfo) {
        DoConnectSdo result = new DoConnectSdo();

        Long customerId = customerInfo.getCustomerId();
        if (DataUtil.isNullOrZero(customerId)) {
            AreaFullDTO precinct = areaRepo.getAreaByCode(customerInfo.getAreaCode());
            AreaFullDTO district = areaRepo.getAreaByCode(precinct.getParentCode());
            AreaFullDTO province = areaRepo.getAreaByCode(district.getParentCode());
            //insert customer
            Customer customer = customerInfo.toCustomer(province.getAreaCode(), district.getAreaCode(), precinct.getAreaCode(), contractInfo);
            customer = customerRepo.save(customer);
            result.setCustomerId(customer.getCustomerId());
            customerId = customer.getCustomerId();

            //insert customer identity
            if (!DataUtil.isNullOrEmpty(customerInfo.getCustomerIdentityNo())) {
                CustomerIdentity ci = customerInfo.toCustomerIdentity(customer.getCustomerId());
                ci = customerIdentityRepo.save(ci);
            }

            if (!DataUtil.isNullOrZero(customerInfo.getCustomerPropectiveId())) {
                Optional<CustomerPropective> optionalCP = customerPropectiveRepo.findById(customerInfo.getCustomerPropectiveId());
                if (optionalCP.isPresent()) {
                    CustomerPropective cp = optionalCP.get();
                    cp.setCustomerId(customerInfo.getCustomerId());

                    customerPropectiveRepo.save(cp);
                }
            }
        } else {
            result.setCustomerId(customerInfo.getCustomerId());
        }

        customerPropectiveRepo.updateAfterConnect(customerInfo.getCustomerPropectiveCode(), customerInfo.getCustomerPropectiveId(), customerId);

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Insert customer order">
    private String insertCustomerOrder(Long customerId, String orderType, Long accountId, Long productId, Long subscriberId,
                                       String areaCode, String orderContactName, String orderContactTel, String orderContactEmail) {
        CustomerOrderConfig coc = customerOrderConfigRepo.search(orderType, Const.ORDER_ACTION_TYPE.CONNECT_WAIT_ACCEPTANCE, productId);

        Date dueDate = new Date();
        if (coc != null && !DataUtil.isNullOrZero(coc.getCustomerOrderConfigId())) {
            Long limitTime = coc.getLimitTime();
            if (!DataUtil.isNullObject(limitTime)) {
                dueDate = DateUtil.addDate(dueDate, DataUtil.safeToInt(limitTime));
            }
        }

        CustomerOrder co = new CustomerOrder();
        co.setCustomerId(customerId);
        co.setCustomerAccountId(accountId);
        co.setProductId(productId);
        co.setSubscriberId(subscriberId);
        co.setProvince(areaCode);
        co.setOrderType(orderType);
        co.setStatus(Const.ORDER_STATUS.PENDING);
        co.setOrderActionTypeId(Const.ORDER_ACTION_TYPE.CONNECT_WAIT_ACCEPTANCE);
        co.setOrderDatetime(new Date());
        co.setOrderContactName(orderContactName);
        co.setOrderContactTel(orderContactTel);
        co.setOrderContactEmail(orderContactEmail);
        co.setCreateUser(Const.ADMIN);
        co.setCreateDatetime(new Date());
        co.setAssigneeCode(Const.ADMIN);
        co.setDueDatetime(dueDate);

        co = customerOrderRepo.save(co);

        String customerOrderId = String.valueOf(co.getCustomerOrderId());

        int lengthCustomerOrderId = customerOrderId.length();
        for (int i = 0; i < 10 - lengthCustomerOrderId; i++) {
            customerOrderId = "0" + customerOrderId;
        }

        return customerOrderId;
    }
    //</editor-fold>

    //<editor-fold desc="Contract do connect">
    private Map<String, Object> contractDoConnect(Long customerId, ContractInfoDoConnectSdi contractInfo, ProductOfferInfoDoConnectSdi productOfferInfo) {
        Map<String, Object> result = new HashMap<>();

        CustomerContract cc = new CustomerContract();
        CustomerAccount ca = new CustomerAccount();

        if (DataUtil.isNullOrZero(contractInfo.getContractId())) {
            //new contract ==> create new account
            //create new account
            ca = contractInfo.toCustomerAccount(customerId, productOfferInfo.getAccountBccs(), productOfferInfo.getAccountServiceNo());
            ca = accountRepo.save(ca);

            //create new contract
            cc = contractInfo.toCustomerContract(customerId, ca.getAccountId(), productOfferInfo.getProductOfferId(), productOfferInfo.getPartnerId());
            cc = contractRepo.save(cc);
        } else {
            cc.setContractId(contractInfo.getContractId());

            Long accId = contractRepo.getAccountId(contractInfo.getContractId());
            ca.setAccountId(accId);
        }

        result.put("accountId", ca.getAccountId());
        result.put("contractId", cc.getContractId());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Document do connect">
    private void documentDoConnect(List<DocumentDoConnectSdi> listDocument, Long customerId){
        for (DocumentDoConnectSdi item : listDocument) {
            Optional<Document> optionalDoc = documentRepo.findById(item.getDocumentId());
            if (optionalDoc.isPresent()) {
                Document doc = optionalDoc.get();
                doc.setCode(item.getDocumentCode());
                doc.setName(item.getDocumentName());
                doc.setType(item.getDocumentType());
                doc.setUpdateUser("ADMIN");
                doc.setUpdateDatetime(new Date());
            }

            DocumentMapping dm = new DocumentMapping();

            dm.setDocumentId(item.getDocumentId());
            dm.setObjectType(Const.DOC_OBJ_MAP_TYPE.CUSTOMER);
            dm.setMappingObjectId(customerId);
            dm.setStatus(Const.STATUS.ACTIVE);
            dm.setCreateDatetime(new Date());
            dm.setCreateUser("ADMIN");

            documentMappingRepo.save(dm);

            //move document detail temp to document detail
            List<DocumentDetailTemp> listDocDetailTemp = documentDetailTempRepo.getByDocumentId(item.getDocumentId());
            for (DocumentDetailTemp docDetailTempItem : listDocDetailTemp) {
                //copy file to new folder

                //remove file in folder temp

                //insert into document detail table
                DocumentDetail documentDetail = docDetailTempItem.toDocumentDetail();
                documentDetailRepo.save(documentDetail);

                //delete from document detail temp table
                documentDetailTempRepo.delete(docDetailTempItem);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="doConnect">
    @Override
    public DoConnectSdo doConnect(DoConnectSdi dataConnect) {
        validInputDoConnect(dataConnect);

        CustomerInfoDoConnectSdi customerInfo = dataConnect.getCustomerInfo();
        ContractInfoDoConnectSdi contractInfo = dataConnect.getContractInfo();
        ProductOfferInfoDoConnectSdi productOfferInfo = dataConnect.getProductOfferInfo();
        List<DocumentDoConnectSdi> listDocument = dataConnect.getListDocument();

        //customer
        DoConnectSdo result = customerDoConnect(customerInfo, contractInfo);

        //contract
        Map<String, Object> rsContractConnect = contractDoConnect(result.getCustomerId(), contractInfo, productOfferInfo);

        // product offer
        Subscriber subscriber = productOfferInfo.toSubscriber(result.getCustomerId(), DataUtil.safeToLong(rsContractConnect.get("accountId")),
                DataUtil.safeToLong(rsContractConnect.get("contractId")), contractInfo.getPaymentMethod());
        subscriber = subscriberRepo.save(subscriber);

        SubscriberExtMedical sem = productOfferInfo.toSubscriberExtMedical(subscriber.getSubscriberId());
        subscriberExtMedicalRepo.save(sem);

        //document
        documentDoConnect(listDocument, result.getCustomerId());

        String customerName = customerInfo.getCustomerName();
        String tel = customerInfo.getTel();
        String email = customerInfo.getEmail();

        if (Const.CUSTOMER_BUS_TYPE.BUSINESS.equals(customerInfo.getCustomerBusType())) {
            customerName = contractInfo.getRepresentName();
            tel = contractInfo.getRepresentTel();
            email = contractInfo.getRepresentEmail();
        }

        String customerOrderId = insertCustomerOrder(result.getCustomerId(), Const.ORDER_TYPE.CONNECT, DataUtil.safeToLong(rsContractConnect.get("accountId")), productOfferInfo.getProductId(),
                subscriber.getSubscriberId(), customerInfo.getProvince(), customerName, tel, email);
        result.setCustomerOrderId(customerOrderId);

        return result;
    }
    //</editor-fold>
}
