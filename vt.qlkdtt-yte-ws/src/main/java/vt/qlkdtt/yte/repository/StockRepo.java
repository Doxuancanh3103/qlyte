package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.Stock;

@Repository
public interface StockRepo extends JpaRepository<Stock,Long> ,StockRepoCustom{
}
