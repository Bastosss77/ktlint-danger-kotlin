package io.github.bastosss77.danger.ktlint.parser.json

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.parser.KtlintReportParser
import io.github.bastosss77.danger.ktlint.parser.json.model.JsonFileReport
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

internal class JsonReportParser : KtlintReportParser {
    private val json = Json.Default

    @OptIn(ExperimentalSerializationApi::class)
    override fun parse(file: File): KtlintReport {
        val issues =
            json
                .decodeFromStream<Set<JsonFileReport>>(file.inputStream())
                .filter { it.errors.isNotEmpty() }
                .toSet()

        return mapToKtlintReport(issues)
    }
}

private fun mapToKtlintReport(issues: Set<JsonFileReport>) : KtlintReport =
    KtlintReport(
        issues = issues.map { jsonFileReport ->
            FileIssueReport(
                name = jsonFileReport.file,
                issues = jsonFileReport.errors.map { jsonIssueReport ->
                    IssueReport(
                        line = jsonIssueReport.line,
                        column = jsonIssueReport.column,
                        message = jsonIssueReport.message,
                        rule = jsonIssueReport.rule,
                        severity = null
                    )
                }.toSet()
            )
        }.toSet()
    )
