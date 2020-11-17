package vt.qlkdtt.yte.repository.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.dto.StockDTO;
import vt.qlkdtt.yte.repository.StockRepoCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
@Transactional
public class StockRepoImpl implements StockRepoCustom {

    @Autowired
    EntityManager em;
    @Override
    public boolean isExist(Long stockId) {
        StringBuilder sql = new StringBuilder("SELECT Count(*) FROM stock WHERE stock_id = "+stockId);
        Query query = (Query) em.createNativeQuery(sql.toString());
        Object queryResult = query.getSingleResult();
        return DataUtil.safeToLong(queryResult)>0;
    }

    @Override
    public StockDTO delete(Long stockId) {
        StockDTO stockDTO = this.getStockById(stockId);
        StringBuilder sql = new StringBuilder("DELETE FROM stock WHERE stock_id = "+stockId);
        Query query = (Query) em.createNativeQuery(sql.toString());
        query.executeUpdate();
        return stockDTO;
    }

    @Override
    public StockDTO getStockById(Long stockId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM stock WHERE stock_id = "+stockId);
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> list = query.getResultList();
        Object[] obj = list.get(0);

        StockDTO stockDTO = new StockDTO();
        stockDTO.setStockId(Long.parseLong(obj[0].toString()));
        stockDTO.setStockName(obj[1].toString());
        stockDTO.setCreatDate(DateTime.parse(obj[2].toString()).toDate());
        return stockDTO;
    }

    @Override
    public StockDTO getStockByIdVer2(Long stockId) {
        StringBuilder sql = new StringBuilder("Select * from stock where stock_id = "+stockId);
        StockDTO result = new StockDTO();
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> objects = query.getResultList();
        if (!DataUtil.isNullOrEmpty(objects)){
            for(Object[] obj:objects){
                result = DataUtil.convertObjectsToClass(obj,result);
            }
        }
        return result;
    }

    @Override
    public List<StockDTO> getStocks() {
        StringBuilder sql = new StringBuilder("select * from Stock");
        List<StockDTO> result = new ArrayList<>();

        Query query = em.createNativeQuery(sql.toString());

        List<Object[]> objects = query.getResultList();
        if(!DataUtil.isNullOrEmpty(objects)){
            for(Object[] obj : objects){
                System.out.println(obj[2]);
                result.add(DataUtil.convertObjectsToClass(obj,new StockDTO()));
                System.out.println(result);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, Object[]> getStockInfo() {
        Map<Integer,Object[]> map = new TreeMap<>();
        StringBuilder sql = new StringBuilder("Select stock.stock_id, stock_name,creat_date,good_code,good_name," +
                                              "good_quantity,good_price,create_date\n" +
                                     "from stock left join stock_good on stock.stock_id = stock_good.stock_id\n");
        Query query = em.createNativeQuery(sql.toString());

        List<Object[]> list = query.getResultList();
        for (int i = 0 ; i < list.size() ; i++){
            map.put(i,list.get(i));
        }
        return map;
    }


}
