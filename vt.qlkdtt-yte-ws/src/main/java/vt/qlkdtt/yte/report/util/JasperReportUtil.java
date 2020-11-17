package vt.qlkdtt.yte.report.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.type.OrientationEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class JasperReportUtil {
    public static void main(String[] args) throws JRException, IOException {

//        JasperReport jasperReport = JasperCompileManager
//                .compileReport("C:\\Users\\Admin\\JaspersoftWorkspace\\MyReports\\Blank_A4.jrxml");
//
//        // Tham số truyền vào báo cáo.
//        Map<String, Object> parameters = new HashMap<String, Object>();
//
//        // DataSource
//        // Đây là báo cáo đơn giản, không kết nối vào DB
//        // vì vậy không cần nguồn dữ liệu.
//        JRDataSource dataSource = new JREmptyDataSource();
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
//                parameters, dataSource);
//
//        // Đảm bảo thư mục đầu ra tồn tại.
//        File outDir = new File("K:\\01.Work\\source\\qlkdtt_yte_ws\\vt.qlkd-ws\\vt.qlkdtt-yte-ws\\reportOut\\");
//        outDir.mkdirs();
//
//        // Chạy báo cáo và export ra file PDF.
//        JasperExportManager.exportReportToPdfFile(jasperPrint,
//                "K:\\01.Work\\source\\qlkdtt_yte_ws\\vt.qlkd-ws\\vt.qlkdtt-yte-ws\\reportOut\\Blank_A4.pdf");
//
//        System.out.println("Done!");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nowDate", new Date());
        parameters.put("stringNumber1", "Check bien string bao cao");

        String fileOutputName = "BAO_CAO_TEST_" + System.currentTimeMillis() + ".pdf";
        String pathTemplateDesign = null;
        String pathTemplateJasper = "Blank_A4.jrxml";
        String pathFileOutput = "K:\\01.Work\\source\\qlkdtt_yte_ws\\vt.qlkd-ws\\vt.qlkdtt-yte-ws\\reportOut\\" + fileOutputName;

        try (OutputStream os = new FileOutputStream(pathFileOutput)) {
            long start = System.currentTimeMillis();
            PdfDTO pdf = exportPdf(parameters, pathTemplateDesign, pathTemplateJasper);
            if (pdf != null) {
                os.write(pdf.getPdfBytes());
            }
        } catch (FileNotFoundException ex) {
            log.error("File " + pathTemplateJasper + "is not existed...");
            throw ex;
        } catch (Exception ex) {
            log.error("Have error when export Pdf");
        }
    }

    public static void exportPdf(Map<String, Object> parameters, String pathTemplateDesign, String pathTemplateJasper, String pathFileOutput) throws Exception {
        try (OutputStream os = new FileOutputStream(pathFileOutput)) {
            long start = System.currentTimeMillis();
            PdfDTO pdf = exportPdf(parameters, pathTemplateDesign, pathTemplateJasper);
            if (pdf != null) {
                os.write(pdf.getPdfBytes());
            }
        } catch (FileNotFoundException ex) {
            log.error("File " + pathTemplateJasper + "is not existed...");
            throw ex;
        } catch (JRException ex) {
            log.error("Have error when export Pdf");
            throw ex;
        }
    }

    public static PdfDTO exportPdf(Map<String, Object> parameters, String pathTemplateDesign, String pathTemplateJasper) throws Exception {
        PdfDTO invoicePrinterDTO = new PdfDTO();
        invoicePrinterDTO.setFileExtension("pdf");

        try {
            long start = System.currentTimeMillis();
            JasperReport jasperReport = JasperCompileManager
                    .compileReport("C:\\Users\\Admin\\JaspersoftWorkspace\\MyReports\\" + pathTemplateJasper);
//                    getJaserReport(pathTemplateDesign, pathTemplateJasper);
            if (jasperReport == null) {
                return null;
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            jasperPrint.setOrientation(OrientationEnum.PORTRAIT);

//            limitPage(beans, jasperPrint);

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            invoicePrinterDTO.setPdfBytes(pdfBytes);

            parameters.put("DATA_OUT_PUT", invoicePrinterDTO);
            String coreName = StringUtils.substringBetween(pathTemplateDesign, "/", ".");
            String fileName = String.format("%s%s_%s.%s", coreName, RandomStringUtils.randomNumeric(5), parameters.get("nowDate"), invoicePrinterDTO.getFileExtension());
            invoicePrinterDTO.setFileName(fileName);
            log.info("exportPdf: duration = " + (System.currentTimeMillis() - start));

//        } catch (FileNotFoundException ex) {
//            log.error("File " + pathTemplateJasper + "is not existed...");
//            throw ex;
        } catch (JRException ex) {
            log.error("Have error when export Pdf");
            throw ex;
        }
        return invoicePrinterDTO;
    }

}
