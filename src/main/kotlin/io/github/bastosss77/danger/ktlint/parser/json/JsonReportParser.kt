package io.github.bastosss77.danger.ktlint.parser.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.RuleReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import io.github.bastosss77.danger.ktlint.parser.KtlintReportParser
import io.github.bastosss77.danger.ktlint.parser.json.model.JsonFileReport
import java.io.File

internal class JsonReportParser : KtlintReportParser {
    private val jsonParser = ObjectMapper()
        .registerKotlinModule()

    override fun parse(file: File): KtlintReport {
        val filesErrors = jsonParser.readValue<Set<JsonFileReport>>(file)

        return mapToKtlintReport(filesErrors)
    }
}

private fun mapToKtlintReport(issues: Set<JsonFileReport>): KtlintReport =
    KtlintReport(
        issues =
            issues
                .mapNotNull { jsonFileReport ->
                    if(jsonFileReport.errors.isEmpty()) return@mapNotNull null

                    FileIssueReport(
                        name = jsonFileReport.file,
                        issues =
                            jsonFileReport.errors
                                .mapNotNull { jsonIssueReport ->
                                    if(jsonFileReport.errors.isEmpty()) return@mapNotNull null
                                    IssueReport(
                                        line = jsonIssueReport.line,
                                        column = jsonIssueReport.column,
                                        message = jsonIssueReport.message,
                                        rule = RuleReport.parse(jsonIssueReport.rule),
                                        severity = SeverityIssue.ERROR,
                                    )
                                }.toSet(),
                    )
                }.toSet(),
    )
