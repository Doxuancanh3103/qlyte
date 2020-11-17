package vt.qlkdtt.yte.report.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDTO {
    List<String> lstParameter;
    String templateFile;
    String fileType;
}
