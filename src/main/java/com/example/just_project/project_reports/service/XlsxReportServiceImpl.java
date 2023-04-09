package com.example.just_project.project_reports.service;

import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.project_reports.service.contract.XlsxReportService;
import com.example.just_project.common.util.AppException;
import com.example.just_project.common.util.AppMsgErrors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class XlsxReportServiceImpl implements XlsxReportService {

    @NonNull
    private final ObjectMapperService objectMapperService;

    @Override
    @SneakyThrows
    public byte[] generateXlsx(
            @NonNull final List<Objects> dtoList,
            @NonNull final String reportName
    ) {
        val json = objectMapperService.writeValueAsString(dtoList);
        return generateXlsx(json, reportName);
    }

    @Override
    @SneakyThrows
    public byte[] generateXlsx(
            @NonNull final String json,
            @NonNull final String reportName
    ) {
        try (val workbook = new XSSFWorkbook()) {
            val sheet = workbook.createSheet(reportName);
            // разбираем JSON:
            val data = new ObjectMapper().readTree(json);
            // заполняем строку с заголовками:
            fillHeaderLine(sheet, data.elements(), getStandardHeaderStyle(workbook));
            // заполняем таблицу данными:
            fillTable(sheet, data.elements(), getXssfCellStyle(workbook));

            // применяем автоматическое изменение ширины колонок:
            IntStream.range(0, sheet.getRow(0).getLastCellNum()).forEach(sheet::autoSizeColumn);

            val outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception exception) {
            throw new AppException(AppMsgErrors.FAILED_TO_GENERATE_REPORT, exception);
        }
    }

    /**
     * Заполняем строку с заголовками.
     */
    private void fillHeaderLine(
            @NonNull XSSFSheet sheet,
            @NonNull Iterator<JsonNode> rows,
            @NonNull XSSFCellStyle style
    ) {
        val headerRow = sheet.createRow(0); // создаем строку с названиями колонок
        val firstRow = rows.next();
        val columnNames = firstRow.fieldNames();
        var cellIndex = 0;
        // заполняем ячейки в строке заголовка данными:
        while (columnNames.hasNext()) {
            val columnName = columnNames.next();
            val cell = headerRow.createCell(cellIndex++);
            cell.setCellValue(columnName);
            cell.setCellStyle(style);
        }
    }

    private void fillTable(
            @NonNull XSSFSheet sheet,
            @NonNull Iterator<JsonNode> rows,
            @NonNull XSSFCellStyle style
    ) {
        var rowIndex = 1;
        while (rows.hasNext()) {
            val row = rows.next();
            val sheetRow = sheet.createRow(rowIndex++);
            val cells = row.elements();
            var cellIndex = 0;
            while (cells.hasNext()) {
                val cell = cells.next();
                val sheetCell = sheetRow.createCell(cellIndex++);
                if (cell.isTextual()) {
                    sheetCell.setCellValue(cell.asText());
                } else if (cell.isNumber()) {
                    sheetCell.setCellValue(cell.asDouble());
                } else if (cell.isBoolean()) {
                    sheetCell.setCellValue(cell.asBoolean());
                } else {
                    sheetCell.setCellValue(cell.toString());
                }

                sheetCell.setCellStyle(style);
            }
        }
    }

    @NotNull
    private XSSFCellStyle getXssfCellStyle(@NotNull XSSFWorkbook workbook) {
        val cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
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
}
