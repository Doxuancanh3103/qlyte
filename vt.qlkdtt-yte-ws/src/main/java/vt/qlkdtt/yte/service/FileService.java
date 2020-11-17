package vt.qlkdtt.yte.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.common.MediaTypeUtils;
import vt.qlkdtt.yte.domain.Document;
import vt.qlkdtt.yte.domain.DocumentDetail;
import vt.qlkdtt.yte.domain.DocumentDetailTemp;
import vt.qlkdtt.yte.domain.DocumentSeq;
import vt.qlkdtt.yte.dto.FileDownloadDTO;
import vt.qlkdtt.yte.repository.DocumentDetailRepo;
import vt.qlkdtt.yte.repository.DocumentDetailTempRepo;
import vt.qlkdtt.yte.repository.DocumentRepo;
import vt.qlkdtt.yte.repository.DocumentSeqRepo;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdo.UploadFileDetailSdo;
import vt.qlkdtt.yte.service.sdo.UploadFileSdo;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileService {

    @Value("${uploadFile.uploadDir}")
    private String uploadDir;

    @Value("${uploadFile.downloadDir}")
    private String downloadDir;

    @Value("${uploadFile.maxUploadSizeInMb}")
    private String maxUploadSizeInMb;

    @Value("${uploadFile.splitChar}")
    private String splitChar;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    DocumentSeqRepo documentSeqRepo;

    @Autowired
    DocumentDetailRepo documentDetailRepo;

    @Autowired
    DocumentDetailTempRepo documentDetailTempRepo;

    @Autowired
    private ServletContext servletContext;

    //<editor-fold desc="Upload file">
    public String uploadFile(MultipartFile file) {
        String path = "";
        //validate file
        if (file.getSize() > Long.parseLong(maxUploadSizeInMb) * 1024 * 1024) {
            throw new AppException("File-002", "File upload khong duoc vuot qua 10MB");
        }
        try {
            String uniqueFileName = changeFileName(StringUtils.cleanPath(file.getOriginalFilename()));
            Path copyLocation = Paths.get(uploadDir + File.separator + uniqueFileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            path = copyLocation.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("File-001", "Upload khong thanh cong" + e.getMessage() != null ? e.getMessage() : "");
        }
        return path;
    }
    //</editor-fold>

    //<editor-fold desc="Upload file2">
    public String uploadFile2(MultipartFile file) {
        String path = "";
        //validate file
        if (file.getSize() > Long.parseLong(maxUploadSizeInMb) * 1024 * 1024) {
            throw new AppException("File-002", "File upload khong duoc vuot qua 10MB");
        }
        try {
            String uniqueFileName = changeFileName(StringUtils.cleanPath(file.getOriginalFilename()));

            String pathParent = uploadDir + File.separator + "Temp";

            if (!Files.exists(Paths.get(pathParent))) {
                Files.createDirectory(Paths.get(pathParent));
            }

            Path copyLocation = Paths.get(uploadDir + File.separator + "Temp" + File.separator + uniqueFileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            path = copyLocation.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("File-001", "Upload khong thanh cong" + e.getMessage() != null ? e.getMessage() : "");
        }
        return path;
    }
    //</editor-fold>

    //<editor-fold desc="Change file name">
    public String changeFileName(String fileName) {
        String returnFileName = fileName;
        try {
            long uniqueId = System.currentTimeMillis();
            if (fileName.contains(".")) {
                String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                String orgFileName = fileName.substring(0, fileName.lastIndexOf(".") > 0 ? fileName.lastIndexOf(".") : 0);
                orgFileName = orgFileName.substring(0, orgFileName.length() >= 19 ? 20 : orgFileName.length());

                returnFileName = DataUtil.createSlug(orgFileName) + "_" + uniqueId + ext;
            } else {
                returnFileName = DataUtil.createSlug(fileName) + uniqueId;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnFileName;
    }
    //</editor-fold>

    //<editor-fold desc="Download file">
    public FileDownloadDTO downloadFile(String fileName) {
        FileDownloadDTO fileDownloadDTO = new FileDownloadDTO();
        if (DataUtil.isUnicodeString(fileName)) {
            throw new AppException("File-004", "Khong co thong tin duong dan file cua tai lieu nay");
        }
        try {
            String fullFilePath = downloadDir + File.separator + fileName;
            File file = new File(fullFilePath);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            fileDownloadDTO.setInputStreamResource(resource);
            fileDownloadDTO.setFileLength(file.length());
            fileDownloadDTO.setFileName(fileName.substring(fileName.lastIndexOf(splitChar) + 1, fileName.length()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("File-003", "Download file khong thanh cong" + e.getMessage() != null ? e.getMessage() : "");
        }
        return fileDownloadDTO;
    }
    //</editor-fold>

    //<editor-fold desc="Upload file by object">
    public UploadFileSdo uploadFileByObject(MultipartFile[] files, Long documentId, String folderName) {
        UploadFileSdo result = new UploadFileSdo();

        if (DataUtil.isNullOrZero(documentId)) {
            DocumentSeq documentSeq = documentSeqRepo.save(new DocumentSeq());
            String seq = String.valueOf(documentSeq.getId());

            Document document = new Document();
            document.setCode(Const.DOCUMENT_CODE_PREFIX + Strings.padStart(seq, 5, Const.PAD_CHAR));
            document.setName(Const.DOCUMENT_CODE_PREFIX + Strings.padStart(seq, 5, Const.PAD_CHAR));
            document.setCreateUser(Const.ADMIN);
            document.setCreateDatetime(new Date());

            document = documentRepo.save(document);
            documentId = document.getId();
        }
        result.setDocumentId(documentId);

        List<UploadFileDetailSdo> listUploadDetail = new ArrayList<>();
        for (MultipartFile file : files) {
            String filePath = uploadFile2(file);

            if (!DataUtil.isStringNullOrEmpty(filePath)) {
                UploadFileDetailSdo sdo = new UploadFileDetailSdo();

                String fileName = filePath;
                if (filePath.contains(splitChar)) {
                    fileName = filePath.substring(filePath.lastIndexOf(splitChar) + 1, filePath.length());
                }

                DocumentDetailTemp documentDetail = new DocumentDetailTemp();
                documentDetail.setDocumentId(documentId);
                documentDetail.setPath(filePath);
                documentDetail.setFileName(fileName);
                documentDetail.setCreateUser(Const.ADMIN);
                documentDetail.setCreateDatetime(new Date());

                documentDetail = documentDetailTempRepo.save(documentDetail);

                sdo.setDocumentDetailTempId(documentDetail.getDocumentDetailTempId());
                sdo.setFileName(fileName);
                sdo.setFilePath(filePath);

                listUploadDetail.add(sdo);
            }
        }
        result.setDocumentDetails(listUploadDetail);

        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Download file 2">
    public ResponseEntity<InputStreamResource> downloadFile2(Long documentDetailId){
        Optional<DocumentDetail> optionalDT = documentDetailRepo.findById(documentDetailId);

        if (optionalDT.isPresent()) {
            DocumentDetail documentDetail = optionalDT.get();

            String filePath = documentDetail.getPath();

            FileDownloadDTO fileDownloadDTO = new FileDownloadDTO();

            if (DataUtil.isUnicodeString(filePath)) {
                throw new AppException("File-004", "Khong co thong tin duong dan file cua tai lieu nay");
            }

            try {
                File file = new File(filePath);
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                fileDownloadDTO.setInputStreamResource(resource);
                fileDownloadDTO.setFileLength(file.length());
                fileDownloadDTO.setFileName(documentDetail.getFileName());
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException("File-003", "Download file khong thanh cong" + e.getMessage() != null ? e.getMessage() : "");
            }

            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filePath);
            return ResponseEntity.ok()
                    // Content-Disposition
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileDownloadDTO.getFileName())
                    // Content-Type
                    .contentType(mediaType)
                    // Contet-Length
                    .contentLength(fileDownloadDTO.getFileLength()) //
                    .body(fileDownloadDTO.getInputStreamResource());
        } else {
            throw new AppException("API-FI001", "Khong ton tai tai lieu tren he thong");
        }
    }
    //</editor-fold>
}
