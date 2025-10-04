package io.github.bastosss77.danger.ktlint

import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import io.github.bastosss77.danger.ktlint.model.KtlintFileIssue
import io.github.bastosss77.danger.ktlint.model.KtlintIssue
import io.github.bastosss77.danger.ktlint.model.KtlintIssueReport
import io.github.bastosss77.danger.ktlint.reporter.KtlintReporter
import io.github.bastosss77.danger.ktlint.utils.TestResources
import org.junit.jupiter.api.assertThrows
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

        assertThrows<IllegalArgumentException> {
            KtlintPlugin.parse(file)
        }
    }

    @Test
    fun `Parse json file`() {
        val file = TestResources.Json.notEmpty
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
                    ),
            )

        assertEquals(expectedReport, KtlintPlugin.parse(file))
    }

    @Test
    fun `Parse multiple json files`() {
        val files = arrayOf(TestResources.Json.notEmpty, TestResources.Json.notEmpty2)

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

        assertEquals(expectedReport, KtlintPlugin.parse(files))
    }

    @Test
    fun `Report with reporter`() {
        val report =
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
                    ),
            )

        justRun { mockReporter.report(any()) }

        KtlintPlugin.report(report, mockReporter)

        verify { mockReporter.report(report) }
    }
}
