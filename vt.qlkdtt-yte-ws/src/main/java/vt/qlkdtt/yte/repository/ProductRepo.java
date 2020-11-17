package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, ProductRepoCustom {
}
