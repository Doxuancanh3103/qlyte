package vt.qlkdtt.yte.excel.hssf;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PutDataToHSSFFile {

    public static Map<Integer, Object[]> putDataToHSSFFile(Map<Integer,Object[]> map, String name){

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Stock Info");

        HSSFRow row;
        row = hssfSheet.createRow(0);
        HSSFCell hssfCell = row.createCell(0);
        hssfCell.setCellValue("Stock Id");

        hssfCell = row.createCell(1);
        hssfCell.setCellValue("Stock name");

        hssfCell = row.createCell(2);
        hssfCell.setCellValue("create date stock");

        hssfCell = row.createCell(3);
        hssfCell.setCellValue("Good code");

        hssfCell = row.createCell(4);
        hssfCell.setCellValue("Good name");

        hssfCell = row.createCell(5);
        hssfCell.setCellValue("Good quantity");

        hssfCell = row.createCell(6);
        hssfCell.setCellValue("Good price");

        hssfCell = row.createCell(7);
        hssfCell.setCellValue("Create date good stock");

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream((new File(name)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Set<Integer> keys = map.keySet();
        int rowId = 1;
        for (Integer key:keys){
            row = hssfSheet.createRow(rowId++);
            Object[] objectArr = map.get(key);
            int cellId = 0;
            for (Object obj : objectArr){

                HSSFCell cell = row.createCell(cellId++);
                if (obj == null){
                    cell.setCellValue("Null");
                }else{
                    cell.setCellValue(obj.toString());
                }
            }
        }

        try {
            hssfWorkbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Put Completed!");
        return map;
    }
}
