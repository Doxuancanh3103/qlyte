package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.CustomerPropective;

@Repository
public interface CustomerPropectiveRepo extends JpaRepository<CustomerPropective, Long>, CustomerPropectiveRepoCustom {
}
