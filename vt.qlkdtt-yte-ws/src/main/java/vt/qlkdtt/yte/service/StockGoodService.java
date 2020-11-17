package vt.qlkdtt.yte.service;


import vt.qlkdtt.yte.dto.StockGoodDTO;
import vt.qlkdtt.yte.dto.StockGoodFullDTO;
import vt.qlkdtt.yte.service.sdi.StockGoodDeleteSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodInsertSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodTransferSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodUpdateSdi;

import java.text.ParseException;

public interface StockGoodService {
    Object insert(StockGoodInsertSdi stockGoodInsertSdi);
    StockGoodDTO delete(StockGoodDeleteSdi stockGoodDeleteSdi);
    StockGoodDTO update(StockGoodUpdateSdi stockGoodUpdateSdi);
    StockGoodDTO transfer(StockGoodTransferSdi stockGoodTransferSdi) throws ParseException;
    StockGoodFullDTO getStockGoodByCode(Long goodCode);
    StockGoodDTO getStockGoodById(Long goodCode);
    boolean isExist(Long stockId);
}
