package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.Document;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
}
