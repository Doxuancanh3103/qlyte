package vt.qlkdtt.yte.repository;

public interface CustomerIdentityRepoCustom {
    boolean isExist(String customerIdentityNo);

    void updateIdentityNo(String identityNo, Long customerId);
}
