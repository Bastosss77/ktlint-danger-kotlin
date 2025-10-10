package io.github.bastosss77.danger.ktlint.parser.xml.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class XmlErrorReport(
    @field:JacksonXmlProperty
    val line: Int = 0,

    @field:JacksonXmlProperty
    val column: Int = 0,

    @field:JacksonXmlProperty
    val message: String = "",

    @field:JacksonXmlProperty
    val source: String = "",

    @field:JacksonXmlProperty
    val severity: String = "",
)
