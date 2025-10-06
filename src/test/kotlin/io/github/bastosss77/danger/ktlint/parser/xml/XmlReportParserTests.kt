package io.github.bastosss77.danger.ktlint.parser.xml

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import io.github.bastosss77.danger.ktlint.utils.TestResources
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class XmlReportParserTests {
    private val parser = XMLReportParser()

    @Test
    fun `Parse empty report`() {
        val file = TestResources.Xml.empty
        val expectedReport = KtlintReport(issues = emptySet())

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }

    @Test
    fun `Parse report with issues`() {
        val file = TestResources.Xml.notEmpty
        val expectedReport = KtlintReport(
            issues = setOf(
                FileIssueReport(
                    name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/KtlintPlugin.kt",
                    issues = setOf(
                        IssueReport(
                            line = 11,
                            column = 1,
                            severity = SeverityIssue.ERROR,
                            message = "Needless blank line(s)",
                            rule = "standard:no-consecutive-blank-lines"
                        ),
                        IssueReport(
                            line = 56,
                            column = 20,
                            severity = SeverityIssue.ERROR,
                            message = "Missing trailing comma before \")\"",
                            rule = "standard:trailing-comma-on-call-site"
                        )
                    )
                ),
                FileIssueReport(
                    name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/model/KtlintFileIssue.kt",
                    issues = setOf(
                        IssueReport(
                            line = 10,
                            column = 33,
                            severity = SeverityIssue.ERROR,
                            message = "Missing trailing comma before \")\"",
                            rule = "standard:trailing-comma-on-declaration-site"
                        ),
                        IssueReport(
                            line = 11,
                            column = 1,
                            severity = SeverityIssue.ERROR,
                            message = "Unexpected blank line(s) in value parameter list",
                            rule = "standard:no-blank-line-in-list"
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
        val file = TestResources.Xml.emptyIssue

        val report = parser.parse(file)

        assertTrue(report.isClean)
    }

    @Test
    fun `Parse malformed report`() {
        val file = TestResources.Xml.malformed

        assertFails { parser.parse(file) }
    }

    @Test
    fun `Parse duplicated issue`() {
        val file = TestResources.Xml.duplicated
        val expectedReport = KtlintReport(
            issues = setOf(
                FileIssueReport(
                    name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/KtlintPlugin.kt",
                    issues = setOf(
                        IssueReport(
                            line = 11,
                            column = 1,
                            severity = SeverityIssue.ERROR,
                            message = "Needless blank line(s)",
                            rule = "standard:no-consecutive-blank-lines"
                        )
                    )
                ),
                FileIssueReport(
                    name = "Projects/ktlint-danger-kotlin/src/main/kotlin/io/github/bastosss77/danger/ktlint/model/KtlintFileIssue.kt",
                    issues = setOf(
                        IssueReport(
                            line = 10,
                            column = 33,
                            severity = SeverityIssue.ERROR,
                            message = "Missing trailing comma before \")\"",
                            rule = "standard:trailing-comma-on-declaration-site"
                        )
                    )
                )
            )
        )

        val report = parser.parse(file)

        assertEquals(expectedReport, report)
    }
}