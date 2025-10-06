package io.github.bastosss77.danger.ktlint.parser.xml

import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.parser.KtlintReportParser
import io.github.bastosss77.danger.ktlint.parser.xml.model.XmlReport
import io.github.bastosss77.danger.ktlint.parser.xml.model.mapToKtlintReport
import nl.adaptivity.xmlutil.newReader
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.xmlStreaming
import java.io.File

class XMLReportParser : KtlintReportParser {
    private val xml = XML.Companion

    override fun parse(file: File): KtlintReport {
        val reader = xmlStreaming.newReader(file.inputStream())

        return xml.decodeFromReader<XmlReport>(reader).mapToKtlintReport()
    }
}