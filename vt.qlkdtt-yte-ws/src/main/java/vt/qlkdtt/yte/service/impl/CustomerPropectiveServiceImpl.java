package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerPropective;
import vt.qlkdtt.yte.domain.Product;
import vt.qlkdtt.yte.repository.AreaRepo;
import vt.qlkdtt.yte.repository.CustomerPropectiveRepo;
import vt.qlkdtt.yte.repository.GlobalListRepo;
import vt.qlkdtt.yte.repository.ProductRepo;
import vt.qlkdtt.yte.service.CustomerPropectiveService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveInsertSdi;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveSearchSdi;
import vt.qlkdtt.yte.service.sdi.CustomerPropectiveUpdateSdi;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveInsertSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerPropectiveSearchSdo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CustomerPropectiveServiceImpl implements CustomerPropectiveService {
    @Autowired
    CustomerPropectiveRepo customerPropectiveRepo;

    @Autowired
    GlobalListRepo globalListRepo;

    @Autowired
    AreaRepo areaRepo;

    @Autowired
    ProductRepo productRepo;

    @Override
    public void changeStatusByProductOffer(String status, long productOfferId) {
        customerPropectiveRepo.changeStatusByProductOffer(status, productOfferId);
    }

    @Override
    public boolean isExist(long customerPropectiveId) {
        return customerPropectiveRepo.isExist(customerPropectiveId);
    }

    //<editor-fold desc="Search">
    @Override
    public Page<CustomerPropectiveSearchSdo> searchCustomerPropective(CustomerPropectiveSearchSdi sdi, Pageable pageable) {
        return customerPropectiveRepo.searchCustomerPropective(sdi, pageable);
    }
    //</editor-fold>

    //<editor-fold desc="Search by id">
    @Override
    public CustomerPropectiveSearchByIdSdo searchById(Long customerPropectiveId) {
        if (DataUtil.isNullOrZero(customerPropectiveId)) {
            throw new AppException("API-CP001", "customerPropectiveId is required");
        }

        return customerPropectiveRepo.searchById(customerPropectiveId);
    }
    //</editor-fold>

    //<editor-fold desc="Change status">
    @Override
    public Map<String, Object> changeStatus(Long customerPropectiveId, String status) {
        if (DataUtil.isNullOrZero(customerPropectiveId)) {
            throw new AppException("API-CP001", "customerPropectiveId is required");
        }

        if (DataUtil.isNullOrEmpty(status)) {
            throw new AppException("API-CP002", "status is required");
        }

        boolean isExistCustomerPropective = customerPropectiveRepo.isExist(customerPropectiveId);
        if (!isExistCustomerPropective) {
            List<String> lstError = new ArrayList<>();
            lstError.add(DataUtil.safeToString(customerPropectiveId));

            throw new AppException("API-CP003", "customerPropectiveId " + customerPropectiveId + " not exist", lstError);
        }

        return customerPropectiveRepo.changeStatus(customerPropectiveId, status);
    }
    //</editor-fold>

    //<editor-fold desc="Valid input insert">
    private void validInputInsert(CustomerPropectiveInsertSdi dataInsert) {
        List<String> lstError = new ArrayList<>();

        String customerBusType = dataInsert.getCustomerBusTypeValue();
        String customerName = dataInsert.getCustomerName();
        String identityType = dataInsert.getIdentityType();
        String identityNo = dataInsert.getIdentityNo();
        String birthDate = dataInsert.getBirthDate();
        String areaCodeProvince = dataInsert.getAreaCodeProvince();
        String areaCodeDistrict = dataInsert.getAreaCodeDistrict();
        String areaCodePrecinct = dataInsert.getAreaCodePrecinct();
        String address = dataInsert.getAddress();
        String tel = dataInsert.getTel();
        String email = dataInsert.getEmail();
        Long productId = dataInsert.getProductId();
        String medicalNo = dataInsert.getMedicalNo();
        String medicalPermissionNo = dataInsert.getMedicalPermissionNo();
        String medicalPermissionDate = dataInsert.getMedicalPermissionDate();
        String accountNo = dataInsert.getAccountNo();
        String accountServiceNo = dataInsert.getAccountServiceNo();
        String representName = dataInsert.getRepresentName();
        String representIdNo = dataInsert.getRepresentIdNo();
        String representTel = dataInsert.getRepresentTel();
        String representEmail = dataInsert.getRepresentEmail();
        String salesManCode = dataInsert.getSalesManCode();
        String brokersPartnerCode = dataInsert.getBrokersPartnerCode();
        String medicalOrgType = dataInsert.getMedicalOrgType();

        //customer bus type
        if (DataUtil.isNullOrEmpty(customerBusType)) {
            throw new AppException("API-CP039", "Customer business type is required");
        }
        boolean isExistCustomerBusType = globalListRepo.isExist(customerBusType, Const.GLOBAL_LIST_CODE.CUSTOMER_BUS_TYPE);
        if (!isExistCustomerBusType) {
            lstError.add(customerBusType);
            throw new AppException("API-CP005", "customerBusTypeValue " + customerBusType + " not exist", lstError);
        }


        if (customerBusType.equals(Const.CUSTOMER_BUS_TYPE.PERSONAL)) {
            //identity type
            if (DataUtil.isNullOrEmpty(identityType)) {
                throw new AppException("API-CP040", "Customer identity type is required");
            }
            boolean isExistIdType = globalListRepo.isExist(identityType, Const.GLOBAL_LIST_CODE.ID_TYPE);
            if (!isExistIdType) {
                lstError.add(identityType);
                throw new AppException("API-CP041", "Customer identity type {0} not exist", lstError);
            }

            //identity no
            if (DataUtil.isNullOrEmpty(identityNo)) {
                throw new AppException("API-CP012", "Identity number is required");
            }
            if (identityNo.length() > 30) {
                throw new AppException("API-CP013", "identityNo length cannot exceed 30 characters");
            }
        }

        //customer name
        if (DataUtil.isNullOrEmpty(customerName)) {
            throw new AppException("API-CP008", "customerName is required");
        }
        if (customerName.length() > 255) {
            throw new AppException("API-CP009", "customerName length cannot exceed 255 characters");
        }

        //birth date
        if (!DataUtil.isNullOrEmpty(birthDate)) {
            if (!DateUtil.isDate(birthDate, Const.DATE_FORMAT)) {
                throw new AppException("API-CP015", "birthDate format must be yyyy-mm-dd");
            }
        }

        //area code province
        if (DataUtil.isNullOrEmpty(areaCodeProvince)) {
            throw new AppException("API-CP042", "Province is required");
        }
        boolean isExistAreaCodeProvince = areaRepo.isExist(areaCodeProvince);
        if (!isExistAreaCodeProvince) {
            lstError.add(areaCodeProvince);
            throw new AppException("API-CP017", "areaCodeProvince " + areaCodeProvince + " is not area code province", lstError);
        }

        //area code district
        if (DataUtil.isNullOrEmpty(areaCodeDistrict)) {
            throw new AppException("API-CP043", "District is required");
        }
        boolean isExistAreaCodeDistrict = areaRepo.isExist(areaCodeDistrict);
        if (!isExistAreaCodeDistrict) {
            lstError.add(areaCodeDistrict);
            throw new AppException("API-CP019", "areaCodeDistrict " + areaCodeDistrict + " is not area code district", lstError);
        }

        //area code precinct
        if (DataUtil.isNullOrEmpty(areaCodePrecinct)) {
            throw new AppException("API-CP044", "Precinct is required");
        }
        boolean isExistAreaCodePrecinct = areaRepo.isExist(areaCodePrecinct);
        if (!isExistAreaCodePrecinct) {
            lstError.add(areaCodePrecinct);
            throw new AppException("API-CP021", "areaCodePrecinct " + areaCodePrecinct + " is not area code precinct", lstError);
        }

        //address
        if (DataUtil.isNullOrEmpty(address)) {
            throw new AppException("API-CP022", "address is required");
        }
        if (address.length() > 500) {
            throw new AppException("API-CP023", "address length cannot exceed 500 characters");
        }

        //tel
        if (DataUtil.isNullOrEmpty(tel)) {
            throw new AppException("API-CP024", "tel is required");
        }
        if (!DataUtil.checkPhone(tel)) {
            lstError.add(tel);
            throw new AppException("API-CP025", "tel " + tel + " is not phone number", lstError);
        }

        //email
        if (DataUtil.isNullOrEmpty(email)) {
            throw new AppException("API-CP026", "email is required");
        }
        if (email.length() > 50) {
            throw new AppException("API-CP027", "email length cannot exceed 50 characters");
        }
        if (!DataUtil.isValidEmail(email)) {
            lstError.add(email);
            throw new AppException("API-CP028", "email " + email + " is not email address", lstError);
        }

        //product
        if (!DataUtil.isNullOrZero(productId)) {
            Optional<Product> optionalP = productRepo.findById(productId);
            if (!optionalP.isPresent()) {
                lstError.add(String.valueOf(productId));
                throw new AppException("API-CP046", "Product id {0} not exist", lstError);
            }
        }

        //medicalNo
        if (!DataUtil.isNullOrEmpty(medicalNo) && medicalNo.length() > 100) {
            throw new AppException("API-CP047", "Medical number length cannot exceed 100 characters");
        }

        //medical permission no
        if (!DataUtil.isNullOrEmpty(medicalPermissionNo) && medicalPermissionNo.length() > 50) {
            throw new AppException("API-CP063", "Medical permission number length cannot exceed 50 characters");
        }

        //medical permission date
        if (!DataUtil.isNullOrEmpty(medicalPermissionDate) && !DateUtil.isDate(medicalPermissionDate, Const.DATE_FORMAT)) {
            throw new AppException("API-CP064", "Medical permission date format must be yyyy-mm-dd");
        }

        //accountNo
        if (!DataUtil.isNullOrEmpty(accountNo) && accountNo.length() > 30) {
            throw new AppException("API-CP048", "Account number length cannot exceed 30 characters");
        }

        //accountServiceNo
        if (!DataUtil.isNullOrEmpty(accountServiceNo) && accountServiceNo.length() > 30) {
            throw new AppException("API-CP049", "Account service number length cannot exceed 30 characters");
        }

        //represent name
        if (DataUtil.isNullOrEmpty(representName)) {
            throw new AppException("API-CP051", "Represent name is required");
        }
        if (representName.length() > 100) {
            throw new AppException("API-CP057", "Represent name length cannot exceed 100 characters");
        }

        //represent id no
        if (DataUtil.isNullOrEmpty(representIdNo)) {
            throw new AppException("API-CP052", "Represent identity number is required");
        }
        if (representIdNo.length() > 30) {
            throw new AppException("API-CP058", "Represent identity number length cannot exceed 30 characters");
        }

        //represent tel
        if (DataUtil.isNullOrEmpty(representTel)) {
            throw new AppException("API-CP053", "Represent tel is required");
        }
        if (!DataUtil.checkPhone(representTel)) {
            lstError.add(representTel);
            throw new AppException("API-CP054", "{0} is not phone number", lstError);
        }

        //represent email
        if (DataUtil.isNullOrEmpty(representEmail)) {
            throw new AppException("API-CP055", "Represent email is required");
        }
        if (!DataUtil.isValidEmailVersion2(representEmail)) {
            lstError.add(representEmail);
            throw new AppException("API-CP056", "{0} is not email", lstError);
        }
        if (representEmail.length() > 50) {
            throw new AppException("API-CP059", "Represent email length cannot exceed 50 characters");
        }

        //sales man code
        if (!DataUtil.isNullOrEmpty(salesManCode) && salesManCode.length() > 50) {
            throw new AppException("API-CP060", "Sales man code length cannot exceed 50 characters");
        }

        //broker partner code
        if (!DataUtil.isNullOrEmpty(brokersPartnerCode) && brokersPartnerCode.length() > 255) {
            throw new AppException("API-CP061", "Brokers partner code length cannot exceed 255 characters");
        }

        //medical org type
        if (!DataUtil.isNullOrEmpty(medicalOrgType)) {
            boolean isExistMedicalOrgType = globalListRepo.isExist(medicalOrgType, Const.GLOBAL_LIST_CODE.CUSTOMER_EXT_MEDICAL_MEDICAL_ORG_TYPE);
            if (!isExistMedicalOrgType) {
                lstError.add(medicalOrgType);
                throw new AppException("API-CP062", "Medical org type " + medicalOrgType + " not exist", lstError);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Insert customer propective">
    @Override
    public CustomerPropectiveInsertSdo createCustomerPropective(CustomerPropectiveInsertSdi dataInsert) {
        CustomerPropectiveInsertSdo result = new CustomerPropectiveInsertSdo();

        validInputInsert(dataInsert);

        CustomerPropective cp = new CustomerPropective();
        if (!DataUtil.isNullOrEmpty(dataInsert.getIdentityNo())) {
            cp = customerPropectiveRepo.searchByIdentityNo(dataInsert.getIdentityNo());
            if (DataUtil.isNullOrZero(cp.getPropectiveCustomerId())) {
                String cpCode = customerPropectiveRepo.getCustomerPropectiveCode();
                //tạo mới customer propective
                cp = dataInsert.toCustomerPropective();
                cp.setCustomerPropectiveCode(cpCode);
            } else {
                //số giấy tờ đã tồn tại ==> insert theo customer propective cũ
                cp = dataInsert.toCustomerPropective(cp);
            }
        } else {
            //check tên đã tồn tại
            cp = customerPropectiveRepo.searchByCustomerName(dataInsert.getCustomerName());
            if (DataUtil.isNullOrZero(cp.getPropectiveCustomerId())) {
                String cpCode = customerPropectiveRepo.getCustomerPropectiveCode();
                //tạo mới customer propective
                cp = dataInsert.toCustomerPropective();
                cp.setCustomerPropectiveCode(cpCode);
            } else {
                //insert theo customer propective cũ
                cp = dataInsert.toCustomerPropective(cp);
            }
        }
        cp = customerPropectiveRepo.save(cp);

        result.setCustomerPropectiveId(cp.getPropectiveCustomerId());

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Valid input update">
    private void validInputUpdate(CustomerPropectiveUpdateSdi dataUpdate) {
        List<String> lstError = new ArrayList<>();

        Long customerPropectiveId = dataUpdate.getCustomerPropectiveId();
        String customerBusType = dataUpdate.getCustomerBusTypeValue();
        String customerName = dataUpdate.getCustomerName();
        String identityType = dataUpdate.getIdentityType();
        String identityNo = dataUpdate.getIdentityNo();
        String birthDate = dataUpdate.getBirthDate();
        String areaCodeProvince = dataUpdate.getAreaCodeProvince();
        String areaCodeDistrict = dataUpdate.getAreaCodeDistrict();
        String areaCodePrecinct = dataUpdate.getAreaCodePrecinct();
        String address = dataUpdate.getAddress();
        String tel = dataUpdate.getTel();
        String email = dataUpdate.getEmail();
        Long productId = dataUpdate.getProductId();
        String medicalNo = dataUpdate.getMedicalNo();
        String medicalPermissionNo = dataUpdate.getMedicalPermissionNo();
        String medicalPermissionDate = dataUpdate.getMedicalPermissionDate();
        String accountNo = dataUpdate.getAccountNo();
        String accountServiceNo = dataUpdate.getAccountServiceNo();
        String representName = dataUpdate.getRepresentName();
        String representIdNo = dataUpdate.getRepresentIdNo();
        String representTel = dataUpdate.getRepresentTel();
        String representEmail = dataUpdate.getRepresentEmail();
        String salesManCode = dataUpdate.getSalesManCode();
        String brokersPartnerCode = dataUpdate.getBrokersPartnerCode();
        String medicalOrgType = dataUpdate.getMedicalOrgType();

        //customer propective id
        if (DataUtil.isNullOrZero(customerPropectiveId)) {
            throw new AppException("API-CP001", "customerPropectiveId is required");
        }
        boolean isExistCustomerPropective = customerPropectiveRepo.isExist(customerPropectiveId);
        if (!isExistCustomerPropective) {
            lstError.add(DataUtil.safeToString(customerPropectiveId));
            throw new AppException("API-CP003", "customerPropectiveId " + customerPropectiveId + " not exist", lstError);
        }

        //customer bus type
        if (DataUtil.isNullOrEmpty(customerBusType)) {
            throw new AppException("API-CP039", "Customer business type is required");
        }
        boolean isExistCustomerBusType = globalListRepo.isExist(customerBusType, Const.GLOBAL_LIST_CODE.CUSTOMER_BUS_TYPE);
        if (!isExistCustomerBusType) {
            lstError.add(customerBusType);
            throw new AppException("API-CP005", "customerBusTypeValue " + customerBusType + " not exist", lstError);
        }

        if (customerBusType.equals(Const.CUSTOMER_BUS_TYPE.PERSONAL)) {
            //identity type
            if (DataUtil.isNullOrEmpty(identityType)) {
                throw new AppException("API-CP040", "Customer identity type is required");
            }
            boolean isExistIdType = globalListRepo.isExist(identityType, Const.GLOBAL_LIST_CODE.ID_TYPE);
            if (!isExistIdType) {
                lstError.add(identityType);
                throw new AppException("API-CP041", "Customer identity type {0} not exist", lstError);
            }

            //identity no
            if (DataUtil.isNullOrEmpty(identityNo)) {
                throw new AppException("API-CP012", "Identity number is required");
            }
            if (identityNo.length() > 30) {
                throw new AppException("API-CP013", "identityNo length cannot exceed 30 characters");
            }
        }

        //customer name
        if (DataUtil.isNullOrEmpty(customerName)) {
            throw new AppException("API-CP008", "customerName is required");
        }
        if (customerName.length() > 255) {
            throw new AppException("API-CP009", "customerName length cannot exceed 255 characters");
        }

        //birth date
        if (!DataUtil.isNullOrEmpty(birthDate)) {
            if (!DateUtil.isDate(birthDate, Const.DATE_FORMAT)) {
                throw new AppException("API-CP015", "birthDate format must be yyyy-mm-dd");
            }
        }

        //area code province
        if (DataUtil.isNullOrEmpty(areaCodeProvince)) {
            throw new AppException("API-CP042", "Province is required");
        }
        boolean isExistAreaCodeProvince = areaRepo.isExist(areaCodeProvince);
        if (!isExistAreaCodeProvince) {
            lstError.add(areaCodeProvince);
            throw new AppException("API-CP017", "areaCodeProvince " + areaCodeProvince + " is not area code province", lstError);
        }

        //area code district
        if (DataUtil.isNullOrEmpty(areaCodeDistrict)) {
            throw new AppException("API-CP043", "District is required");
        }
        boolean isExistAreaCodeDistrict = areaRepo.isExist(areaCodeDistrict);
        if (!isExistAreaCodeDistrict) {
            lstError.add(areaCodeDistrict);
            throw new AppException("API-CP019", "areaCodeDistrict " + areaCodeDistrict + " is not area code district", lstError);
        }

        //area code precinct
        if (DataUtil.isNullOrEmpty(areaCodePrecinct)) {
            throw new AppException("API-CP044", "Precinct is required");
        }
        boolean isExistAreaCodePrecinct = areaRepo.isExist(areaCodePrecinct);
        if (!isExistAreaCodePrecinct) {
            lstError.add(areaCodePrecinct);
            throw new AppException("API-CP021", "areaCodePrecinct " + areaCodePrecinct + " is not area code precinct", lstError);
        }

        //address
        if (DataUtil.isNullOrEmpty(address)) {
            throw new AppException("API-CP022", "address is required");
        }
        if (address.length() > 500) {
            throw new AppException("API-CP023", "address length cannot exceed 500 characters");
        }

        //tel
        if (DataUtil.isNullOrEmpty(tel)) {
            throw new AppException("API-CP024", "tel is required");
        }
        if (!DataUtil.checkPhone(tel)) {
            lstError.add(tel);
            throw new AppException("API-CP025", "tel " + tel + " is not phone number", lstError);
        }

        //email
        if (DataUtil.isNullOrEmpty(email)) {
            throw new AppException("API-CP026", "email is required");
        }
        if (email.length() > 50) {
            throw new AppException("API-CP027", "email length cannot exceed 50 characters");
        }
        if (!DataUtil.isValidEmail(email)) {
            lstError.add(email);
            throw new AppException("API-CP028", "email " + email + " is not email address", lstError);
        }

        //product
        if (!DataUtil.isNullOrZero(productId)) {
            Optional<Product> optionalP = productRepo.findById(productId);
            if (!optionalP.isPresent()) {
                lstError.add(String.valueOf(productId));
                throw new AppException("API-CP046", "Product id {0} not exist", lstError);
            }
        }

        //medicalNo
        if (!DataUtil.isNullOrEmpty(medicalNo) && medicalNo.length() > 100) {
            throw new AppException("API-CP047", "Medical number length cannot exceed 100 characters");
        }

        //medical permission no
        if (!DataUtil.isNullOrEmpty(medicalPermissionNo) && medicalPermissionNo.length() > 50) {
            throw new AppException("API-CP063", "Medical permission number length cannot exceed 50 characters");
        }

        //medical permission date
        if (!DataUtil.isNullOrEmpty(medicalPermissionDate) && !DateUtil.isDate(medicalPermissionDate, Const.DATE_FORMAT)) {
            throw new AppException("API-CP064", "Medical permission date format must be yyyy-mm-dd");
        }

        //accountNo
        if (!DataUtil.isNullOrEmpty(accountNo) && accountNo.length() > 30) {
            throw new AppException("API-CP048", "Account number length cannot exceed 30 characters");
        }

        //accountServiceNo
        if (!DataUtil.isNullOrEmpty(accountServiceNo) && accountServiceNo.length() > 30) {
            throw new AppException("API-CP049", "Account service number length cannot exceed 30 characters");
        }

        //represent name
        if (DataUtil.isNullOrEmpty(representName)) {
            throw new AppException("API-CP051", "Represent name is required");
        }
        if (representName.length() > 100) {
            throw new AppException("API-CP057", "Represent name length cannot exceed 100 characters");
        }

        //represent id no
        if (DataUtil.isNullOrEmpty(representIdNo)) {
            throw new AppException("API-CP052", "Represent identity number is required");
        }
        if (representIdNo.length() > 30) {
            throw new AppException("API-CP058", "Represent identity number length cannot exceed 30 characters");
        }

        //represent tel
        if (DataUtil.isNullOrEmpty(representTel)) {
            throw new AppException("API-CP053", "Represent tel is required");
        }
        if (!DataUtil.checkPhone(representTel)) {
            lstError.add(representTel);
            throw new AppException("API-CP054", "{0} is not phone number", lstError);
        }

        //represent email
        if (DataUtil.isNullOrEmpty(representEmail)) {
            throw new AppException("API-CP055", "Represent email is required");
        }
        if (!DataUtil.isValidEmailVersion2(representEmail)) {
            lstError.add(representEmail);
            throw new AppException("API-CP056", "{0} is not email", lstError);
        }
        if (representEmail.length() > 50) {
            throw new AppException("API-CP059", "Represent email length cannot exceed 50 characters");
        }

        //sales man code
        if (!DataUtil.isNullOrEmpty(salesManCode) && salesManCode.length() > 50) {
            throw new AppException("API-CP060", "Sales man code length cannot exceed 50 characters");
        }

        //broker partner code
        if (!DataUtil.isNullOrEmpty(brokersPartnerCode) && brokersPartnerCode.length() > 255) {
            throw new AppException("API-CP061", "Brokers partner code length cannot exceed 255 characters");
        }

        //medical org type
        if (!DataUtil.isNullOrEmpty(medicalOrgType)) {
            boolean isExistMedicalOrgType = globalListRepo.isExist(medicalOrgType, Const.GLOBAL_LIST_CODE.CUSTOMER_EXT_MEDICAL_MEDICAL_ORG_TYPE);
            if (!isExistMedicalOrgType) {
                lstError.add(medicalOrgType);
                throw new AppException("API-CP062", "Medical org type " + medicalOrgType + " not exist", lstError);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update">
    @Override
    public CustomerPropectiveInsertSdo updateCustomerPropective(CustomerPropectiveUpdateSdi dataUpdate) {
        CustomerPropectiveInsertSdo result = new CustomerPropectiveInsertSdo();

        validInputUpdate(dataUpdate);

        Optional<CustomerPropective> optionalCP = customerPropectiveRepo.findById(dataUpdate.getCustomerPropectiveId());

        if (optionalCP.isPresent()) {
            CustomerPropective cp = optionalCP.get();
            cp = dataUpdate.updateCustomerPropective(cp);

            cp = customerPropectiveRepo.save(cp);

            result.setCustomerPropectiveId(cp.getPropectiveCustomerId());
        }

        return result;
    }
    //</editor-fold>
}
