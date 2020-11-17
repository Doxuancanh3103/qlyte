package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.CustomerIdentity;

@Repository
public interface CustomerIdentityRepo extends JpaRepository<CustomerIdentity, Long>, CustomerIdentityRepoCustom {
}
