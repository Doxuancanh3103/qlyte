package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.dto.StockDTO;

import java.util.List;
import java.util.Map;

public interface StockRepoCustom {
    boolean isExist(Long stockId);
    StockDTO delete(Long stockId);
    StockDTO getStockById(Long stockId);
    StockDTO getStockByIdVer2(Long stockId);
    List<StockDTO> getStocks();
    Map<Integer,Object[]> getStockInfo();
}
