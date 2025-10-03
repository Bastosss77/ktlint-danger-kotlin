package com.bastosss77.danger.ktlint.reporter

import com.bastosss77.danger.ktlint.model.KtlintIssueReport


interface KtlintReporter {
    fun report(report: KtlintIssueReport)
}
