package io.github.bastosss77.danger.ktlint.model

import io.github.bastosss77.danger.ktlint.parser.json.model.JsonFileReport
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KtlintIssueReportTests {
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
                                        rule = "standard:no-trailing-spaces",
                                        severity = SeverityIssue.ERROR,
                                    ),
                                    IssueReport(
                                        line = 26,
                                        column = 1,
                                        message = "File must end with a newline (\\n)",
                                        rule = "standard:final-newline",
                                        severity = SeverityIssue.ERROR,
                                    ),
                                ),
                        ),
                    ),
            )

        assertEquals(expectedReport, expectedReport + expectedReport)
    }
}
