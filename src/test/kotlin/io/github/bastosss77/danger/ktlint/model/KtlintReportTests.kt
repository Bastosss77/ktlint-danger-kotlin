package io.github.bastosss77.danger.ktlint.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KtlintReportTests {
    @Test
    fun `Assert empty issue is clean`() {
        val issue = KtlintReport(emptySet())

        assertTrue(issue.isClean)
    }

    @Test
    fun `Assert empty issue is not clean`() {
        val issue =
            KtlintReport(
                setOf(FileIssueReport("", emptySet())),
            )

        assertFalse(issue.isClean)
    }

    @Test
    fun `Assert no duplicated issue`() {
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

        assertEquals(expectedReport, expectedReport + expectedReport)
    }

    @Test
    fun `Assert has severity`() {
        val report =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                ),
                        ),
                    ),
            )

        assertTrue(report.hasInfo)
        assertTrue(report.hasError)
        assertTrue(report.hasWarning)
    }

    @Test
    fun `Assert issue count`() {
        val report =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                ),
                        ),
                    ),
            )

        assertEquals(3, report.issueCount)
    }

    @Test
    fun `Assert has only warning`() {
        val warningOnlyReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                ),
                        ),
                    ),
            )

        val report =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                ),
                        ),
                    ),
            )

        assertTrue(warningOnlyReport.isWarningOnly)
        assertFalse(report.isWarningOnly)
    }

    @Test
    fun `Assert has only info`() {
        val infoOnlyReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                ),
                        ),
                    ),
            )

        val report =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                ),
                        ),
                    ),
            )

        assertTrue(infoOnlyReport.isInfoOnly)
        assertFalse(report.isInfoOnly)
    }

    @Test
    fun `Assert has only error`() {
        val errorOnlyReport =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                ),
                        ),
                    ),
            )

        val report =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                ),
                        ),
                    ),
            )

        assertTrue(errorOnlyReport.isErrorOnly)
        assertFalse(report.isErrorOnly)
    }

    @Test
    fun `Test get by severity`() {
        val report =
            KtlintReport(
                issues =
                    setOf(
                        FileIssueReport(
                            name = "fake",
                            issues =
                                setOf(
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 2,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.WARNING,
                                    ),
                                    IssueReport(
                                        line = 1,
                                        column = 1,
                                        message = "no message",
                                        rule = RuleReport.Standard(""),
                                        severity = SeverityIssue.INFO,
                                    ),
                                ),
                        ),
                    ),
            )

        val issues = report.getBySeverity(SeverityIssue.ERROR)

        assertEquals(2, issues.size)
        assertTrue(issues.all { it.severity == SeverityIssue.ERROR })
    }
}
