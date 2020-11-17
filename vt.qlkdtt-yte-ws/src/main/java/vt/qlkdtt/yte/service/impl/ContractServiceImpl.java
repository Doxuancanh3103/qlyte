package vt.qlkdtt.yte.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.common.MessageResource;
import vt.qlkdtt.yte.domain.CustomerAccount;
import vt.qlkdtt.yte.domain.CustomerContract;
import vt.qlkdtt.yte.domain.CustomerOrder;
import vt.qlkdtt.yte.repository.AccountRepo;
import vt.qlkdtt.yte.repository.ContractRepo;
import vt.qlkdtt.yte.repository.CustomerOrderRepo;
import vt.qlkdtt.yte.repository.SubscriberRepo;
import vt.qlkdtt.yte.service.ContractService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.ContractSearchSdi;
import vt.qlkdtt.yte.service.sdi.ContractUpdateSdi;
import vt.qlkdtt.yte.service.sdo.ContractFindByIdSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchConnectSdo;
import vt.qlkdtt.yte.service.sdo.ContractSearchSdo;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ContractServiceImpl implements ContractService {
    @Autowired
    ContractRepo contractRepo;

    @Autowired
    SubscriberRepo subscriberRepo;

    @Autowired
    CustomerOrderRepo customerOrderRepo;

    @Autowired
    private MessageResource messageResource;

    @Autowired
    AccountRepo accountRepo;

    //<editor-fold desc="Valid input search">
    private void validInputSearch(ContractSearchSdi contractSearchSdi) {
        String fromDateStr = contractSearchSdi.getFromDate();
        String toDateStr = contractSearchSdi.getToDate();

        if (!DataUtil.isNullOrEmpty(fromDateStr) && !DateUtil.isDate(fromDateStr, Const.DATE_FORMAT)) {
            throw new AppException("API-CT001", "fromDate format must be yyyy-MM-dd");
        }

        if (!DataUtil.isNullOrEmpty(toDateStr) && !DateUtil.isDate(toDateStr, Const.DATE_FORMAT)) {
            throw new AppException("API-CT002", "toDate format must be yyyy-MM-dd");
        }

        if (!DataUtil.isNullOrEmpty(fromDateStr) && !DataUtil.isNullOrEmpty(toDateStr)) {
            Date fromDate = DateUtil.string2Date(fromDateStr, Const.DATE_FORMAT);
            Date toDate = DateUtil.string2Date(toDateStr, Const.DATE_FORMAT);

            int countDay = DateUtil.getDayBetween2DateString(fromDateStr, toDateStr, Const.DATE_FORMAT);

            if (countDay > 31) {
                throw new AppException("API-CT003", "Searched only for 1 month");
            }
        }
    }
    //</editor-fold>

    @Override
    @Transactional
    public Page<ContractSearchSdo> searchContract(ContractSearchSdi contractSearchSdi, Pageable pageable) {
        validInputSearch(contractSearchSdi);

        return contractRepo.searchContract(contractSearchSdi, pageable);
    }

    @Override
    @Transactional
    public Page<ContractSearchConnectSdo> searchContractByCustomer(ContractSearchSdi contractSearchSdi, Pageable pageable) {
        validInputSearch(contractSearchSdi);

        if (DataUtil.isNullOrZero(contractSearchSdi.getCustomerId())) {
            throw new AppException("API-CT007", "Customer id is required");
        }

        return contractRepo.searchContractConnect(contractSearchSdi, pageable);
    }

    @Override
    @Transactional
    public ContractFindByIdSdo findById(long customerContractId) {
        return contractRepo.findById(customerContractId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerContract updateContract(ContractUpdateSdi contractUpdateSdi) {
        if (DataUtil.isNullOrZero(contractUpdateSdi.getContractId())) {
            throw new AppException("API-", "contractId is required");
        }

        CustomerContract contractUpdate = contractRepo.getOne(contractUpdateSdi.getContractId());
        if (contractUpdate == null || DataUtil.isNullOrZero(contractUpdate.getContractId())) {
            throw new AppException("APR-ERR001", messageResource.getMessage("APR-ERR001"));
        }
        if (contractRepo.isExits(contractUpdateSdi.getContractNo(), contractUpdateSdi.getContractId())) {
            throw new AppException("API-CT004", "contractNo moi da ton tai tren he thong");
        }
        if(DataUtil.isNullOrEmpty(contractUpdateSdi.getOrderActionTypeId())) {
            throw new AppException("API-CT005", "orderActionTypeId is required");
        }

        contractUpdate = contractUpdateSdi.updateContract(contractUpdate);
        // cap nhat hop dong
        contractUpdate = contractRepo.save(contractUpdate);
        // cap nhat account
        Optional<CustomerAccount> optionalAccount = accountRepo.findById(contractUpdate.getCustomerAccountId());
        if (optionalAccount.isPresent()) {
            CustomerAccount account = optionalAccount.get();
            account = contractUpdateSdi.updateAccount(account);

            accountRepo.save(account);
        }

        // ghi nhan customerOrder
//        insertCustomerOrderTypeContract(contractUpdate, Const.ORDER_TYPE.CONTRACT, contractUpdateSdi.getOrderActionTypeId(), Const.ORDER_STATUS.EXECUTED);

        return contractUpdate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerContract liquidateContract(long contractId) {
        // validate co hop le de thanh ly hop dong hay khong
        // cap nhat hop dong
        CustomerContract customerContract = contractRepo.getOne(contractId);
        if (customerContract == null || DataUtil.isNullOrZero(customerContract.getContractId())) {
            throw new AppException("API-ERR001", "Id khong ton tai");
        } else if (!DataUtil.isStringNullOrEmpty(customerContract.getStatus())
                && "0".equals(customerContract.getStatus())) {
            throw new AppException("API-CT006", "Trang thai hop dong khong hop le");
        }
        customerContract.setStatus("0");
        customerContract.setUpdateDatetime(new Date());
        customerContract.setUpdateUser("ADMIN");
        CustomerContract contract = contractRepo.save(customerContract);
        // cap nhat subscriber neu co

        // ghi nhan customerOrder
        insertCustomerOrderTypeContract(customerContract, Const.ORDER_TYPE.CONTRACT, Const.ORDER_ACTION_TYPE.CONTRACT_LIQUIDATION, Const.ORDER_STATUS.EXECUTED);
        return contract;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerContract extensionContract(long contractId) {
        // validate co hop le de gia han hop dong hay khong
        // gia han hop dong
        CustomerContract customerContract = contractRepo.getOne(contractId);
        if (customerContract == null || DataUtil.isNullOrZero(customerContract.getContractId())) {
            throw new AppException("API-ERR001", "Id khong ton tai");
        } else if (!DataUtil.isStringNullOrEmpty(customerContract.getStatus())
                && "0".equals(customerContract.getStatus())) {
            throw new AppException("API-CT006", "Trang thai hop dong khong hop le");
        }
        customerContract.setStatus("1");
        customerContract.setUpdateDatetime(new Date());
        customerContract.setUpdateUser("ADMIN");
        CustomerContract contract = contractRepo.save(customerContract);
        // cap nhat subscriber neu co

        // ghi nhan customerOrder
        insertCustomerOrderTypeContract(customerContract, Const.ORDER_TYPE.CONTRACT, Const.ORDER_ACTION_TYPE.CONTRACT_EXTEND, Const.ORDER_STATUS.EXECUTED);
        return contract;
    }

    public void insertCustomerOrderTypeContract(CustomerContract contractUpdate, String orderType, String orderActionType, String status) {
        CustomerOrder customerOrder = new CustomerOrder();
        if (DataUtil.isNullOrZero(contractUpdate.getCustomerId())) {
            customerOrder.setCustomerId(contractUpdate.getCustomerId());
        }
        if (DataUtil.isNullOrZero(contractUpdate.getCustomerAccountId())) {
            customerOrder.setCustomerAccountId(contractUpdate.getCustomerAccountId());
        }
//        customerOrder.setProductId();
//        customerOrder.setSubscriberId();
//        customerOrder.setProvince();
        customerOrder.setOrderType(orderType);
        customerOrder.setOrderActionTypeId(orderActionType);
//        customerOrder.setOrderContactName();
//        customerOrder.setOrderContactTel();
//        customerOrder.setOrderContactEmail();
//        customerOrder.setOrderContactAddress();
        customerOrder.setOrderDatetime(new Date());
        customerOrder.setDueDatetime(DateUtil.addDay(new Date(), 7));
        customerOrder.setStatus(status);
//        customerOrder.setAssigneeCode();
//        customerOrder.setReporterCode();
//        customerOrder.setShopCode();
        customerOrder.setCreateUser("ADMIN");
        customerOrder.setCreateDatetime(new Date());

        customerOrderRepo.save(customerOrder);
    }

}
