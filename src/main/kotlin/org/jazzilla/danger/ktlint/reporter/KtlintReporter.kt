package org.example.org.jazzilla.danger.ktlint.reporter

import org.example.org.jazzilla.danger.ktlint.model.KtlintIssueReport

interface KtlintReporter {
    fun report(report: KtlintIssueReport)
}
