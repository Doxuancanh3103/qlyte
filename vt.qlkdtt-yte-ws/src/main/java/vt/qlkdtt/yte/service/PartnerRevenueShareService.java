package vt.qlkdtt.yte.service;

import org.springframework.stereotype.Service;

@Service
public interface PartnerRevenueShareService {
    void changeStatusByProductId(String status, long productId);

    void changeStatusByProductOfferId(String status, long productOfferId);

    boolean isExist(long prsId);
}
