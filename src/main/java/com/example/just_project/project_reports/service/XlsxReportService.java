package com.example.just_project.project_reports.service;

import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface XlsxReportService {

    byte[] create(
            @NonNull List<Map<String, Object>> data,
            @NonNull String sheetName
    );
}
