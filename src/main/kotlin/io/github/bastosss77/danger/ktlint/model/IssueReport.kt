package io.github.bastosss77.danger.ktlint.model

import kotlinx.serialization.Serializable

data class IssueReport(
    val line: Int,
    val column: Int,
    val message: String,
    val rule: String,
    val severity: SeverityIssue?,
)
