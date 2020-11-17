package vt.qlkdtt.yte.report.util;

import lombok.Data;

@Data
public class ExcelDTO {

    private Long invoiceUsedId;
    private String fileName;

    private byte[] pdfBytes;
    private byte[] excelBytes;

    private String fileExtension = "pdf";
    private boolean removeAble = false;
    private boolean offlineFile = false;
}
