package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.DocumentMapping;

@Repository
public interface DocumentMappingRepo extends JpaRepository<DocumentMapping, Long>, DocMappingRepoCustom {
}
