package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.DocumentDetail;

@Repository
public interface DocumentDetailRepo extends JpaRepository<DocumentDetail, Long> {
}
