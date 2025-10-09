package io.github.bastosss77.danger.ktlint.model

data class KtlintReport(
    val issues: Set<FileIssueReport>,
) {
    val isClean: Boolean
        get() = issues.isEmpty()

    val issueCount: Int
        get() = issues.sumOf { it.issues.size }

    val isWarningOnly: Boolean
        get() = isOnlySeverity(SeverityIssue.WARNING)

    val isInfoOnly: Boolean
        get() = isOnlySeverity(SeverityIssue.INFO)

    val isErrorOnly: Boolean
        get() = isOnlySeverity(SeverityIssue.ERROR)

    val hasWarning: Boolean
        get() = hasSeverity(SeverityIssue.WARNING)

    val hasError: Boolean
        get() = hasSeverity(SeverityIssue.ERROR)

    val hasInfo: Boolean
        get() = hasSeverity(SeverityIssue.INFO)

    operator fun plus(report: KtlintReport): KtlintReport {
        val mutableIssues =
            issues.toMutableSet().apply {
                addAll(report.issues)
            }

        return KtlintReport(mutableIssues.toSet())
    }

    fun getBySeverity(severityIssue: SeverityIssue) =
        issues.flatMap { fileIssueReport ->
            fileIssueReport.issues.filter { it.severity == severityIssue }
        }

    private fun hasSeverity(severityIssue: SeverityIssue) =
        issues.any { fileIssueReport ->
            fileIssueReport.issues.any { it.severity == severityIssue }
        }

    private fun isOnlySeverity(severityIssue: SeverityIssue) =
        issues.all { fileIssueReport ->
            fileIssueReport.issues.all { it.severity == severityIssue }
        }
}
