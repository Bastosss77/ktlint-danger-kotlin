package io.github.bastosss77.danger.ktlint.parser.json.model

import io.github.bastosss77.danger.ktlint.model.IssueReport
import kotlinx.serialization.Serializable

@Serializable
data class JsonFileReport(
    val file: String,
    val errors: Set<JsonErrorReport>
)