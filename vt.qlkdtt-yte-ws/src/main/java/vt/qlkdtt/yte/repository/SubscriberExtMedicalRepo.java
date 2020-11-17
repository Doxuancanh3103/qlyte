package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.SubscriberExtMedical;

@Repository
public interface SubscriberExtMedicalRepo extends JpaRepository<SubscriberExtMedical, Long> {
}
