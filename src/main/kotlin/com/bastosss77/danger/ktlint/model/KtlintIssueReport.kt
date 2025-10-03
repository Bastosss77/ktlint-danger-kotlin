package com.bastosss77.danger.ktlint.model

data class KtlintIssueReport(
    val issues: Set<KtlintFileIssue>,
) {
    val isClean = issues.isEmpty()

    operator fun plus(report: KtlintIssueReport): KtlintIssueReport {
        val mutableIssues =
            issues.toMutableSet().apply {
                addAll(report.issues)
            }

        return KtlintIssueReport(mutableIssues.toSet())
    }
}
