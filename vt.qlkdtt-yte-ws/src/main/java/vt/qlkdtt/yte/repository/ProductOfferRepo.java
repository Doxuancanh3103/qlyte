package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.ProductOffer;

@Repository
public interface ProductOfferRepo extends JpaRepository<ProductOffer, Long>, ProductOfferRepoCustom {
}
