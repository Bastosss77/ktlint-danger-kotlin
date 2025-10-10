package io.github.bastosss77.danger.ktlint.parser.xml.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.RuleReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue

@JacksonXmlRootElement(namespace = "checkstyle")
data class XmlReport(

    @field:JacksonXmlProperty
    val version: String = "",

    @field:JsonProperty("file")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val file: List<XmlFileReport> = emptyList(),
)

fun XmlReport.mapToKtlintReport(): KtlintReport =
    KtlintReport(
        issues =
            file
                .mapNotNull { xmlFileReport ->
                    if (xmlFileReport.errors.isEmpty()) return@mapNotNull null

                    FileIssueReport(
                        name = xmlFileReport.name,
                        issues =
                            xmlFileReport.errors
                                .map { xmlError ->
                                    IssueReport(
                                        line = xmlError.line,
                                        column = xmlError.column,
                                        message = xmlError.message,
                                        rule = RuleReport.parse(xmlError.source),
                                        severity = SeverityIssue.from(xmlError.severity) ?: SeverityIssue.ERROR,
                                    )
                                }.toSet(),
                    )
                }.toSet(),
    )
