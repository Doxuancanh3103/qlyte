package vt.qlkdtt.yte.repository;

public interface CustomerExtMedicalRepoCustom {
    boolean isExist(String medicalPermissionNo);

    void updateMedicalPermissionNo(String medicalPermissionNo, Long customerId);
}
