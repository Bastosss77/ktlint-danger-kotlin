package io.github.bastosss77.danger.ktlint.parser.sarif.model

data class SarifRunResultLocationReport(
    val physicalLocation: SarifRunResultPhysicalLocationReport,
)

data class SarifRunResultPhysicalLocationReport(
    val artifactLocation: SarifRunResultArtifactLocationReport,
    val region: SarifRunResultLocationRegionReport,
)

data class SarifRunResultLocationRegionReport(
    val startColumn: Int,
    val startLine: Int,
)

data class SarifRunResultArtifactLocationReport(
    val uri: String,
)
