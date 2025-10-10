package io.github.bastosss77.danger.ktlint.parser.json.model

data class JsonFileReport(
    val file: String,
    val errors: Set<JsonErrorReport>,
)
