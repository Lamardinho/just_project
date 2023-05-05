package com.example.just_project.project_reports.service.impl;

import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.util.AppException;
import com.example.just_project.common.util.AppMsgErrors;
import com.example.just_project.project_reports.service.XlsxReportService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class XlsxReportServiceImpl implements XlsxReportService {

    @NonNull
    private final ObjectMapperService objectMapperService;

    @Override
    public byte[] create(
            @NonNull final List<Map<String, Object>> data,
            @NonNull final String sheetName
    ) {
        try (val workbook = new XSSFWorkbook()) {
            val sheet = workbook.createSheet(sheetName);
            val headerStyle = getStandardHeaderStyle(workbook);
            val cellStyle = getXssfCellStyle(workbook);
            var rowIndex = 0;
            // заполняем строку с заголовками:
            val headerRow = sheet.createRow(rowIndex++);
            val keySet = data.get(0).keySet();
            val headers = keySet.toArray(new String[0]);
            for (var i = 0; i < headers.length; i++) {
                val cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            // заполняем таблицу данными:
            for (val map : data) {
                if (!map.keySet().equals(keySet)) {
                    throw new IllegalArgumentException(
                            AppMsgErrors.ALL_OBJECTS_IN_JSON_MUST_HAVE_THE_SAME_SET_OF_FIELDS
                    );
                }
                val row = sheet.createRow(rowIndex++);
                for (var i = 0; i < headers.length; i++) {
                    val value = map.get(headers[i]);
                    createCellAndSetValue(row, i, value);
                    row.getCell(i).setCellStyle(cellStyle);
                }
            }

            // применяем автоматическое изменение ширины колонок:
            IntStream.range(0, sheet.getRow(0).getLastCellNum()).forEach(sheet::autoSizeColumn);

            val outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new AppException(String.format(AppMsgErrors.FAILED_TO_GENERATE_REPORT, ex.getMessage()));
        }
    }

    private void createCellAndSetValue(
            @NonNull XSSFRow row,
            int i,
            @NonNull Object value
    ) {
        if (value instanceof String) {
            row.createCell(i).setCellValue((String) value);
        } else if (value instanceof Double) {
            row.createCell(i).setCellValue((Double) value);
        } else if (value instanceof Number) {
            row.createCell(i).setCellValue(((Number) value).longValue());
        } else if (value instanceof Boolean) {
            row.createCell(i).setCellValue(value.toString());
        } else if (value instanceof List) {
            row.createCell(i).setCellValue(objectMapperService.writeValueAsString(value));
        } else {
            row.createCell(i).setCellValue(objectMapperService.writeValueAsString(value));
        }
    }

    @NotNull
    private XSSFCellStyle getStandardHeaderStyle(@NotNull XSSFWorkbook workbook) {
        val headerFont = workbook.createFont();
        headerFont.setBold(true);
        val headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        return headerCellStyle;
    }

    @NotNull
    private XSSFCellStyle getXssfCellStyle(@NotNull XSSFWorkbook workbook) {
        val cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
    }
}
