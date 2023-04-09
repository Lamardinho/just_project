package com.example.just_project.project_reports.controller;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.project_reports.service.contract.XlsxReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Tag(name = "reports", description = "reports")
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    @NotNull
    private final XlsxReportService xlsxReportService;

    @Operation(summary = "Создать отчет XLSX")
    @PostMapping("/make_report/json")
    @TrackExecutionTime
    public ResponseEntity<byte[]> generateReport(@RequestBody String json) {
        val report = xlsxReportService.makeReport(json, "Report");
        val headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
