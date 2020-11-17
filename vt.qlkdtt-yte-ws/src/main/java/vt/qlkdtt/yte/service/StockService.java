package vt.qlkdtt.yte.service;

import vt.qlkdtt.yte.dto.StockDTO;
import vt.qlkdtt.yte.service.sdi.StockInsertSdi;
import vt.qlkdtt.yte.service.sdi.StockUpdateSdi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StockService {
    Object insert(StockInsertSdi stockInsertSdi) throws IOException;
    boolean isExist(Long stockId);
    StockDTO delete(Long stockId);
    StockDTO getStockById(Long stockId);
    StockDTO update(StockUpdateSdi stockUpdateSdi);
    StockDTO getStockByIdVer2(Long stockId);
    List<StockDTO> getStocks();
    Map<Integer, Object[]> putDataToHSSFFile(Map<Integer, Object[]> map,String name);
    Map<Integer,Object[]> getStockInfo();
    Map<Integer,Object[]> importDataToTable(Map<Integer,Object[]> map);
}
