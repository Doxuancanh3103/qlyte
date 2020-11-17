package vt.qlkdtt.yte.report.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.MediaTypeUtils;
import vt.qlkdtt.yte.report.util.ReportRequestDTO;
import vt.qlkdtt.yte.report.util.ReportRequestObject;
import vt.qlkdtt.yte.report.util.ReportServiceImpl;
import vt.qlkdtt.yte.service.exception.AppException;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "report")
public class ReportEngineController {
    @Autowired
    ReportServiceImpl reportServiceImpl;

    @Autowired
    private ServletContext servletContext;

    @PostMapping(value = "createReport")
    public ResponseEntity<?> createReport(@RequestBody ReportRequestDTO reportRequestDTO) throws FileNotFoundException {
        String nomeMetodo = ".createReport() ";
        ReportRequestObject response = new ReportRequestObject();
//		LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);
        try {
            if (DataUtil.isNullObject(reportRequestDTO)) {
                throw new AppException("Can nhap input");
            } else if (DataUtil.isStringNullOrEmpty(reportRequestDTO.getTemplateFile())) {
                throw new AppException("Can nhap file template");
            }

            List<String> lstParameter = reportRequestDTO.getLstParameter();
            String templateFile = reportRequestDTO.getTemplateFile();
            String fileType = reportRequestDTO.getFileType();

            if (Const.REPORT_TEMPLATE_CODE.REPORT_NEW_CONNECT_MEDICAL_CODE.equalsIgnoreCase(templateFile)) {
                templateFile = Const.REPORT_TEMPLATE_CODE.REPORT_NEW_CONNECT_MEDICAL_NAME;
            }

//            templateFile = "K:\\01.Work\\source\\qlkdtt_yte_ws\\vt.qlkd-ws\\vt.qlkdtt-yte-ws\\src\\main\\resources\\template\\jasper\\" + templateFile;
            templateFile = "C:\\Users\\Admin\\JaspersoftWorkspace\\MyReports\\" + templateFile;
            String destinationPath = "K:\\01.Work\\source\\qlkdtt_yte_ws\\vt.qlkd-ws\\vt.qlkdtt-yte-ws\\reportOut\\";

            String fileName = "report_" + System.currentTimeMillis();

            ReportRequestObject requestObject = new ReportRequestObject();
            requestObject.setTemplateFile(templateFile);
            requestObject.setFileType(fileType);
            requestObject.setFileName(fileName);
            requestObject.setDestinationPath(destinationPath);

            Map<String, Object> parameters = new HashMap<String, Object>();
            int i = 1;
            for (String param : lstParameter) {
                parameters.put("m_param" + StringUtils.leftPad(DataUtil.safeToString(i), 2, "0"), param);
                i++;
            }

            response = reportServiceImpl.generatorReport(requestObject, parameters);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.noContent().build();
        }
        // Build
        File file = new File(response.getDownloadLink());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, response.getFileName() + response.getFileType());
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + response.getFileName() + response.getFileType())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }


//	@RequestMapping(value = { Constants.ACTION_EXPORT_FILE }, method = RequestMethod.GET)
//	public ResponseEntity<?> exportFile(@RequestParam(value = "reportInput") String reportInput)
//	{
//		String nomeMetodo = ".exportFile() ";
//		LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);
//
//		ReportRequestObject response = new ReportRequestObject();
//		try
//		{
//			LOG.info("ReportInput: " + reportInput);
//			DetectDateFormat ddf = new DetectDateFormat();
//			HashMap<String, Object> parameters = new ObjectMapper().readValue(reportInput, HashMap.class);
//			for (String key : parameters.keySet())
//			{
//				if (ddf.isValidDate((String) parameters.get(key)))
//				{
//					// Convert Date to dd-MMM-yyyy
//					DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//					parameters.put(key, df.format(ddf.parse((String) parameters.get(key))));
//				}
//			}
//			// parameters.put("IS_IGNORE_PAGINATION", "true");
//			response.setMimeType(reportService.getMediaType((String) parameters.get("fileType")));
//
//			String templateFile = ev.getProperty("upload.path.template") + (String) parameters.get("fileTempName") + CommonConstant.TEMPLATE_TYPE;
//			String destinationPath = ev.getProperty("DestinationPath");
//
//			// path
//			parameters.put("realPath", ev.getProperty("upload.path.template"));
//
//			LOG.info("destinationPath: " + destinationPath);
//
//			ReportRequestObject requestObject = new ReportRequestObject(templateFile, (String) parameters.get("fileType"),
//					(String) parameters.get("fileTempName"), destinationPath, CommonConstant.EMPTY);
//			LOG.info("templateFile: " + templateFile);
//
//			response = reportService.generatorReport(requestObject, parameters);
//			response.setMimeType(reportService.getMediaType((String) parameters.get("fileType")));
//		}
//		catch (Exception e)
//		{
//			LOG.error(e.getMessage(), e);
//			MessagesResponse mess = new MessagesResponse();
//			mess.setMessages(e.getMessage());
//			mess.setStatus(String.valueOf(CommonConstant.STATUS_DEFAULT));
//			LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
//			return new ResponseEntity<MessagesResponse>(mess, HttpStatus.OK);
//		}
//
//		HttpHeaders headers = new HttpHeaders();
//		File file = null;
//		try
//		{
//			file = resourceLoader.getResource("file:" + response.getDownloadLink()).getFile();
//		}
//		catch (IOException e)
//		{
//			LOG.error(e.getMessage(), e);
//			MessagesResponse mess = new MessagesResponse();
//			mess.setStatus(String.valueOf(CommonConstant.STATUS_DEFAULT));
//			mess.setMessages(CommonConstant.CREATE_REPORT_ERROR_CODE);
//			LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
//			return new ResponseEntity<MessagesResponse>(mess, HttpStatus.OK);
//		}
//		LOG.info(LOG.getName() + nomeMetodo + response.getDownloadLink());
//		LOG.info(LOG.getName() + nomeMetodo + response.getMimeType());
//
//		FileSystemResource fileSystemResource = new FileSystemResource(file);
//		headers.setContentType(MediaType.parseMediaType(response.getMimeType()));
//		headers.set("MyDownloadFile", response.getFileName());
//		headers.set("FileType", response.getFileType());
//
//		List<String> listOfAccessControl = new ArrayList<String>();
//		listOfAccessControl.add("MyDownloadFile");
//		listOfAccessControl.add("FileType");
//
//		headers.setAccessControlExposeHeaders(listOfAccessControl);
//
//		LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
//		return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
//	}
}
