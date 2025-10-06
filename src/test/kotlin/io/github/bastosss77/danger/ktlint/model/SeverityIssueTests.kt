package io.github.bastosss77.danger.ktlint.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SeverityIssueTests {
    @Test
    fun `test unknown severity returns null`() {
        assertNull(SeverityIssue.from("unknown"))
    }

    @Test
    fun `test severity mapping`() {
        assertEquals(SeverityIssue.INFO, SeverityIssue.from("info"))
        assertEquals(SeverityIssue.ERROR, SeverityIssue.from("error"))
        assertEquals(SeverityIssue.WARNING, SeverityIssue.from("warning"))
    }
}
