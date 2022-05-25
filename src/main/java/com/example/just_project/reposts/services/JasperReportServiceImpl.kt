package com.example.just_project.reposts.services

import com.example.just_project.reposts.services.contract.ReportService
import net.sf.jasperreports.engine.JRAbstractExporter
import net.sf.jasperreports.engine.JREmptyDataSource
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

/**
 * Сервис для формирования отчетов с помощью JasperSoft
 */
@Service
class JasperReportServiceImpl : ReportService {

    override fun makeReport(
            exporter: JRAbstractExporter<*, *, *, *>,
            template: Resource,
            params: Map<String, Any>
    ): ByteArray {

        return template.inputStream.use { inputStream ->
            val reportTemplate = JasperCompileManager.compileReport(inputStream)

            val jasperPrint = JasperFillManager.fillReport(
                    reportTemplate, params, JREmptyDataSource()
            )

            ByteArrayOutputStream().use {
                exporter.exporterOutput = SimpleOutputStreamExporterOutput(it)
                exporter.setExporterInput(SimpleExporterInput(jasperPrint))
                exporter.exportReport()
                it.toByteArray()
            }
        }
    }
}
