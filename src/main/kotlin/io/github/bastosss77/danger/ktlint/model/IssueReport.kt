package io.github.bastosss77.danger.ktlint.model

data class IssueReport(
    val line: Int,
    val column: Int,
    val message: String,
    val rule: RuleReport,
    val severity: SeverityIssue?,
)
