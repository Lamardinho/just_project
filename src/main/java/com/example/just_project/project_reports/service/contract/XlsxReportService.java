package com.example.just_project.project_reports.service.contract;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Objects;

public interface XlsxReportService {

    @SneakyThrows
    byte[] generateXlsx(
            @NonNull List<Objects> dtoList,
            @NonNull String reportName
    );

    @SneakyThrows
    byte[] generateXlsx(
            @NonNull String json,
            @NonNull String reportName
    );
}
