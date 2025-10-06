package io.github.bastosss77.danger.ktlint.model

data class KtlintReport(
    val issues: Set<FileIssueReport>,
) {
    val isClean = issues.isEmpty()

    operator fun plus(report: KtlintReport): KtlintReport {
        val mutableIssues =
            issues.toMutableSet().apply {
                addAll(report.issues)
            }

        return KtlintReport(mutableIssues.toSet())
    }
}
