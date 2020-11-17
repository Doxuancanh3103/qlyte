package vt.qlkdtt.yte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vt.qlkdtt.yte.domain.StockGood;
import vt.qlkdtt.yte.dto.StockGoodDTO;
import vt.qlkdtt.yte.dto.StockGoodFullDTO;
import vt.qlkdtt.yte.repository.StockGoodRepo;
import vt.qlkdtt.yte.repository.StockRepo;
import vt.qlkdtt.yte.service.StockGoodService;
import vt.qlkdtt.yte.service.sdi.StockGoodDeleteSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodInsertSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodTransferSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodUpdateSdi;

import javax.transaction.Transactional;
import java.text.ParseException;

@Service
@Transactional
public class StockGoodServiceImpl implements StockGoodService {

    @Autowired
    StockRepo stockRepo;
    @Autowired
    StockGoodRepo stockGoodRepo;
    @Override
    public Object insert(StockGoodInsertSdi stockGoodInsertSdi) {
        System.out.println("Insert");
        Long stockId = stockGoodInsertSdi.getStockId();
        Long goodCode = stockGoodInsertSdi.getGoodCode();
        if (!stockRepo.isExist(stockId)){
            return null;
        }
        if (stockGoodRepo.isExistStockId(stockId)&&stockGoodRepo.isExistGoodCode(goodCode)){
            return null;
        }
        StockGood stockGood = new StockGood();
        stockGood.setStockId(stockId);
        stockGood.setGoodCode(goodCode);
        stockGood.setGoodName(stockGoodInsertSdi.getGoodName());
        stockGood.setGoodPrice(stockGoodInsertSdi.getGoodPrice());
        stockGood.setGoodQuantity(stockGoodInsertSdi.getGoodQuantity());
        stockGood.setCreateDate(stockGoodInsertSdi.getCreateDate());
        System.out.println(stockGood.toString());
        stockGoodRepo.save(stockGood);
        Object[] objects = new Object[5];
        objects[0] = stockId;
        objects[1] = stockGoodInsertSdi.getGoodName();
        objects[2] = stockGoodInsertSdi.getGoodQuantity();
        objects[3] = stockGoodInsertSdi.getGoodPrice();
        objects[4] = stockGoodInsertSdi.getCreateDate();
        return objects;
    }

    @Override
    public StockGoodDTO delete(StockGoodDeleteSdi stockGoodDeleteSdi) {
        if (stockGoodRepo.isExistGoodCode(stockGoodDeleteSdi.getGoodCode())){
            return stockGoodRepo.delete(stockGoodDeleteSdi);
        }
        return null;
    }

    @Override
    public StockGoodDTO update(StockGoodUpdateSdi stockGoodUpdateSdi) {
        Long stockId = stockGoodUpdateSdi.getStockId();
        Long goodCode = stockGoodUpdateSdi.getGoodCode();
        boolean stock = stockGoodRepo.isExistStockId(stockId);
        boolean good = stockGoodRepo.isExistGoodCode(goodCode);
        if (stock==true&&good==true){
            StockGood stockGood = new StockGood();
            stockGood.setCreateDate(stockGoodUpdateSdi.getCreateDate());
            stockGood.setGoodName(stockGoodUpdateSdi.getGoodName());
            stockGood.setGoodPrice(stockGoodUpdateSdi.getGoodPrice());
            stockGood.setGoodQuantity(stockGoodUpdateSdi.getGoodQuantity());
            stockGood.setStockId(stockId);
            stockGood.setGoodCode(goodCode);
            stockGoodRepo.save(stockGood);
            return this.getStockGoodById(goodCode);
        }

        return null;
    }

    @Override
    public StockGoodDTO transfer(StockGoodTransferSdi stockGoodTransferSdi) throws ParseException {

        Long fromStockId = stockGoodTransferSdi.getFromStockId();
        Long fromGoodCode = stockGoodTransferSdi.getFromGoodCode();
        Long toStockId = stockGoodTransferSdi.getToStockId();
        StockGoodFullDTO stockGoodFullDTO = stockGoodRepo.getStockGoodByCode(fromGoodCode);
        boolean fromStock = stockGoodRepo.isExistStockId(fromStockId);
        boolean fromGood = stockGoodRepo.isExistGoodCode(fromGoodCode);
        boolean toStock = stockRepo.isExist(toStockId);
        if (fromGood&&fromStock&&toStock){
            StockGood stockGood = new StockGood();
            stockGood.setStockId(toStockId);
            stockGood.setGoodCode(fromGoodCode);
            stockGood.setGoodQuantity(stockGoodFullDTO.getGoodQuantity());
            stockGood.setGoodPrice(stockGoodFullDTO.getGoodPrice());
            stockGood.setGoodName(stockGoodFullDTO.getGoodName());
            stockGood.setCreateDate(stockGoodFullDTO.getCreateDate());
            stockGoodRepo.save(stockGood);
            return this.getStockGoodById(fromGoodCode);
        }
        return null;
    }

    @Override
    public StockGoodFullDTO getStockGoodByCode(Long goodCode) {
        return stockGoodRepo.getStockGoodByCode(goodCode);
    }

    @Override
    public StockGoodDTO getStockGoodById(Long goodCode) {
        return stockGoodRepo.getStockGoodById(goodCode);
    }

    @Override
    public boolean isExist(Long stockId) {
        return stockGoodRepo.isExistStockId(stockId);
    }
}
