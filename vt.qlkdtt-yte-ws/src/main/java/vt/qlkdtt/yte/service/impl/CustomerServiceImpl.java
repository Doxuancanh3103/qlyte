package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.Customer;
import vt.qlkdtt.yte.domain.CustomerIdentity;
import vt.qlkdtt.yte.domain.CustomerOrder;
import vt.qlkdtt.yte.dto.AreaFullDTO;
import vt.qlkdtt.yte.repository.*;
import vt.qlkdtt.yte.service.CustomerService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.CustomerSearchAfterSaleSdi;
import vt.qlkdtt.yte.service.sdi.CustomerUpdateSdi;
import vt.qlkdtt.yte.service.sdo.CustomerFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.CustomerSearchAfterSaleSdo;
import vt.qlkdtt.yte.service.sdo.CustomerSearchSdo;
import vt.qlkdtt.yte.service.sdo.CustomerUpdateSdo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    GlobalListRepo globalListRepo;

    @Autowired
    AreaRepo areaRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerExtMedicalRepo customerExtMedicalRepo;

    @Autowired
    CustomerIdentityRepo customerIdentityRepo;

    @Autowired
    CustomerOrderRepo customerOrderRepo;

    @Override
    public Page<CustomerSearchSdo> searchCustomerConnection(String customerBusType, String customerName, String accountServiceNo,
                                                            String customerIdentityType, String customerIdentityNo, Pageable pageRequest) {
        return customerRepo.searchCustomerConnection(customerBusType, customerName, accountServiceNo, customerIdentityType, customerIdentityNo, pageRequest);
    }

    @Override
    public Page<CustomerSearchAfterSaleSdo> searchCustomerAfterSale(CustomerSearchAfterSaleSdi dataSearch, Pageable pageRequest) {
        return customerRepo.searchCustomerAfterSale(dataSearch, pageRequest);
    }

    @Override
    public CustomerFindByIdSdo findById(Long customerId) throws ParseException, IllegalAccessException {
        if (DataUtil.isNullOrZero(customerId)) {
            throw new AppException("API-CU002", "customerId is required");
        }
        return customerRepo.searchCustomerById(customerId);
    }

    //<editor-fold desc="Valid data update">
    private void validDataUpdate(CustomerUpdateSdi dataUpdate) {
        List<String> lstError = new ArrayList<>();

        //check customerId
        if (DataUtil.isNullOrZero(dataUpdate.getCustomerId())) {
            throw new AppException("API-CU002", "customerId is required");
        }
        boolean isExistCustomer = customerRepo.isExist(dataUpdate.getCustomerId());
        if (!isExistCustomer) {
            lstError.add(DataUtil.safeToString(dataUpdate.getCustomerId()));

            throw new AppException("API-CU001", "customerId " + dataUpdate.getCustomerId() + " not exist", lstError);
        }

        //check order type
        if (DataUtil.isNullOrEmpty(dataUpdate.getOrderType())) {
            throw new AppException("API-CU003", "orderType is required");
        }
        boolean isExistOrderType = globalListRepo.isExist(dataUpdate.getOrderType(), Const.GLOBAL_LIST_CODE.ORDER_TYPE);
        if (!isExistOrderType) {
            lstError.add(dataUpdate.getOrderType());

            throw new AppException("API-CU004", "orderType " + dataUpdate.getOrderType() + " not exist", lstError);
        }

        //check customer bus type
        if (!DataUtil.isNullOrEmpty(dataUpdate.getCustomerBusType())) {
            boolean isExistCustomerBusType = globalListRepo.isExist(dataUpdate.getCustomerBusType(), Const.GLOBAL_LIST_CODE.CUSTOMER_BUS_TYPE);
            if (!isExistCustomerBusType) {
                lstError.add(dataUpdate.getCustomerBusType());

                throw new AppException("API-CU005", "customerBusType " + dataUpdate.getCustomerBusType() + " not exist", lstError);
            }
        }

        // check customerType
//        if (!DataUtil.isNullOrEmpty(dataUpdate.getCustomerType())) {
//            boolean isExistCustomerType = globalListRepo.isExist(dataUpdate.getCustomerType(), Const.GLOBAL_LIST_CODE.CUSTOMER_TYPE);
//            if (!isExistCustomerType) {
//                List<String> lstError = new ArrayList<>();
//                lstError.add(dataUpdate.getCustomerType());
//
//                throw new AppException("API-CU006", "customerType " + dataUpdate.getCustomerType() + " not exist", lstError);
//            }
//        }

        //check customer name
        if (DataUtil.isStringNullOrEmpty(dataUpdate.getCustomerName())) {
            throw new AppException("API-CU007", "customerName is required");
        }

        if (dataUpdate.getCustomerName().length() > 500) {
            throw new AppException("API-CU008", "customerName length cannot exceed 500 characters");
        }

        //customer identity no
        if (!DataUtil.isNullOrEmpty(dataUpdate.getCustomerIdentityNo())) {
            boolean isExistCustomerIdentityType = globalListRepo.isExist(dataUpdate.getCustomerIdentityType(), Const.GLOBAL_LIST_CODE.ID_TYPE);
            if (!isExistCustomerIdentityType) {
                lstError.add(dataUpdate.getCustomerIdentityType());
                throw new AppException("API-CU021", "Customer identity not exist", lstError);
            }
        }

        //customer identity no
        if (!DataUtil.isNullOrEmpty(dataUpdate.getCustomerIdentityNo()) && dataUpdate.getCustomerIdentityNo().length() > 30) {
            throw new AppException("API-CU009", "customerIdentityNo length cannot exceed 30 characters");
        }

        //medical permission no
        if (!DataUtil.isNullOrEmpty(dataUpdate.getMedicalPermissionNo()) && dataUpdate.getMedicalPermissionNo().length() > 50) {
            throw new AppException("API-CU010", "medicalPermissionNo length cannot exceed 50 characters");
        }

        //check birthDate
        if (!DataUtil.isStringNullOrEmpty(dataUpdate.getBirthDate()) && !DateUtil.isDate(dataUpdate.getBirthDate(), Const.DATE_FORMAT)) {
            throw new AppException("API-CU011", "birthDate format must be yyyy-mm-dd");
        }

        //check areaCode
        boolean isExistAreaCode = areaRepo.isExist(dataUpdate.getAreaCode());
        if (!isExistAreaCode) {
            lstError.add(dataUpdate.getAreaCode());

            throw new AppException("API-CU012", "areaCode " + dataUpdate.getAreaCode() + " not exist", lstError);
        }

        boolean isPrecinctAreaCode = areaRepo.isPrecinctAreaCode(dataUpdate.getAreaCode());
        if (!isPrecinctAreaCode) {
            lstError.add(dataUpdate.getAreaCode());

            throw new AppException("API-CU013", "areaCode " + dataUpdate.getAreaCode() + " is not precinct area code", lstError);
        }

        //address
        if (DataUtil.isNullOrEmpty(dataUpdate.getAddress())) {
            throw new AppException("API-CU014", "address is required");
        }
        if (dataUpdate.getAddress().length() > 500) {
            throw new AppException("API-CU015", "address length cannot exceed 500 characters");
        }

        //representName
        if (!DataUtil.isNullOrEmpty(dataUpdate.getRepresentName()) && dataUpdate.getAddress().length() > 100) {
            throw new AppException("API-CU016", "representName length cannot exceed 100 characters");
        }

        //representIdNo
        if (!DataUtil.isNullOrEmpty(dataUpdate.getRepresentIdNo()) && dataUpdate.getRepresentIdNo().length() > 30) {
            throw new AppException("API-CU017", "representIdNo length cannot exceed 30 characters");
        }

        //representTitle
        if (!DataUtil.isNullOrEmpty(dataUpdate.getRepresentTitle()) && dataUpdate.getRepresentTitle().length() > 50) {
            throw new AppException("API-CU018", "representTitle length cannot exceed 50 characters");
        }

        //check represent tel
        if (!DataUtil.isNullOrEmpty(dataUpdate.getRepresentTel())) {
            boolean isTel = DataUtil.checkPhone(dataUpdate.getRepresentTel());
            if (!isTel) {
                lstError.add(dataUpdate.getRepresentTel());

                throw new AppException("API-CU019", "representTel " + dataUpdate.getRepresentTel() + " is not phone number", lstError);
            }
        }

        //check represent email
        if (!DataUtil.isNullOrEmpty(dataUpdate.getRepresentEmail())) {
            boolean isEmail = DataUtil.isValidEmail(dataUpdate.getRepresentEmail());
            if (!isEmail) {
                lstError.add(dataUpdate.getRepresentEmail());

                throw new AppException("API-CU020", "representEmail " + dataUpdate.getRepresentEmail() + " is not email", lstError);
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Update customer info">
    private void updateCustomerInfo(Customer customer, CustomerUpdateSdi dataUpdate) {
        AreaFullDTO precinct = areaRepo.getAreaByCode(dataUpdate.getAreaCode());
        AreaFullDTO district = areaRepo.getAreaByCode(precinct.getParentCode());
        AreaFullDTO province = areaRepo.getAreaByCode(district.getParentCode());

        customer.setCustomerBusType(dataUpdate.getCustomerBusType());
        customer.setCustomerType(dataUpdate.getCustomerType());
        customer.setName(dataUpdate.getCustomerName());

        if (!DataUtil.isNullOrEmpty(dataUpdate.getBirthDate())) {
            customer.setBirthDate(DateUtil.string2Date(dataUpdate.getBirthDate(), Const.DATE_FORMAT));
        }

        customer.setTel(dataUpdate.getTel());
        customer.setEmail(dataUpdate.getEmail());
        customer.setAddress(dataUpdate.getAddress());
        customer.setAreaCode(dataUpdate.getAreaCode());
        customer.setProvince(province.getAreaCode());
        customer.setDistrict(district.getAreaCode());
        customer.setPrecinct(precinct.getAreaCode());
        customer.setRepresentName(dataUpdate.getRepresentName());
        customer.setRepresentIdNo(dataUpdate.getRepresentIdNo());
        customer.setRepresentTitle(dataUpdate.getRepresentTitle());
        customer.setRepresentTel(dataUpdate.getRepresentTel());
        customer.setRepresentEmail(dataUpdate.getRepresentEmail());
        customer.setUpdateDatetime(new Date());
        customer.setUpdateUser(Const.ADMIN);

        customerRepo.save(customer);
    }
    //</editor-fold>

    private void insertCustomerOrder(Long customerId, String orderType) {
        CustomerOrder co = new CustomerOrder();
        co.setCustomerId(customerId);
        co.setOrderType(orderType);
        co.setStatus(Const.ORDER_STATUS.EXECUTED);
        co.setOrderActionTypeId(Const.ORDER_ACTION_TYPE.AFTER_SALE_CHANGE_INFOR);
        co.setCreateUser("ADMIN");
        co.setCreateDatetime(new Date());

        customerOrderRepo.save(co);
    }

    @Override
    public CustomerUpdateSdo update(CustomerUpdateSdi dataUpdate) {
        validDataUpdate(dataUpdate);

        CustomerUpdateSdo result = new CustomerUpdateSdo();

        Optional<Customer> optional = customerRepo.findById(dataUpdate.getCustomerId());

        if (optional.isPresent()) {
            Customer customer = optional.get();

            //update customer
            updateCustomerInfo(customer, dataUpdate);

            //update medical permission no
            customerExtMedicalRepo.updateMedicalPermissionNo(dataUpdate.getMedicalPermissionNo(), customer.getCustomerId());

            //update customer identity no
            customerIdentityRepo.updateIdentityNo(dataUpdate.getCustomerIdentityNo(), customer.getCustomerId());

            //insert customer order
//            insertCustomerOrder(customer.getCustomerId(), dataUpdate.getOrderType());

            result.setCustomerId(customer.getCustomerId());
        }

        return result;
    }
}
