package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.DocumentDetailTemp;

@Repository
public interface DocumentDetailTempRepo extends JpaRepository<DocumentDetailTemp, Long>, DocumentDetailTempRepoCustom {
}
