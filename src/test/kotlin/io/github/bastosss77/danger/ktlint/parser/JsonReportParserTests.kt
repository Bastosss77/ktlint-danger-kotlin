package io.github.bastosss77.danger.ktlint.parser

import com.bastosss77.danger.ktlint.utils.TestResources
import com.bastosss77.danger.ktlint.model.KtlintFileIssue
import com.bastosss77.danger.ktlint.model.KtlintIssue
import com.bastosss77.danger.ktlint.model.KtlintIssueReport
import io.github.bastosss77.danger.ktlint.parser.JsonReportParser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class JsonReportParserTests {
    private val parser = JsonReportParser()

    @Test
    fun `Parse empty report`() {
        val file = TestResources.Json.empty

        val report = parser.parse(file)

        assertTrue(report.isClean)
    }

    @Test
    fun `Parse report with issues`() {
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

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }

    @Test
    fun `Parse report and remove empty issues`() {
        val file = TestResources.Json.emptyIssue
        val report = parser.parse(file)

        assertTrue(report.isClean)
    }

    @Test
    fun `Parse malformed report`() {
        val file = TestResources.Json.malformed

        assertFails { parser.parse(file) }
    }

    @Test
    fun `Parse duplicated issue`() {
        val file = TestResources.Json.duplicated
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
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                    ),
                                ),
                        ),
                    ),
            )

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }
}
