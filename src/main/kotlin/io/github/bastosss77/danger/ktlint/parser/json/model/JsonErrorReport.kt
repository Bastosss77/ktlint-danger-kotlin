package io.github.bastosss77.danger.ktlint.parser.json.model

data class JsonErrorReport(
    val line: Int,
    val column: Int,
    val message: String,
    val rule: String,
)
