package io.github.bastosss77.danger.ktlint.model

data class FileIssueReport(
    val name: String,
    val issues: Set<IssueReport>
)