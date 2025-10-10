package io.github.bastosss77.danger.ktlint.parser.sarif.model

data class SarifRunResultReport(
    val level: String,
    val locations: List<SarifRunResultLocationReport>,
    val message: SarifRunResultMessageReport,
    val ruleId: String,
)
