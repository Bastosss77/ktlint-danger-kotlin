package io.github.bastosss77.danger.ktlint.parser.json.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonErrorReport(
    val line: Int,
    val column: Int,
    val message: String,
    val rule: String
)