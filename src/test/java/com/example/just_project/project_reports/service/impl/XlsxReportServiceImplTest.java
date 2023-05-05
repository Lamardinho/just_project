package com.example.just_project.project_reports.service.impl;

import com.example.just_project.common.dto.IdAndName;
import com.example.just_project.common.services.ObjectMapperService;
import com.example.just_project.common.util.AppMsgErrors;
import com.example.just_project.project_reports.dto.XlsxTestDto;
import com.example.just_project.project_reports.service.impl.XlsxReportServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.val;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class XlsxReportServiceImplTest {

    private final XlsxReportServiceImpl xlsxReportService =
            new XlsxReportServiceImpl(new ObjectMapperService(new ObjectMapper()));
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void create() throws IOException, InvalidFormatException {
        val jsonBytes = Files.readAllBytes(
                Paths.get("src/test/resources/reports/xlsx_report_service_test/nba_stars.json")
        );
        val data =
                objectMapper.readValue(jsonBytes, new TypeReference<List<Map<String, Object>>>() {
                });
        //act:
        val result = xlsxReportService.create(data, "Report");
        // Записываем отчет на диск:
        val reportPath = Paths.get("/tmp/xlsx_report_service_test_nba_stars_new.xlsx");
        val newFilePath = Files.write(reportPath, result);

        // проверяем содержимое:
        testCompareExcelFiles(
                "src/test/resources/reports/xlsx_report_service_test/nba_stars_old.xlsx",
                newFilePath.toString()
        );
    }

    @Test
    void create_whenFieldsInDifferentOrder_thenOk() throws IOException, InvalidFormatException {
        val json = "[{\"a\":10,\"b\":20},{\"b\":20,\"a\":10}]";
        val data = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });
        //act:
        val result = xlsxReportService.create(data, "Report");
        // Записываем отчет на диск:
        val reportPath = Paths.get("/tmp/different_order_fields_new.xlsx");
        val newFilePath = Files.write(reportPath, result);

        // проверяем содержимое:
        testCompareExcelFiles(
                "src/test/resources/reports/xlsx_report_service_test/different_order_fields_old.xlsx",
                newFilePath.toString()
        );
    }

    @Test
    void create_byDtoList() throws IOException, InvalidFormatException {
        val list = List.of(
                new XlsxTestDto(1, "name1", true, new IdAndName(10L, "secondName1")),
                new XlsxTestDto(2, "name2", true, new IdAndName(20L, "secondName2")),
                new XlsxTestDto(3, "name3", true, new IdAndName(30L, "secondName3"))
        );
        val data = objectMapper.convertValue(list, new TypeReference<List<Map<String, Object>>>() {
        });
        //act:
        val result = xlsxReportService.create(data, "Report");
        // Записываем отчет на диск:
        val reportPath = Paths.get("/tmp/xlsxTestDto_new.xlsx");
        val newFilePath = Files.write(reportPath, result);

        // проверяем содержимое:
        testCompareExcelFiles(
                "src/test/resources/reports/xlsx_report_service_test/xlsxTestDto_old.xlsx",
                newFilePath.toString()
        );
    }

    @Test
    void create_whenNotValidJson_thenError() throws JsonProcessingException {
        val json = "[{\"a\":10,\"b\":20,\"c\":30},{\"b\":20,\"a\":10}]";
        val data = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });
        assertThatThrownBy(() ->
                xlsxReportService.create(data, "report")
        ).hasMessage(
                String.format(
                        AppMsgErrors.FAILED_TO_GENERATE_REPORT,
                        AppMsgErrors.ALL_OBJECTS_IN_JSON_MUST_HAVE_THE_SAME_SET_OF_FIELDS
                )
        );
    }

    /**
     * Проверка содержимого.
     */
    private void testCompareExcelFiles(
            @NonNull String oldFilePath,
            @NonNull String newFilePath
    ) throws IOException, InvalidFormatException {
        @SuppressWarnings("resource") val expectedWorkbook = new XSSFWorkbook(new File(oldFilePath));
        @SuppressWarnings("resource") val actualWorkbook = new XSSFWorkbook(new File(newFilePath));

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
