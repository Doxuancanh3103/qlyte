package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vt.qlkdtt.yte.domain.MedSaleFeeConfig;

public interface MedSaleFeeConfigRepo extends JpaRepository<MedSaleFeeConfig,Long>,MedSaleFeeConfigRepoCustom {
}
