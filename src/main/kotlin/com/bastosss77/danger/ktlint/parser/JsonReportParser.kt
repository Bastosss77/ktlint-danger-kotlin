package com.bastosss77.danger.ktlint.parser

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import com.bastosss77.danger.ktlint.model.KtlintFileIssue
import com.bastosss77.danger.ktlint.model.KtlintIssueReport
import java.io.File

internal class JsonReportParser : KtlintReportParser {
    private val json = Json

    @OptIn(ExperimentalSerializationApi::class)
    override fun parse(file: File): KtlintIssueReport {
        val issues =
            json
                .decodeFromStream<Set<KtlintFileIssue>>(file.inputStream())
                .filter { it.issues.isNotEmpty() }
                .toSet()

        return KtlintIssueReport(issues)
    }
}
