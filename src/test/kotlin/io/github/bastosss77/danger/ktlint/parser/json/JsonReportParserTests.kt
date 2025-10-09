package io.github.bastosss77.danger.ktlint.parser.json

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.RuleReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import io.github.bastosss77.danger.ktlint.parser.json.model.JsonFileReport
import io.github.bastosss77.danger.ktlint.utils.TestResources
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

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }
}
