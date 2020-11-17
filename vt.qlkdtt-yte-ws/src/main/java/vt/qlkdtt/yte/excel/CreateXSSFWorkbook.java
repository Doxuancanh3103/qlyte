package vt.qlkdtt.yte.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateXSSFWorkbook {
    public static void createXSSFWorkbook(String name){
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(new File(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            xssfWorkbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Create XSSFWorkbook Successfully");
    }
}
