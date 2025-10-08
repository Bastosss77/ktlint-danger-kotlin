package io.github.bastosss77.danger.ktlint.parser.sarif

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.RuleReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import io.github.bastosss77.danger.ktlint.utils.TestResources
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class SarifReportParserTests {
    private val parser = SarifReportParser()

    @Test
    fun `Parse empty report`() {
        val file = TestResources.Sarif.empty
        val report = parser.parse(file)

        assertTrue(report.isClean)
    }

    @Test
    fun `Parse report with issues`() {
        val file = TestResources.Sarif.notEmpty
        val expectedReport = KtlintReport(
            setOf(
                FileIssueReport(
                    name = "Documents/Perso/Projects/ktlint-danger-kotlin/build.gradle.kts",
                    issues = setOf(
                        IssueReport(
                            line = 26,
                            column = 5,
                            message = "Missing space after //",
                            rule = RuleReport.Standard("comment-spacing"),
                            severity = SeverityIssue.ERROR
                        ),
                        IssueReport(
                            line = 30,
                            column = 42,
                            message = "Expected newline before '.'",
                            rule = RuleReport.Standard("chain-method-continuation"),
                            severity = SeverityIssue.ERROR
                        )
                    )
                ),
                FileIssueReport(
                    name = "Documents/Perso/Projects/ktlint-danger-kotlin/SarifReportParser.kt",
                    issues = setOf(
                        IssueReport(
                            line = 30,
                            column = 48,
                            message = "Expected newline before '.'",
                            rule = RuleReport.Standard("chain-method-continuation"),
                            severity = SeverityIssue.ERROR
                        )
                    )
                )
            )
        )

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }

    @Test
    fun `Parse report and remove empty issues`() {
        val file = TestResources.Sarif.emptyIssue

        val expectedReport = KtlintReport(
            setOf(
                FileIssueReport(
                    name = "Documents/Perso/Projects/ktlint-danger-kotlin/build.gradle.kts",
                    issues = setOf(
                        IssueReport(
                            line = 30,
                            column = 42,
                            message = "Expected newline before '.'",
                            rule = RuleReport.Standard("chain-method-continuation"),
                            severity = SeverityIssue.ERROR
                        )
                    )
                ),
                FileIssueReport(
                    name = "Documents/Perso/Projects/ktlint-danger-kotlin/SarifReportParser.kt",
                    issues = setOf(
                        IssueReport(
                            line = 30,
                            column = 48,
                            message = "Expected newline before '.'",
                            rule = RuleReport.Standard("chain-method-continuation"),
                            severity = SeverityIssue.ERROR
                        )
                    )
                )
            )
        )

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }

    @Test
    fun `Parse malformed report`() {
        val file = TestResources.Sarif.malformed

        assertFails { parser.parse(file) }
    }

    @Test
    fun `Parse duplicated issue`() {
        val file = TestResources.Sarif.duplicated
        val expectedReport = KtlintReport(
            setOf(
                FileIssueReport(
                    name = "Documents/Perso/Projects/ktlint-danger-kotlin/build.gradle.kts",
                    issues = setOf(
                        IssueReport(
                            line = 26,
                            column = 5,
                            message = "Missing space after //",
                            rule = RuleReport.Standard("comment-spacing"),
                            severity = SeverityIssue.ERROR
                        )
                    )
                )
            )
        )

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }
}