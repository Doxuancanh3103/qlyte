package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.dto.StockDTO;
import vt.qlkdtt.yte.excel.hssf.GetDataFromHSSFFile;
import vt.qlkdtt.yte.service.StockGoodService;
import vt.qlkdtt.yte.service.StockService;
import vt.qlkdtt.yte.service.sdi.StockDeleteSdi;
import vt.qlkdtt.yte.service.sdi.StockInsertSdi;
import vt.qlkdtt.yte.service.sdi.StockUpdateSdi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "stock")
@Api(value = "Stock")
public class StockController {
    @Autowired
    StockService stockService;
    @Autowired
    StockGoodService stockGoodService;
    @PostMapping(value = "insert")
    @ApiOperation(value = "Insert stock")
    public ResponseEntity<Object> insert(@RequestBody StockInsertSdi stockInsertSdi) throws IOException {
//        CreateXSSFWorkbook.createXSSFWorkbook("canh.xlsx");
        return ResponseEntity.status(HttpStatus.OK).body(stockService.insert(stockInsertSdi));
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete stock by id")
    public ResponseEntity<Object> delete(@RequestBody StockDeleteSdi stockDeleteSdi){
        if (stockGoodService.isExist(stockDeleteSdi.getStockId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kho dang chua hanh");
        }
        if (stockService.isExist(stockDeleteSdi.getStockId())){
            return ResponseEntity.status(HttpStatus.OK).body(stockService.delete(stockDeleteSdi.getStockId()));
        }
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "update")
    @ApiOperation(value = "update stock")
    public ResponseEntity<Object> update(@RequestBody StockUpdateSdi stockUpdateSdi){
        if (stockService.isExist(stockUpdateSdi.getStockId())){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(stockService.update(stockUpdateSdi));
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "writeToExcel")
    @ApiOperation(value = "write to Excel")
    public ResponseEntity<Object> writeToHSSFExcel(@RequestParam String filename){
        System.out.println(filename);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stockService.putDataToHSSFFile(stockService.getStockInfo(),filename));
    }

    @PutMapping(value = "readExcel")
    @ApiOperation(value = "read Excel")
    public Map<Integer,Object[]> importDataToTable(@RequestParam String filename){
        return stockService.importDataToTable(GetDataFromHSSFFile.getDataFromHSSFFile(filename));
    }

    @GetMapping(value = "getStockById")
    @ApiOperation(value = "get-stock-by-id")
    public ResponseEntity<StockDTO> getStockByID(@RequestParam Long stockId){
        return ResponseEntity.status(HttpStatus.OK).body(stockService.getStockById(stockId));
    }

    @GetMapping(value = "get-stocks")
    @ApiOperation(value = "get stocks")
    public ResponseEntity<Object> getStocks(){
        List<StockDTO> result = stockService.getStocks();
        if (!DataUtil.isNullOrEmpty(result)){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "get-stock-by-id-ver2")
    @ApiOperation(value = "get stock by id ver2")
    public ResponseEntity<Object> getStockByIdVer2(@RequestParam Long stockId){
        StockDTO stockDTO = stockService.getStockByIdVer2(stockId);
        if (stockDTO == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(stockDTO);
    }
}
