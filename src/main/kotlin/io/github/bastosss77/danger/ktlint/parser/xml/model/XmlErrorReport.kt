package io.github.bastosss77.danger.ktlint.parser.xml.model

import kotlinx.serialization.Serializable

@Serializable
data class XmlErrorReport(
    val line: Int,
    val column: Int,
    val message: String,
    val source: String,
    val severity: String,
)
