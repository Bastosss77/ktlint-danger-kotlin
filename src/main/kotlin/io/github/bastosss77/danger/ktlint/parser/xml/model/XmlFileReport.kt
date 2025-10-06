package io.github.bastosss77.danger.ktlint.parser.xml.model

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("file", "", "")
data class XmlFileReport(
    @XmlSerialName("name")
    val name: String,
    @XmlSerialName("error")
    val errors: List<XmlErrorReport>,
)
