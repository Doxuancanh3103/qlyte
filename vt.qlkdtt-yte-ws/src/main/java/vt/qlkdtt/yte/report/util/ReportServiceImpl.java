package vt.qlkdtt.yte.report.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.apache.catalina.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vt.qlkdtt.yte.common.Const;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private DataSource dataSource;

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Transactional
    public ReportRequestObject generatorReport(ReportRequestObject input, Map<String, Object> parameters) throws JRException, Exception {
        String nomeMetodo = ".generatorReport()";
        LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);

        ReportRequestObject response = new ReportRequestObject();
        response.setTemplateFile(input.getTemplateFile());
        response.setFileType(input.getFileType());
        response.setFileName(input.getFileName());
        response.setDestinationPath(input.getDestinationPath());


        try {
            LOG.info("input.getTemplateFile(): " + input.getTemplateFile());
            JasperReport jasperReport = JasperCompileManager.compileReport(input.getTemplateFile());
            LOG.info("jasperReport: " + jasperReport);
            LOG.info("parameters: " + jasperReport);

            for (Object obj : parameters.entrySet()) {
                Map.Entry<String, String> entry = (Map.Entry) obj;
                LOG.info(entry.getKey() + " : " + entry.getValue());
            }
            if (input.getFileType().equals(".xls") || input.getFileType().equals(".xlsx")) {
                parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
            }
            //add for connection
            Connection connection = dataSource.getConnection();
            //end add
//            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, connection);

            exportToFile(print, input.getFileType(), input.getDestinationPath(), input.getFileName());
            response.setDownloadLink(input.getDestinationPath() + input.getFileName() + input.getFileType());

        } catch (JRException ex) {
            LOG.error(ex.getMessage(), ex);
            LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
            throw ex;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
            throw ex;
        }
        LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
        return response;
    }

//    public ReportRequestObject generatorReportEform(String inputTemplateName, Vector vtParam, int tempPages, String imgBackgrounPath, String inputTemplatePath,
//                                                    String outputFilePath, Connection connection) throws JRException, Exception {
//        String nomeMetodo = ".generatorReportEform()";
//        LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
//
//        ReportRequestObject response = new ReportRequestObject();
//        long startTime = System.currentTimeMillis();
//        try {
//            LOG.info("inputTemplateName = " + inputTemplateName);
//            LOG.info("tempPages = " + tempPages);
//            LOG.info("imgBackgrounPath = " + imgBackgrounPath);
//            LOG.info("inputTemplatePath = " + inputTemplatePath);
//            LOG.info("outputFilePath = " + outputFilePath);
//
//            Map params = new HashMap();
//
//            if (vtParam.isEmpty()) {
//                LOG.error("PARAM ERR !");
//                LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
//
//                throw new Exception("PARAM ERR !");
//            } else if (tempPages == 0) {
//                LOG.error("number of page template ERR !");
//                LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
//                throw new Exception("number of page template ERR !");
//            } else {
//                for (int i = 0; i < vtParam.size(); i++) {
//                    EformParam param = (EformParam) vtParam.get(i);
//                    params.put(param.getKey(), param.getValue());
//                }
//            }
//
//            params.put("p_path_img", imgBackgrounPath);
//            params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
//
////			FileUtil.forceFolderExist(inputTemplatePath);
////			String outputFileName = inputTemplateName + "_export_" + new java.text.SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date())
////					+ ".pdf";
//
//            LOG.info("Parameters: " + params.toString());
//
//            List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
//
//            for (int i = 1; i <= tempPages; i++) {
//                String iPage = "_00" + String.valueOf(i) + Const.TEMPLATE_TYPE; // format template : test_001, test_002
//
//                String inputFilePath = inputTemplatePath + inputTemplateName + iPage;
//                LOG.info("page: " + inputTemplateName + iPage);
//                LOG.info("inputFilePath : " + inputFilePath);
//                JasperReport jasperReport = JasperCompileManager.compileReport(inputFilePath);
//                JasperPrint fillData = JasperFillManager.fillReport(jasperReport, params, connection);
//                jasperPrintList.add(fillData);
//            }
//
//            JRPdfExporter exporter = new JRPdfExporter();
//            // Add the list as a Parameter
//            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
//            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bOut);
//            exporter.exportReport();
//
//            FileUtils.writeByteArrayToFile(new File(outputFilePath), bOut.toByteArray());
//            response.setDownloadLink(outputFilePath);
//
//            LOG.info("Export [" + inputTemplateName + ".pdf] success!");
//            long totalTime = System.currentTimeMillis() - startTime;
//            LOG.info("EformBean2.exportFile2() END [time = +" + totalTime + "] ");
//
//        } catch (JRException ex) {
//            LOG.error(ex.getMessage(), ex);
//            LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
//            throw ex;
//        }
//
//        LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
//        return response;
//    }

    public void exportToFile(JasperPrint print, String fileType, String destinationPath, String fileName) throws JRException {
        String nomeMetodo = ".exportToFile()";
        LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);

        ExporterInput exporterInput = new SimpleExporterInput(print);

        if (fileType.equalsIgnoreCase(".pdf")) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(exporterInput);
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
            exporter.setExporterOutput(exporterOutput);

            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }
        if (fileType.equalsIgnoreCase(".xlsx")) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(exporterInput);
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
            exporter.setExporterOutput(exporterOutput);
            SimpleXlsxExporterConfiguration configuration = new SimpleXlsxExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }
        if (fileType.equalsIgnoreCase(".xls")) {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(exporterInput);
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
            exporter.setExporterOutput(exporterOutput);
            SimpleXlsExporterConfiguration configuration = new SimpleXlsExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }
        if (fileType.equalsIgnoreCase(".docx")) {
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(exporterInput);
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
            exporter.setExporterOutput(exporterOutput);
            SimpleDocxExporterConfiguration configuration = new SimpleDocxExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        }
        LOG.info("File output : " + destinationPath + fileName + fileType);
        LOG.info(LOG.getName() + nomeMetodo + Const.END_LOG);
    }

//    public static void main(String agrs[]) {
//        try {
//            ReportService service = new ReportService();
//            ReportRequestObject input = new ReportRequestObject("C:\\Users\\Admin\\JaspersoftWorkspace\\MyReports\\Blank_A4.jrxml", ".pdf", "REPORT_OUT_02",
//                    "K:\\01.Work\\source\\qlkdtt_yte_ws\\vt.qlkd-ws\\vt.qlkdtt-yte-ws\\reportOut\\", "Test");
//            Map<String, Object> parameters = new HashMap<>();
////            Connection connection = (Connection) em.unwrap(Connection.class);
//
//            service.generatorReport(input, parameters);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
