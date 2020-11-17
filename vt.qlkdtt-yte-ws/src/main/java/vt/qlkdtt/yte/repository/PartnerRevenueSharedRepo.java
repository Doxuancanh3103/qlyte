package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.PartnerRevenueShared;

@Repository
public interface PartnerRevenueSharedRepo extends JpaRepository<PartnerRevenueShared, Long>, PartnerRevenueSharedRepoCustom{
}
