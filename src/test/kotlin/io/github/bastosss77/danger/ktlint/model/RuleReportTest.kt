package io.github.bastosss77.danger.ktlint.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class RuleReportTest {

    @Test
    fun `test rule mapping`() {
        assertEquals(RuleReport.Standard("rule"), RuleReport.parse("standard:rule"))
        assertEquals(RuleReport.Experimental("rule"), RuleReport.parse("experimental:rule"))
    }

    @Test
    fun `test parse unknown rule type`() {
        assertFails {
            RuleReport.parse("unknown:rule")
        }
    }

    @Test
    fun `assert full name`() {
        assertEquals("standard:rule", RuleReport.Standard("rule").fullName)
        assertEquals("experimental:rule", RuleReport.Experimental("rule").fullName)
    }
}