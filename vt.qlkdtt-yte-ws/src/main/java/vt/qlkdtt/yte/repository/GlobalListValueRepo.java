package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.GlobalListValue;

@Repository
public interface GlobalListValueRepo extends JpaRepository<GlobalListValue, Long>, GlobalListValueRepoCustom {
}
