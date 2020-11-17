package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.ProductSaleExpenses;

@Repository
public interface ProductSaleExpRepo extends JpaRepository<ProductSaleExpenses, Long>, ProductSaleExpRepoCustom{
}
