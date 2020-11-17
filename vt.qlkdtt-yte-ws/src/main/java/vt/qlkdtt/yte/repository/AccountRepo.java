package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.CustomerAccount;

@Repository
public interface AccountRepo extends JpaRepository<CustomerAccount, Long>, AccountRepoCustom {
}
