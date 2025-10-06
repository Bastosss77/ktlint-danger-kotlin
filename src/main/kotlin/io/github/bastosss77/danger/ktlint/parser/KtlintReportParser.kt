package io.github.bastosss77.danger.ktlint.parser

import io.github.bastosss77.danger.ktlint.model.KtlintReport
import java.io.File

/**
 * Interface to create a file parser for Ktlint report
 */
internal interface KtlintReportParser {
    /**
     * Implement this function to parse a Ktlint report file
     */
    fun parse(file: File): KtlintReport
}
