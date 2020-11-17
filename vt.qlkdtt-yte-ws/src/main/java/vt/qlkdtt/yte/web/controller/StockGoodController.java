package vt.qlkdtt.yte.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vt.qlkdtt.yte.dto.StockGoodDTO;
import vt.qlkdtt.yte.service.StockGoodService;
import vt.qlkdtt.yte.service.sdi.StockGoodDeleteSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodInsertSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodTransferSdi;
import vt.qlkdtt.yte.service.sdi.StockGoodUpdateSdi;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping(value = "stock-good")
@Api(value = "stock-good")
public class StockGoodController {

    @Autowired
    StockGoodService stockGoodService;
    @PostMapping(value = "insert")
    @ApiOperation(value = "insert stock-good")
    public ResponseEntity<Object> insert(@RequestBody StockGoodInsertSdi stockGoodInsertSdi){
        Object object = stockGoodService.insert(stockGoodInsertSdi);
        if(object == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "delete stock-good")
    public ResponseEntity<Object> delete(@RequestBody StockGoodDeleteSdi stockGoodDeleteSdi){
        StockGoodDTO stockGoodDTO = stockGoodService.delete(stockGoodDeleteSdi);
        if (stockGoodDTO == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(stockGoodDTO);
    }

    @PutMapping(value = "update")
    @ApiOperation(value = "update stock-good")
    public ResponseEntity<Object> update(@RequestBody StockGoodUpdateSdi stockGoodUpdateSdi){
        StockGoodDTO stockGoodDTO = stockGoodService.update(stockGoodUpdateSdi);
        if (stockGoodDTO == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(stockGoodDTO);
    }

    @PutMapping(value = "transfer")
    @ApiOperation(value = "transfer stock-good")
    public ResponseEntity<Object> transfer(@RequestBody StockGoodTransferSdi stockGoodTransferSdi) throws ParseException {
       StockGoodDTO stockGoodDTO = stockGoodService.transfer(stockGoodTransferSdi);
       if (stockGoodDTO == null){
           System.out.println("Null");
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.status(HttpStatus.OK).body(stockGoodDTO);
    }

    @GetMapping(value = "getStockGoodByCode")
    @ApiOperation(value = "get Stock good by code")
    public ResponseEntity<Object> getStockGoodByCode(@RequestParam(value = "getStockGoodByCode",required = false) Long goodCode){
        if (stockGoodService.isExist(goodCode)){
            return ResponseEntity.status(HttpStatus.OK).body(stockGoodService.getStockGoodByCode(goodCode));
        }
        return ResponseEntity.noContent().build();
    }
}
