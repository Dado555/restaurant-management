package aldentebackend.controller;

import aldentebackend.model.Salary;
import aldentebackend.service.ReportService;
import aldentebackend.service.SalaryService;
import aldentebackend.support.salary.SalaryToSalaryDTO;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final SalaryService salaryService;
    private final SalaryToSalaryDTO toSalaryDTO;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate end)  {
        Object reportData = reportService.getReportForEveryItem(start, end);
        return new ResponseEntity<>(reportData, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping(value = "/salaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSalaries()  {
        Collection<Salary> reportData = salaryService.getAllCurrentSalaries();
        System.out.println("reportData = " + reportData);
        return new ResponseEntity<>(toSalaryDTO.convert(reportData), HttpStatus.OK);
    }
}
