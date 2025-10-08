package io.github.bastosss77.danger.ktlint

import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.parser.KtlintReportParser
import io.github.bastosss77.danger.ktlint.parser.json.JsonReportParser
import io.github.bastosss77.danger.ktlint.parser.sarif.SarifReportParser
import io.github.bastosss77.danger.ktlint.parser.xml.XMLReportParser
import io.github.bastosss77.danger.ktlint.reporter.DefaultReporter
import io.github.bastosss77.danger.ktlint.reporter.KtlintReporter
import systems.danger.kotlin.sdk.DangerPlugin
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors.toList
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.pathString

/**
 * Ktlint plugin for Danger Kotlin. Parse and report Ktlint issue on your pull requests
 *
 */
object KtlintPlugin : DangerPlugin() {
    override val id: String = "ktlintPlugin"

    private val parsers =
        mapOf(
            "json" to JsonReportParser(),
            "xml" to XMLReportParser(),
            "sarif" to SarifReportParser()
        )

    /**
     * Parse a Ktlint report file to create a report used to display issues from Ktlint
     * @param file File to Ktlint report
     */
    fun parse(file: File): KtlintReport {
        val parser = parsedBy(file)
        return parser.parse(file)
    }

    /**
     * Parse a Ktlint report files to create a report used to display issues from Ktlint
     * @param files List of files to Ktlint report
     */
    fun parse(files: Array<File>): KtlintReport {
        val report = KtlintReport(emptySet())

        return files.fold(report) { acc, next ->
            val fileReport = parsedBy(next).parse(next)

            acc + fileReport
        }
    }

    /**
     * Retrieve all files matching pattern and parse them
     * @param glob Pattern for matching files
     */
    fun parse(glob: String) : KtlintReport {
        val matcher = FileSystems.getDefault().getPathMatcher("glob:$glob")
        val root = Paths.get("")


        val files = Files.walk(root).use { paths ->
            paths
                .filter {it.isRegularFile() && matcher.matches(it) }
                .map {
                    println(it.absolutePathString())

                    File(it.pathString)
                }
                .collect(toList())
                .toList()
        }

        return parse(files.toTypedArray())
    }
    /**
     * Post the parsed report via Danger to your pull request. I case the default reporter doesn't give
     * you satisfaction, you can give a custom one by implementing [KtlintReporter]
     * @param report Report to post
     * @param reporter Reporter used to post the report
     */
    fun report(
        report: KtlintReport,
        reporter: KtlintReporter =
            DefaultReporter(
                context,
            ),
    ) {
        reporter.report(report)
    }

    /**
     * Find the correct parser to use based on the file extension provided as Ktlint report file
     * @param file Ktlint report file
     */
    private fun parsedBy(file: File) = parsers[file.extension] ?: throw IllegalArgumentException("No parser found for file ${file.path} with extension ${file.extension}")
}
