package io.github.bastosss77.danger.ktlint.reporter

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.parser.json.model.JsonFileReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import systems.danger.kotlin.sdk.DangerContext
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.Test

class DefaultReporterTests {
    private val mockContext: DangerContext = mockk()
    private val reporter = DefaultReporter(mockContext)

    @AfterTest
    fun tearDown() {
        confirmVerified(mockContext)
    }

    @Test
    fun `Report with clean Ktlint report`() {
        val expectedReport = KtlintReport(emptySet())

        reporter.report(expectedReport)
    }

    @Test
    fun `Report where Ktlint report has empty issues`() {
        val expectedReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues = emptySet(),
                        ),
                    ),
            )

        reporter.report(expectedReport)
    }

    @Test
    fun `Report where Ktlint report has issues`() {
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
                                        rule = "standard:no-trailing-spaces",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                        severity = SeverityIssue.ERROR
                                    ),
                                ),
                        ),
                        FileIssueReport(
                            name = "fakeFile2.kt",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = "standard:no-trailing-spaces",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                        severity = SeverityIssue.ERROR
                                    ),
                                ),
                        ),
                    ),
            )

        justRun { mockContext.fail(any()) }

        reporter.report(expectedReport)

        expectedReport.issues.forEach { fileIssue ->
            fileIssue.issues.forEach { issue ->
                val message = prepareMessage(issue)

                verify { mockContext.fail(message) }
            }
        }
    }

    @Test
    fun `Report where Ktlint report has issues with path`() {
        val root = File("").absolutePath

        val expectedReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = root,
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = "standard:no-trailing-spaces",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                        severity = SeverityIssue.ERROR
                                    ),
                                ),
                        ),
                        FileIssueReport(
                            name = root,
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = "standard:no-trailing-spaces",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                        severity = SeverityIssue.ERROR
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                        severity = SeverityIssue.ERROR
                                    ),
                                ),
                        ),
                    ),
            )

        justRun { mockContext.fail(any(), any(), any()) }

        reporter.report(expectedReport)

        expectedReport.issues.forEach { fileIssue ->
            fileIssue.issues.forEach { issue ->
                val message = prepareMessage(issue)

                verify { mockContext.fail(message, fileIssue.name, issue.line) }
            }
        }
    }
}

private fun prepareMessage(issue: IssueReport): String =
    """
    **Ktlint** : ${issue.message}
    **Rule** : ${issue.rule}
    """.trimIndent()
