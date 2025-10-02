package org.example.org.jazzilla.danger.ktlint.parser

import org.example.org.jazzilla.danger.ktlint.model.KtlintIssueReport
import java.io.File

interface KtlintReportParser {
    fun parse(file: File): KtlintIssueReport
}
