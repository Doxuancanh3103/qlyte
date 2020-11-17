package vt.qlkdtt.yte.repository;

public interface PartnerRevenueSharedRepoCustom {
    void changeStatusByProductId(String status, long productId);

    void changeStatusByProductOfferId(String status, long productOfferId);

    boolean isExist(long prsId);
}
