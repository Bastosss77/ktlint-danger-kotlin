package io.github.bastosss77.danger.ktlint.parser.xml.model

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlKeyName
import nl.adaptivity.xmlutil.serialization.XmlMapEntryName
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("checkstyle")
data class XmlReport(
    val version: String,

    @XmlElement(true)
    val file: List<XmlFileReport>
)

fun XmlReport.mapToKtlintReport() : KtlintReport =
    KtlintReport(
        issues = file.mapNotNull { xmlFileReport ->
            if(xmlFileReport.errors.isEmpty()) return@mapNotNull null

            FileIssueReport(
                name = xmlFileReport.name,
                issues = xmlFileReport.errors.map { xmlError ->
                    IssueReport(
                        line = xmlError.line,
                        column = xmlError.column,
                        message = xmlError.message,
                        rule = xmlError.source,
                        severity = SeverityIssue.from(xmlError.severity)
                    )
                }.toSet()
            )
        }.toSet()
    )