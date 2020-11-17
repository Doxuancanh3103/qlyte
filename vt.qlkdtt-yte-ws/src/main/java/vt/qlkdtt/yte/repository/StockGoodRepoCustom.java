package vt.qlkdtt.yte.repository;

import vt.qlkdtt.yte.dto.StockGoodDTO;
import vt.qlkdtt.yte.dto.StockGoodFullDTO;
import vt.qlkdtt.yte.service.sdi.StockGoodDeleteSdi;

public interface StockGoodRepoCustom {

    boolean isExistGoodCode(Long goodCode);
    boolean isExistStockId(Long stockId);
    StockGoodDTO delete(StockGoodDeleteSdi stockGoodDeleteSdi);
    StockGoodFullDTO getStockGoodByCode(Long goodCode);
    StockGoodDTO getStockGoodById(Long goodCode);
}
