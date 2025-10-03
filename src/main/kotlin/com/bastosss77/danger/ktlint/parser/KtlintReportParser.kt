package com.bastosss77.danger.ktlint.parser

import com.bastosss77.danger.ktlint.model.KtlintIssueReport
import java.io.File

interface KtlintReportParser {
    fun parse(file: File): KtlintIssueReport
}
