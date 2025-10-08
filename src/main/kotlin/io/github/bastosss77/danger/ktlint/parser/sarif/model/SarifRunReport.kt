package io.github.bastosss77.danger.ktlint.parser.sarif.model

import kotlinx.serialization.Serializable

@Serializable
data class SarifRunReport(
    val results: List<SarifRunResultReport>,
)
