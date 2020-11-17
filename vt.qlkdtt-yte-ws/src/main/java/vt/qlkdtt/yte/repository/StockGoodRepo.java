package vt.qlkdtt.yte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.domain.StockGood;

@Repository
public interface StockGoodRepo extends JpaRepository<StockGood,Long> , StockGoodRepoCustom{
}
