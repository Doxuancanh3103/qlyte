package vt.qlkdtt.yte.report.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestObject 
{
	private String templateFile = "";
	private String fileType = "";
	private String fileName = "";
	private String destinationPath = "";
	private String staffName = "";
	private String shopName = "";
	private String typeOfReport = "";
	private String downloadLink = "";
	private String mimeType = "";
	private long fileSize = 0L;
}
