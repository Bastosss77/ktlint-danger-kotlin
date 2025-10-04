package io.github.bastosss77.danger.ktlint.reporter

import io.github.bastosss77.danger.ktlint.model.KtlintIssueReport

/**
 * Interface to declare a new reporter
 */
interface KtlintReporter {

    /**
     * Implement this function to create a custom reporter
     * @param report Report to post
     */
    fun report(report: KtlintIssueReport)
}
