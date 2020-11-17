package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.CustomerExtMedical;

@Repository
public interface CustomerExtMedicalRepo extends JpaRepository<CustomerExtMedical, Long>, CustomerExtMedicalRepoCustom {
}
