package vt.qlkdtt.yte.excel.hssf;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.*;

public class GetDataFromHSSFFile {

    public static Map<Integer,Object[]> getDataFromHSSFFile(String name){
        Map<Integer,Object[]> map = new TreeMap<>();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HSSFWorkbook hssfWorkbook = null;
        try {
            hssfWorkbook = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        Iterator<Row> rowIterator = hssfSheet.iterator();
        Integer key = 0;
        rowIterator.next();
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List<Object> list = new ArrayList<>();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                System.out.println(cell);
                list.add(cell);
            }
            map.put(key++, list.toArray());
        }
        System.out.println(map.toString());
        return map;
    }
}
