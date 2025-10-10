package io.github.bastosss77.danger.ktlint.parser.xml

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.parser.KtlintReportParser
import io.github.bastosss77.danger.ktlint.parser.xml.model.XmlReport
import io.github.bastosss77.danger.ktlint.parser.xml.model.mapToKtlintReport
import java.io.File

class XMLReportParser : KtlintReportParser {
    private val xmlParser = XmlMapper()

    override fun parse(file: File): KtlintReport =
        xmlParser.readValue<XmlReport>(file).mapToKtlintReport()

}
