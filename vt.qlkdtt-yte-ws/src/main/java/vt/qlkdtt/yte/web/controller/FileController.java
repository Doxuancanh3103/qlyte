package vt.qlkdtt.yte.web.controller;

import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vt.qlkdtt.yte.common.Const;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.repository.DocumentRepo;
import vt.qlkdtt.yte.service.DocumentService;
import vt.qlkdtt.yte.service.sdi.DocumentInsertSdi;
import vt.qlkdtt.yte.service.sdo.DocumentInsertSdo;
import vt.qlkdtt.yte.common.MediaTypeUtils;
import vt.qlkdtt.yte.domain.DocumentSeq;
import vt.qlkdtt.yte.dto.FileDownloadDTO;
import vt.qlkdtt.yte.repository.DocumentSeqRepo;
import vt.qlkdtt.yte.service.FileService;
import vt.qlkdtt.yte.service.exception.AppException;
import vt.qlkdtt.yte.service.sdo.DocumentSearchSdo;
import vt.qlkdtt.yte.service.sdo.FileSdo;
import vt.qlkdtt.yte.service.sdo.UploadFileSdo;

import javax.servlet.ServletContext;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "file")
@Api(value = "File")
public class FileController {
    @Value("${uploadFile.splitChar}")
    private String splitChar;

    @Autowired
    FileService fileService;

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    DocumentSeqRepo documentSeqRepo;

    @Autowired
    private ServletContext servletContext;

    private static final String CUSTOMER_FOLDER_NAME = "customer";
    private static final String PRODUCT_FOLDER_NAME = "product";

    //<editor-fold desc="Upload file">
    @CrossOrigin(origins = "*")
    @PostMapping(value = "uploadFile")
    @ApiOperation(value = "upload file")
    public ResponseEntity<FileSdo> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "documentId", required = false) String documentId) {
        FileSdo fileSdo = new FileSdo();
        // thuc hien upload file
        String filePath = fileService.uploadFile(file);
        // thuc hien ghi nhan file vao Document
        if (!DataUtil.isStringNullOrEmpty(filePath)) {
            String fileName = filePath;
            if (filePath.contains(splitChar)) {
                fileName = filePath.substring(filePath.lastIndexOf(splitChar) + 1, filePath.length());
            }
            DocumentSeq documentSeq = documentSeqRepo.save(new DocumentSeq());
            String seq = String.valueOf(documentSeq.getId());

            DocumentInsertSdi documentInsertSdi = new DocumentInsertSdi();
            if (!DataUtil.isNullOrZero(documentId)) {
                documentInsertSdi.setDocumentId(documentId);
            }
            documentInsertSdi.setCode(Const.DOCUMENT_CODE_PREFIX + Strings.padStart(seq, 5, Const.PAD_CHAR));
            documentInsertSdi.setName(Const.DOCUMENT_CODE_PREFIX + Strings.padStart(seq, 5, Const.PAD_CHAR));
            documentInsertSdi.setFileName(fileName);
            documentInsertSdi.setPath(filePath);
            documentInsertSdi.setCreateUser("admin");

            DocumentInsertSdo documentInsertSdo = documentService.insert(documentInsertSdi);
            // Tra ra ket qua
            fileSdo.setDocumentId(documentInsertSdo.getId());
            fileSdo.setFileName(fileName);
        }
        return ResponseEntity.ok(fileSdo);
    }
    //</editor-fold>

    //<editor-fold desc="Upload file customer">
    @CrossOrigin(origins = "*")
    @PostMapping(value = "uploadFileCustomer") //consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "upload file customer")
    public ResponseEntity<UploadFileSdo> uploadFileCustomer(
            @RequestParam(value = "files") MultipartFile[] files,
//            @RequestParam Long customerId,
            @RequestParam(value = "documentId", required = false) Long documentId
    ) {
        UploadFileSdo fileSdo = fileService.uploadFileByObject(files, documentId, CUSTOMER_FOLDER_NAME);

        return ResponseEntity.ok(fileSdo);
    }
    //</editor-fold>

    //<editor-fold desc="Upload file product">
    @CrossOrigin(origins = "*")
    @PostMapping(value = "uploadFileProduct")
    @ApiOperation(value = "upload file product")
    public ResponseEntity<UploadFileSdo> uploadFileProduct(
            @RequestParam MultipartFile[] files,
//            @RequestParam Long productId,
            @RequestParam(value = "documentId", required = false) Long documentId
    ) {
        UploadFileSdo fileSdo = fileService.uploadFileByObject(files, documentId, PRODUCT_FOLDER_NAME);

        return ResponseEntity.ok(fileSdo);
    }
    //</editor-fold>

    //<editor-fold desc="Download file 2">
    @GetMapping(value = "downloadFile2")
    @ApiOperation(value = "download file 2")
    public ResponseEntity<InputStreamResource> downloadFile2(@RequestParam Long documentDetailId) throws Exception {
        return fileService.downloadFile2(documentDetailId);
    }
    //</editor-fold>

    //<editor-fold desc="Download file">
    @GetMapping(value = "downloadFile")
    @ApiOperation(value = "download file")
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam(value = "documentId", required = false) long documentId) throws Exception {

        DocumentSearchSdo documentSearchSdo = documentService.findById(documentId);
        String fileName = "";
        if (documentSearchSdo != null && !DataUtil.isStringNullOrEmpty(documentSearchSdo.getFileName())) {
            fileName = documentSearchSdo.getFileName();

            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
            FileDownloadDTO fileDownloadDTO = fileService.downloadFile(fileName);
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
