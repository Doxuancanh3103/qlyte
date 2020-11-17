package vt.qlkdtt.yte.repository.impl;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.dto.StockGoodDTO;
import vt.qlkdtt.yte.dto.StockGoodFullDTO;
import vt.qlkdtt.yte.repository.StockGoodRepoCustom;
import vt.qlkdtt.yte.service.sdi.StockGoodDeleteSdi;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class StockGoodRepoImpl implements StockGoodRepoCustom {

    @Autowired
    EntityManager em;

    @Override
    public boolean isExistGoodCode(Long goodCode) {
        StringBuilder sql = new StringBuilder("SELECT Count(*) FROM stock_good WHERE good_code = "+goodCode);
        Query query = (Query) em.createNativeQuery(sql.toString());
        Object queryResult = query.getSingleResult();
        return DataUtil.safeToLong(queryResult)>0;
    }

    @Override
    public boolean isExistStockId(Long stockId) {
        StringBuilder sql = new StringBuilder("SELECT Count(*) FROM stock_good WHERE stock_id = "+stockId);
        Query query = em.createNativeQuery(sql.toString());
        Object queryResult = query.getSingleResult();
        return DataUtil.safeToLong(queryResult)>0;
    }

    @Override
    public StockGoodDTO delete(StockGoodDeleteSdi stockGoodDeleteSdi) {
        StringBuilder sql =
                new StringBuilder("DELETE FROM stock_good WHERE good_code="+
                        stockGoodDeleteSdi.getGoodCode());
        StockGoodDTO stockGoodDTO = this.getStockGoodById(stockGoodDeleteSdi.getGoodCode());
        Query query = em.createNativeQuery(sql.toString());
        query.executeUpdate();
        return stockGoodDTO;
    }

    @Override
    public StockGoodFullDTO getStockGoodByCode(Long goodCode) {
        StringBuilder strQuery = new StringBuilder();

        strQuery.append(" SELECT ");
        strQuery.append(" stock_id,good_code,good_name,good_quantity,good_price,create_date ");
        strQuery.append(" FROM ");
        strQuery.append(" stock_good ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append("  AND good_code = "+goodCode);

        Query query = em.createNativeQuery(strQuery.toString(),StockGoodFullDTO.class);
        List<StockGoodFullDTO> list = query.getResultList();
        return list.get(0);
    }

    @Override
    public StockGoodDTO getStockGoodById(Long goodCode) {
        StringBuilder strQuery = new StringBuilder();

        strQuery.append(" SELECT ");
        strQuery.append(" stock_id,good_code,good_name,good_quantity,good_price,create_date ");
        strQuery.append(" FROM ");
        strQuery.append(" stock_good ");
        strQuery.append(" WHERE 1=1 ");
        strQuery.append("  AND good_code = "+goodCode);
        Query query = em.createNativeQuery(strQuery.toString());
        List<Object[]> list = query.getResultList();
        Object[] obj = list.get(0);
        StockGoodDTO stockGoodDTO = new StockGoodDTO();

        stockGoodDTO.setStockId(Long.parseLong(obj[0].toString()));
        stockGoodDTO.setGoodCode(Long.parseLong(obj[1].toString()));
        stockGoodDTO.setGoodName(obj[2].toString());
        stockGoodDTO.setGoodQuantity(Long.parseLong(obj[3].toString()));
        stockGoodDTO.setGoodPrice(Float.parseFloat(obj[4].toString()));
        stockGoodDTO.setCreateDate(DateTime.parse(obj[5].toString()).toDate());
        return stockGoodDTO;
    }
}
