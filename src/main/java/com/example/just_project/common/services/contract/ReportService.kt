package com.example.just_project.common.services.contract

import net.sf.jasperreports.engine.JRAbstractExporter
import org.springframework.core.io.Resource

interface ReportService {
    fun makeReport(
            exporter: JRAbstractExporter<*, *, *, *>,
            template: Resource,
            params: Map<String, Any>
    ): ByteArray
}
