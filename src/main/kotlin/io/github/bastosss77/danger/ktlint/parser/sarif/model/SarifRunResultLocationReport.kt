package io.github.bastosss77.danger.ktlint.parser.sarif.model

import kotlinx.serialization.Serializable

@Serializable
data class SarifRunResultLocationReport(
    val physicalLocation: SarifRunResultPhysicalLocationReport,
)

@Serializable
data class SarifRunResultPhysicalLocationReport(
    val artifactLocation: SarifRunResultArtifactLocationReport,
    val region: SarifRunResultLocationRegionReport,
)

@Serializable
data class SarifRunResultLocationRegionReport(
    val startColumn: Int,
    val startLine: Int,
)

@Serializable
data class SarifRunResultArtifactLocationReport(
    val uri: String,
)
