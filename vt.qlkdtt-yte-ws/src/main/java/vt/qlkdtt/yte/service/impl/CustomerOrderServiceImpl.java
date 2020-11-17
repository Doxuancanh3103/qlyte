package vt.qlkdtt.yte.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.DateUtil;
import vt.qlkdtt.yte.domain.CustomerOrder;
import vt.qlkdtt.yte.domain.CustomerOrderConfig;
import vt.qlkdtt.yte.domain.Subscriber;
import vt.qlkdtt.yte.repository.CustomerOrderConfigRepo;
import vt.qlkdtt.yte.repository.CustomerOrderRepo;
import vt.qlkdtt.yte.repository.SubscriberRepo;
import vt.qlkdtt.yte.service.CustomerOrderService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdi.CustomerOrderExtDueDateSdi;
import vt.qlkdtt.yte.service.sdi.CustomerOrderSearchSdi;
import vt.qlkdtt.yte.service.sdo.CustomerOrderDetailSearchSdo;
import vt.qlkdtt.yte.service.sdo.CustomerOrderSearchSdo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {
    @Autowired
    CustomerOrderRepo customerOrderRepo;

    @Autowired
    SubscriberRepo subscriberRepo;

    @Autowired
    CustomerOrderConfigRepo customerOrderConfigRepo;

    @Override
    @Transactional
    public Page<CustomerOrderSearchSdo> searchCustomerOrder(CustomerOrderSearchSdi customerOrderSearchSdi, Pageable pageable) {
        return customerOrderRepo.searchCustomerOrder(customerOrderSearchSdi, pageable);
    }

    @Override
    @Transactional
    public CustomerOrderDetailSearchSdo searchOrderDetail(long customerOrderId) {
        return customerOrderRepo.searchOrderDetail(customerOrderId);
    }

    @Override
    public boolean extendOrderDueDate(CustomerOrderExtDueDateSdi customerOrderExtDueDateSdi) {
        List<String> lstError = new ArrayList<>();

        validateExtendOrderDueDate(customerOrderExtDueDateSdi);

        CustomerOrder customerOrder = customerOrderRepo.searchById(customerOrderExtDueDateSdi.getCustomerOrderId());
        if (customerOrder == null || DataUtil.isNullOrZero(customerOrder.getCustomerOrderId())) {
            throw new AppException("API-CO009", "Customer order id not exist");
        }
        customerOrder.setUpdateDatetime(new Date());
        Date extDueDate = DateUtil.safeStringToDateByPattern(customerOrderExtDueDateSdi.getExtendDueDate());
        if (DateUtil.compareDateToDate(extDueDate, new Date()) < 0) {
            throw new AppException("API-CO010", "Extend due date can not less than current date");
        }

        if (!DataUtil.isNullObject(customerOrder.getExtDueDatetime())) {
            throw new AppException("API-CO011", "Can not extend customer order already extend");
        }

        String orderTypeId = customerOrder.getOrderType();
        Long productId = customerOrder.getProductId();

        CustomerOrderConfig coc = customerOrderConfigRepo.search(orderTypeId, Const.ORDER_ACTION_TYPE.CONNECT_WAIT_ACCEPTANCE, productId);

        Date extendDueDate = DateUtil.string2Date(customerOrderExtDueDateSdi.getExtendDueDate(), Const.DATE_FORMAT);
        Date maxExtendDate = DateUtil.addDate(customerOrder.getDueDatetime(), DataUtil.safeToInt(coc.getExtendTime()));
        if (DateUtil.compareDateToDate(maxExtendDate, extendDueDate) < 0) {
            lstError.add(String.valueOf(coc.getExtendTime()));
            throw new AppException("API-CO012", "Just extend {0} days", lstError);
        }

        customerOrder.setExtDueDatetime(extDueDate);
        customerOrderRepo.save(customerOrder);

        return true;
    }

    @Override
    public boolean acceptanceCharge(long customerOrderId) {
        if (DataUtil.isNullOrZero(customerOrderId)) {
            throw new AppException("API-CO012", "customerOrderId khong duoc de trong");
        }
        CustomerOrder customerOrder = customerOrderRepo.searchById(customerOrderId);
        if (customerOrder == null || DataUtil.isNullOrZero(customerOrder.getCustomerOrderId())) {
            throw new AppException("API-CO009", "customerOrderId khong ton tai");
        }
        // trang thai dau noi - nghiem thu tinh cuoc
        if (customerOrder.getOrderActionTypeId() != null && !"3".equalsIgnoreCase(customerOrder.getOrderActionTypeId())) {
            throw new AppException("API-CO013", "trang thai yeu cau khong phai Dau noi - nghiem thu tinh cuoc");
        }
        customerOrder.setUpdateDatetime(new Date());
        // thuc hien tich hop bccs nghiem thu tinh cuoc
        customerOrder.setOrderActionTypeId(Const.ORDER_ACTION_TYPE.CONNECT_ACCEPTANCED);
        customerOrder.setStatus(Const.ORDER_STATUS.EXECUTED);
        customerOrderRepo.save(customerOrder);

        //cập nhật subscriber
        Subscriber s = subscriberRepo.getOne(customerOrder.getSubscriberId());

        Long volBase = s.getVolBase();
        Long volPromotion = s.getVolPromotion();

        Date expireDate = DateUtil.addMonth(new Date(), (int) (volBase + volPromotion));

        s.setStatus(Const.SUBSCRIBER_STATUS.ACTIVE);
        s.setEffectiveDate(new Date());
        s.setExpireDate(expireDate);

        subscriberRepo.save(s);


        return true;
    }

    @Override
    public boolean rejectConnect(long customerOrderId) {
        if (DataUtil.isNullOrZero(customerOrderId)) {
            throw new AppException("API-CO012", "customerOrderId khong duoc de trong");
        }
//        CustomerOrder customerOrder = customerOrderRepo.findById(customerOrderId);
        Optional<CustomerOrder> optionalCO = customerOrderRepo.findById(customerOrderId);
        if (optionalCO.isPresent()) {
            CustomerOrder customerOrder = optionalCO.get();

            // trang thai dau noi - nghiem thu tinh cuoc
            if (customerOrder.getOrderActionTypeId() != null && !"3".equalsIgnoreCase(customerOrder.getOrderActionTypeId())) {
                throw new AppException("API-CO013", "trang thai yeu cau khong phai Dau noi - nghiem thu tinh cuoc");
            }
            customerOrder.setUpdateDatetime(new Date());
            // reject yeu cau dau noi
            customerOrder.setOrderActionTypeId(Const.ORDER_ACTION_TYPE.CONNECT_CANCEL);
            customerOrder.setStatus(Const.ORDER_STATUS.EXECUTED);
            customerOrder = customerOrderRepo.save(customerOrder);

            return true;
        } else {
            throw new AppException("API-CO009", "customerOrderId khong ton tai");
        }
    }

    public void validateExtendOrderDueDate(CustomerOrderExtDueDateSdi customerOrderExtDueDateSdi) {
        if (customerOrderExtDueDateSdi == null) {
            throw new AppException("API-CO006", "Bat buoc truyen du lieu");
        } else if (DataUtil.isStringNullOrEmpty(customerOrderExtDueDateSdi.getCustomerOrderId())) {
            throw new AppException("API-CO007", "Bat buoc nhap customerOrderId");
        } else if (DataUtil.isStringNullOrEmpty(customerOrderExtDueDateSdi.getExtendDueDate())) {
            throw new AppException("API-CO008", "Bat buoc nhap extendDueDate");
        }
    }

}
