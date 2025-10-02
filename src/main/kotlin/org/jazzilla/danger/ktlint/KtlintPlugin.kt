package org.example.org.jazzilla.danger.ktlint

import org.example.org.jazzilla.danger.ktlint.model.KtlintIssueReport
import org.example.org.jazzilla.danger.ktlint.parser.JsonReportParser
import org.example.org.jazzilla.danger.ktlint.parser.KtlintReportParser
import org.example.org.jazzilla.danger.ktlint.reporter.DefaultReporter
import org.example.org.jazzilla.danger.ktlint.reporter.KtlintReporter
import systems.danger.kotlin.sdk.DangerPlugin
import java.io.File

object KtlintPlugin : DangerPlugin() {
    override val id: String = "ktlintPlugin"

    private val parsers =
        mapOf<String, KtlintReportParser>(
            "json" to JsonReportParser(),
        )

    fun parse(file: File): KtlintIssueReport {
        val parser = parsedBy(file)
        return parser.parse(file)
    }

    fun parse(files: Array<File>): KtlintIssueReport {
        val report = KtlintIssueReport(emptySet())

        return files.fold(report) { acc, next ->
            val fileReport = parsedBy(next).parse(next)

            acc + fileReport
        }
    }

    fun report(
        report: KtlintIssueReport,
        reporter: KtlintReporter = DefaultReporter(context),
    ) {
        reporter.report(report)
    }

    private fun parsedBy(file: File) = parsers[file.extension] ?: throw IllegalArgumentException("No parser found for file ${file.path} with extension ${file.extension}")
}
