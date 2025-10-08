package io.github.bastosss77.danger.ktlint.parser.sarif.model

import kotlinx.serialization.Serializable

@Serializable
data class SarifRunResultMessageReport(
    val text: String,
)
