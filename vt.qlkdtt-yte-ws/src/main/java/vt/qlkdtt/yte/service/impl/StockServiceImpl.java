package vt.qlkdtt.yte.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.domain.Stock;
import vt.qlkdtt.yte.domain.StockGood;
import vt.qlkdtt.yte.dto.StockDTO;
import vt.qlkdtt.yte.excel.hssf.PutDataToHSSFFile;
import vt.qlkdtt.yte.repository.StockGoodRepo;
import vt.qlkdtt.yte.repository.StockRepo;
import vt.qlkdtt.yte.service.StockService;
import vt.qlkdtt.yte.service.sdi.StockInsertSdi;
import vt.qlkdtt.yte.service.sdi.StockUpdateSdi;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepo stockRepo;
    @Autowired
    StockGoodRepo stockGoodRepo;
    @Override
    public Object insert(StockInsertSdi stockInsertSdi) throws IOException {
        Stock stock = new Stock();
        stock.setCreatDate(stockInsertSdi.getCreatDate());
        stock.setStockName(stockInsertSdi.getStockName());
        stockRepo.save(stock);
        Object[] object = new Object[2];
        object[0] = stockInsertSdi.getStockName();
        object[1] = stockInsertSdi.getCreatDate();
        return object;
    }

    @Override
    public boolean isExist(Long stockId) {
        return stockRepo.isExist(stockId);
    }

    @Override
    public StockDTO delete(Long stockId) {
        return stockRepo.delete(stockId);
    }

    @Override
    public StockDTO getStockById(Long stockId) {
        return stockRepo.getStockById(stockId);
    }

    @Override
    public StockDTO update(StockUpdateSdi stockUpdateSdi) {
        Stock stock = new Stock();
        stock.setStockId(stockUpdateSdi.getStockId());
        stock.setStockName((stockUpdateSdi.getStockName()));
        stock.setCreatDate(stockUpdateSdi.getCreatDate());
        stockRepo.save(stock);

        return this.getStockById(stockUpdateSdi.getStockId());
    }

    @Override
    public StockDTO getStockByIdVer2(Long stockId) {
        return stockRepo.getStockByIdVer2(stockId);
    }

    @Override
    public List<StockDTO> getStocks() {
        return stockRepo.getStocks();
    }

    @Override
    public Map<Integer, Object[]> putDataToHSSFFile(Map<Integer, Object[]> map,String name) {
        return PutDataToHSSFFile.putDataToHSSFFile(map,name);
    }

    @Override
    public Map<Integer, Object[]> getStockInfo() {
        return stockRepo.getStockInfo();
    }

    @Override
    public Map<Integer,Object[]> importDataToTable(Map<Integer, Object[]> map) {
        Set<Integer> keys = map.keySet();
        for (Integer key:keys){
            Object[] objects = map.get(key);
            if (stockRepo.isExist(Long.parseLong(objects[0].toString().split("\\.")[0]))){
                System.out.println("Khong insert");
            } else{
                Stock stock = new Stock();
                System.out.println(objects[0].toString());
                System.out.println(objects[1].toString());
                System.out.println(objects[2].toString());
                stock.setStockId(Long.parseLong(objects[0].toString().split("\\.")[0]));
                stock.setStockName(objects[1].toString());
                stock.setCreatDate(DateTime.parse(objects[7].toString()).toDate());
                stockRepo.save(stock);
            }

            if (objects[3] == null){
                System.out.println("Bo");
            } else if (stockGoodRepo.isExistGoodCode(Long.parseLong(objects[3].toString().split("\\.")[0]))){
                System.out.println("Khong insert");
            }else{
                StockGood stockGood = new StockGood();
                stockGood.setGoodCode(Long.parseLong(objects[0].toString().split("\\.")[0]));
                if (objects[4]!= null){
                    stockGood.setGoodName(objects[4].toString());
                }
                if (objects[5]!= null){
                    stockGood.setGoodQuantity(Long.parseLong(objects[0].toString().split("\\.")[0]));
                }
                if (objects[6]!= null){
                    stockGood.setGoodPrice(Float.parseFloat(objects[6].toString()));
                }
                if (objects[7]!= null){
                    stockGood.setCreateDate(DateTime.parse(objects[7].toString()).toDate());
                }

                stockGoodRepo.save(stockGood);
            }
        }

        return map;
    }
}
