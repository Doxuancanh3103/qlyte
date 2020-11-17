package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>, CustomerRepoCustom {
}
