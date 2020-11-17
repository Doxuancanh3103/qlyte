package vt.qlkdtt.yte.service.sdo;

import lombok.Data;

import java.util.List;

@Data
public class UploadFileSdo {
    private Long documentId;
    private List<UploadFileDetailSdo> documentDetails;
}
