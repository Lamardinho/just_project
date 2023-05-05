package com.example.just_project.project_reports.controller;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.project_reports.service.XlsxReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Tag(name = "reports", description = "reports")
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    @NotNull
    private final XlsxReportService xlsxReportService;

    @Operation(summary = "Создание xlsx отчета из JSON. Контракт: массив объектов с одинаковым набором полей")
    @PostMapping("/make_report")
    @TrackExecutionTime
    public ResponseEntity<byte[]> createXlsxReport(
            @RequestParam(name = "reportName", defaultValue = "report", required = false) final String reportName,
            @RequestBody final List<Map<String, Object>> data
    ) {
        val report = xlsxReportService.create(data, reportName);
        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
