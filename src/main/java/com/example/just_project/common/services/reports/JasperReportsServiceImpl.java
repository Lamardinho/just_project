package com.example.just_project.common.services.reports;

import com.example.just_project.common.aop.TrackExecutionTime;
import com.example.just_project.common.enums.JasperReportExtension;
import com.example.just_project.common.services.reports.contract.JasperReportsService;
import com.example.just_project.common.util.AppException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для формирования отчетов с помощью JasperSoft
 */
@Service
@RequiredArgsConstructor
public class JasperReportsServiceImpl implements JasperReportsService {

    @Value("classpath:reports/templates/offers.jrxml")
    private Resource dynamicReportResource;

    @TrackExecutionTime
    public byte[] test(List<?> data) throws JRException, IOException {
        val dataSource = new JRBeanCollectionDataSource(data);
        val hashMap = new HashMap<String, Object>();
        hashMap.put("ROWS_TABLE", dataSource);

        return makeReport(JasperReportExtension.XLSX, hashMap, dynamicReportResource);
    }

    @Override
    public byte[] makeReport(
            @NonNull JasperReportExtension jasperReportExtension,
            @NonNull Map<String, Object> parameters,
            @NonNull Resource template
    ) throws JRException, IOException {
        val jasperReport = JasperCompileManager.compileReport(template.getInputStream());

        val jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        switch (jasperReportExtension) {
            case PDF:
                return JasperExportManager.exportReportToPdf(jasperPrint);
            case XLSX:
                return createXlsxBytes(jasperPrint);
            default:
                throw new AppException("Формирование отчетов для формата: " + jasperReportExtension + " не реализовано");
        }
    }

    /**
     * Генерирует отчет в формате XLSX.
     */
    private byte @NotNull [] createXlsxBytes(@NonNull JasperPrint jasperPrint) throws JRException, IOException {
        val outputStream = new ByteArrayOutputStream();
        val exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        //exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("report.xlsx")); // на диск

        val reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[]{"Report"});

        exporter.setConfiguration(reportConfig);
        exporter.exportReport();

        outputStream.close();

        return outputStream.toByteArray();
    }
}
