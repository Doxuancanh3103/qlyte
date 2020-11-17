package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.CustomerOrderConfig;

@Repository
public interface CustomerOrderConfigRepo extends JpaRepository<CustomerOrderConfig, Long>, CustomerOrderConfigRepoCustom{
}
