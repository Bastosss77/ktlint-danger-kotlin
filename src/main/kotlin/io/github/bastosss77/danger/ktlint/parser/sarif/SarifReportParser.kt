package io.github.bastosss77.danger.ktlint.parser.sarif

import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.parser.KtlintReportParser
import io.github.bastosss77.danger.ktlint.parser.sarif.model.SarifReport
import io.github.bastosss77.danger.ktlint.parser.sarif.model.mapToKtlintReport
import io.github.bastosss77.danger.ktlint.parser.xml.model.mapToKtlintReport
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

class SarifReportParser : KtlintReportParser {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun parse(file: File): KtlintReport {
        val sarifReport = json
            .decodeFromStream<SarifReport>(file.inputStream())

        return sarifReport.mapToKtlintReport()
    }
}