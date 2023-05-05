package com.example.just_project.project_reports.service;

import com.example.just_project.common.services.ObjectMapperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.val;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class XlsxReportServiceImplTest {

    private final XlsxReportServiceImpl xlsxReportService =
            new XlsxReportServiceImpl(new ObjectMapperService(new ObjectMapper()));

    @Test
    void generateXlsx_dtoListParam() throws IOException, InvalidFormatException {
        val jsonBytes = Files.readAllBytes(
                Paths.get("src/test/resources/reports/xlsx_report_service_test/nba_stars.json")
        );
        val json = new String(jsonBytes);

        //act:
        val result = xlsxReportService.makeReport(json, "Report");
        // Записываем отчет на диск:
        val reportPath = Paths.get("/tmp/xlsxReportServiceImplTest.nba_stars_new.xlsx");
        val newFilePath = Files.write(reportPath, result);

        // проверяем содержимое:
        testCompareExcelFiles(newFilePath);
    }

    /**
     * Проверка содержимого.
     */
    private void testCompareExcelFiles(@NonNull Path newFilePath) throws IOException, InvalidFormatException {
        @SuppressWarnings("resource") val expectedWorkbook =
                new XSSFWorkbook(new File("src/test/resources/reports/xlsx_report_service_test/nba_stars_old.xlsx"));
        @SuppressWarnings("resource") val actualWorkbook = new XSSFWorkbook(new File(newFilePath.toString()));

        // Сравниваем кол-во листов в рабочих тетрадях:
        assertThat(expectedWorkbook.getNumberOfSheets()).isEqualTo(actualWorkbook.getNumberOfSheets());

        // Сравниваем содержимое каждого листа
        for (int i = 0; i < expectedWorkbook.getNumberOfSheets(); i++) {
            val expectedSheet = expectedWorkbook.getSheetAt(i);
            val actualSheet = actualWorkbook.getSheetAt(i);

            // Сравниваем кол-во строк в листах
            assertThat(expectedSheet.getLastRowNum()).isEqualTo(actualSheet.getLastRowNum());

            // Сравниваем содержимое каждой строки
            for (int j = 0; j <= expectedSheet.getLastRowNum(); j++) {
                val expectedRow = expectedSheet.getRow(j);
                val actualRow = actualSheet.getRow(j);

                // Сравниваем кол-во ячеек в строках
                assertThat(expectedRow.getLastCellNum()).isEqualTo(actualRow.getLastCellNum());

                // Сравниваем содержимое каждой ячейки
                for (int k = 0; k <= expectedRow.getLastCellNum() - 1; k++) {
                    val expectedCell = expectedRow.getCell(k);
                    val actualCell = actualRow.getCell(k);

                    assertThat(expectedCell.getRawValue()).isEqualTo(actualCell.getRawValue());
                }
            }
        }
    }
}
