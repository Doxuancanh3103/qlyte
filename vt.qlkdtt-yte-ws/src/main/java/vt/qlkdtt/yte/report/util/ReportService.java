package vt.qlkdtt.yte.report.util;

import net.sf.jasperreports.engine.JRException;

import java.util.Map;

public interface ReportService {
    ReportRequestObject generatorReport(ReportRequestObject input, Map<String, Object> parameters) throws JRException, Exception;
}
