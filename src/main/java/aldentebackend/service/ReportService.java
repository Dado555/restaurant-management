package aldentebackend.service;

import java.time.LocalDate;
import java.util.Map;

public interface ReportService {

    Map<Long, Map<String, Object>> getReportForEveryItem(LocalDate start, LocalDate end);
}
