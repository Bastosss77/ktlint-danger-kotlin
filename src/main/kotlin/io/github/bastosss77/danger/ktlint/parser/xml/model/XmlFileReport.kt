package io.github.bastosss77.danger.ktlint.parser.xml.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class XmlFileReport(
    @field:JacksonXmlProperty
    val name: String = "",

    @field:JsonProperty("error")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val errors: List<XmlErrorReport> = emptyList(),
)
