package com.bastosss77.danger.ktlint.model

import com.bastosss77.danger.ktlint.model.KtlintFileIssue
import com.bastosss77.danger.ktlint.model.KtlintIssue
import com.bastosss77.danger.ktlint.model.KtlintIssueReport
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KtlintIssueReportTests {
    @Test
    fun `Assert empty issue is clean`() {
        val issue = KtlintIssueReport(emptySet())

        assertTrue(issue.isClean)
    }

    @Test
    fun `Assert empty issue is not clean`() {
        val issue =
            KtlintIssueReport(
                setOf(KtlintFileIssue("", emptySet())),
            )

        assertFalse(issue.isClean)
    }

    @Test
    fun `Assert no duplicated issue`() {
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

        assertEquals(expectedReport, expectedReport + expectedReport)
    }
}
