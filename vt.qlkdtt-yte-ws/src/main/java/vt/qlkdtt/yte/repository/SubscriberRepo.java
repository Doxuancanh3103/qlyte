package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.Subscriber;

@Repository
public interface SubscriberRepo extends JpaRepository<Subscriber, Long> {
}
