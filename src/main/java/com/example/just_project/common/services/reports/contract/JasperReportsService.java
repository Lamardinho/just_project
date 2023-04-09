package com.example.just_project.common.services.reports.contract;

import com.example.just_project.common.enums.JasperReportExtension;
import lombok.NonNull;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;

public interface JasperReportsService {

    byte[] makeReport(
            @NonNull JasperReportExtension jasperReportExtension,
            @NonNull Map<String, Object> parameters,
            @NonNull Resource template
    ) throws JRException, IOException;
}
