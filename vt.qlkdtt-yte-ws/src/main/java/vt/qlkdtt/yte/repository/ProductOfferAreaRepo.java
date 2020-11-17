package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.ProductOfferArea;

@Repository
public interface ProductOfferAreaRepo extends JpaRepository<ProductOfferArea, Long>, ProductOfferAreaRepoCustom {
}
