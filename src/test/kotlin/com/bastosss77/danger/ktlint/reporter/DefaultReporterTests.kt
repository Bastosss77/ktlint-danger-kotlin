package com.bastosss77.danger.ktlint.reporter

import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import com.bastosss77.danger.ktlint.model.KtlintFileIssue
import com.bastosss77.danger.ktlint.model.KtlintIssue
import com.bastosss77.danger.ktlint.model.KtlintIssueReport
import com.bastosss77.danger.ktlint.reporter.DefaultReporter
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
        val expectedReport = KtlintIssueReport(emptySet())

        reporter.report(expectedReport)
    }

    @Test
    fun `Report where Ktlint report has empty issues`() {
        val expectedReport =
            KtlintIssueReport(
                issues =
                    setOf(
                        KtlintFileIssue(
                            file = "fake",
                            issues = emptySet(),
                        ),
                    ),
            )

        reporter.report(expectedReport)
    }

    @Test
    fun `Report where Ktlint report has issues`() {
        val expectedReport =
            KtlintIssueReport(
                issues =
                    setOf(
                        KtlintFileIssue(
                            file = "fakeFile.kt",
                            issues =
                                setOf(
                                    KtlintIssue(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = "standard:no-trailing-spaces",
                                    ),
                                    KtlintIssue(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                    ),
                                    KtlintIssue(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                    ),
                                ),
                        ),
                        KtlintFileIssue(
                            file = "fakeFile2.kt",
                            issues =
                                setOf(
                                    KtlintIssue(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = "standard:no-trailing-spaces",
                                    ),
                                    KtlintIssue(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                    ),
                                    KtlintIssue(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
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
            KtlintIssueReport(
                issues =
                    setOf(
                        KtlintFileIssue(
                            file = root,
                            issues =
                                setOf(
                                    KtlintIssue(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = "standard:no-trailing-spaces",
                                    ),
                                    KtlintIssue(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                    ),
                                    KtlintIssue(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                    ),
                                ),
                        ),
                        KtlintFileIssue(
                            file = root,
                            issues =
                                setOf(
                                    KtlintIssue(
                                        line = 13,
                                        column = 1,
                                        message = "Trailing space(s)",
                                        rule = "standard:no-trailing-spaces",
                                    ),
                                    KtlintIssue(
                                        line = 24,
                                        column = 5,
                                        message = "Function name should start with a lowercase letter (except factory methods) and use camel case",
                                        rule = "standard:function-naming",
                                    ),
                                    KtlintIssue(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
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

                verify { mockContext.fail(message, fileIssue.file, issue.line) }
            }
        }
    }
}

private fun prepareMessage(issue: KtlintIssue): String =
    """
    **Ktlint** : ${issue.message}
    **Rule** : ${issue.rule}
    """.trimIndent()
