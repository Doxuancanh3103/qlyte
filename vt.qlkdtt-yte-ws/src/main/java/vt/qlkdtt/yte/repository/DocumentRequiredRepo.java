package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.DocumentRequired;

@Repository
public interface DocumentRequiredRepo extends JpaRepository<DocumentRequired, Long>, DocumentRequiredRepoCustom{
}
