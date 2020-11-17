package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.Partner;

@Repository
public interface PartnerRepo extends JpaRepository<Partner, Long>, PartnerRepoCustom {

}
