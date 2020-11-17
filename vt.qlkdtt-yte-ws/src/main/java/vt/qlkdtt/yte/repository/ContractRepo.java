package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.domain.CustomerContract;

@Service
public interface ContractRepo extends JpaRepository<CustomerContract, Long>, ContractRepoCustom {
}
