package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.domain.CustomerOrder;

@Service
public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Long>, CustomerOrderRepoCustom {

}
