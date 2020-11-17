package vt.qlkdtt.yte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadDTO {
    MediaType mediaType;
    InputStreamResource inputStreamResource;
    long fileLength;
    String fileName;
}
