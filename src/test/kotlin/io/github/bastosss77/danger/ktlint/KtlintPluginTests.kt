package io.github.bastosss77.danger.ktlint

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.RuleReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import io.github.bastosss77.danger.ktlint.parser.json.model.JsonFileReport
import io.github.bastosss77.danger.ktlint.reporter.KtlintReporter
import io.github.bastosss77.danger.ktlint.utils.TestResources
import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertThrows
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class KtlintPluginTests {
    private val mockReporter = mockk<KtlintReporter>()

    @AfterTest
    fun tearDown() {
        confirmVerified(mockReporter)
    }

    @Test
    fun `Parse unknown file extension`() {
        val file = File("fake.txt")

        assertThrows(IllegalArgumentException::class.java) {
            KtlintPlugin.parse(file)
        }
    }

    @Test
    fun `Parse json file`() {
        val file = TestResources.Json.notEmpty
        val expectedReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fakeFile.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = RuleReport.Standard("no-trailing-spaces"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = RuleReport.Standard("function-naming"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = RuleReport.Standard("final-newline"),
                                        severity = null,
                                    ),
                                ),
                        ),
                    ),
            )

        assertEquals(expectedReport, KtlintPlugin.parse(file))
    }

    @Test
    fun `Parse xml file`() {
        val file = TestResources.Xml.notEmpty
        val expectedReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/KtlintPlugin.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 11,
                                        column = 1,
                                        severity = SeverityIssue.ERROR,
                                        message = "Needless blank line(s)",
                                        rule = RuleReport.Standard("no-consecutive-blank-lines"),
                                    ),
                                    IssueReport(
                                        line = 56,
                                        column = 20,
                                        severity = SeverityIssue.ERROR,
                                        message = "Missing trailing comma before \")\"",
                                        rule = RuleReport.Standard("trailing-comma-on-call-site"),
                                    ),
                                ),
                        ),
                        FileIssueReport(
                            name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/model/KtlintFileIssue.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 10,
                                        column = 33,
                                        severity = SeverityIssue.ERROR,
                                        message = "Missing trailing comma before \")\"",
                                        rule = RuleReport.Standard("trailing-comma-on-declaration-site"),
                                    ),
                                    IssueReport(
                                        line = 11,
                                        column = 1,
                                        severity = SeverityIssue.ERROR,
                                        message = "Unexpected blank line(s) in value parameter list",
                                        rule = RuleReport.Standard("no-blank-line-in-list"),
                                    ),
                                ),
                        ),
                    ),
            )

        assertEquals(expectedReport, KtlintPlugin.parse(file))
    }

    @Test
    fun `Parse sarif file`() {
        val file = TestResources.Sarif.notEmpty
        val expectedReport =
            KtlintReport(
                setOf(
                    FileIssueReport(
                        name = "Documents/Perso/Projects/ktlint-danger-kotlin/build.gradle.kts",
                        issues =
                            setOf(
                                IssueReport(
                                    line = 26,
                                    column = 5,
                                    message = "Missing space after //",
                                    rule = RuleReport.Standard("comment-spacing"),
                                    severity = SeverityIssue.ERROR,
                                ),
                                IssueReport(
                                    line = 30,
                                    column = 42,
                                    message = "Expected newline before '.'",
                                    rule = RuleReport.Standard("chain-method-continuation"),
                                    severity = SeverityIssue.ERROR,
                                ),
                            ),
                    ),
                    FileIssueReport(
                        name = "Documents/Perso/Projects/ktlint-danger-kotlin/SarifReportParser.kt",
                        issues =
                            setOf(
                                IssueReport(
                                    line = 30,
                                    column = 48,
                                    message = "Expected newline before '.'",
                                    rule = RuleReport.Standard("chain-method-continuation"),
                                    severity = SeverityIssue.ERROR,
                                ),
                            ),
                    ),
                ),
            )

        assertEquals(expectedReport, KtlintPlugin.parse(file))
    }

    @Test
    fun `Parse multiple files`() {
        val files = arrayOf(TestResources.Json.notEmpty, TestResources.Xml.notEmpty)

        val expectedReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fakeFile.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = RuleReport.Standard("no-trailing-spaces"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = RuleReport.Standard("function-naming"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = RuleReport.Standard("final-newline"),
                                        severity = null,
                                    ),
                                ),
                        ),
                        FileIssueReport(
                            name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/KtlintPlugin.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 11,
                                        column = 1,
                                        severity = SeverityIssue.ERROR,
                                        message = "Needless blank line(s)",
                                        rule = RuleReport.Standard("no-consecutive-blank-lines"),
                                    ),
                                    IssueReport(
                                        line = 56,
                                        column = 20,
                                        severity = SeverityIssue.ERROR,
                                        message = "Missing trailing comma before \")\"",
                                        rule = RuleReport.Standard("trailing-comma-on-call-site"),
                                    ),
                                ),
                        ),
                        FileIssueReport(
                            name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/model/KtlintFileIssue.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 10,
                                        column = 33,
                                        severity = SeverityIssue.ERROR,
                                        message = "Missing trailing comma before \")\"",
                                        rule = RuleReport.Standard("trailing-comma-on-declaration-site"),
                                    ),
                                    IssueReport(
                                        line = 11,
                                        column = 1,
                                        severity = SeverityIssue.ERROR,
                                        message = "Unexpected blank line(s) in value parameter list",
                                        rule = RuleReport.Standard("no-blank-line-in-list"),
                                    ),
                                ),
                        ),
                    ),
            )

        assertEquals(expectedReport, KtlintPlugin.parse(files))
    }

    @Test
    fun `Parse json files with glob`() {
        val pattern = "**/test/resources/**/not-empty.json"
        val expectedReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fakeFile.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = RuleReport.Standard("no-trailing-spaces"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = RuleReport.Standard("function-naming"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = RuleReport.Standard("final-newline"),
                                        severity = null,
                                    ),
                                ),
                        ),
                    ),
            )

        val report = KtlintPlugin.parse(pattern)

        assertEquals(expectedReport, report)
    }

    @Test
    fun `Parse multiple files with glob`() {
        val pattern = "**/test/resources/**/not-empty.{json,sarif}"
        val expectedReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fakeFile.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = RuleReport.Standard("no-trailing-spaces"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = RuleReport.Standard("function-naming"),
                                        severity = null,
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = RuleReport.Standard("final-newline"),
                                        severity = null,
                                    ),
                                ),
                        ),
                        FileIssueReport(
                            name = "Documents/Perso/Projects/ktlint-danger-kotlin/build.gradle.kts",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 26,
                                        column = 5,
                                        message = "Missing space after //",
                                        rule = RuleReport.Standard("comment-spacing"),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 30,
                                        column = 42,
                                        message = "Expected newline before '.'",
                                        rule = RuleReport.Standard("chain-method-continuation"),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                ),
                        ),
                        FileIssueReport(
                            name = "Documents/Perso/Projects/ktlint-danger-kotlin/SarifReportParser.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 30,
                                        column = 48,
                                        message = "Expected newline before '.'",
                                        rule = RuleReport.Standard("chain-method-continuation"),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                ),
                        )
                    ),
            )

        val report = KtlintPlugin.parse(pattern)

        assertEquals(expectedReport, report)
    }

    @Test
    fun `Report with reporter`() {
        val report =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fakeFile.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = RuleReport.Standard("no-trailing-spaces"),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = RuleReport.Standard("function-naming"),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = RuleReport.Standard("final-newline"),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                ),
                        ),
                    ),
            )

        justRun { mockReporter.report(any()) }

        KtlintPlugin.report(report, mockReporter)

        verify { mockReporter.report(report) }
    }
}
