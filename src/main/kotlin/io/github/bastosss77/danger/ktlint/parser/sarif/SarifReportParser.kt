package io.github.bastosss77.danger.ktlint.parser.sarif

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.parser.KtlintReportParser
import io.github.bastosss77.danger.ktlint.parser.sarif.model.SarifReport
import io.github.bastosss77.danger.ktlint.parser.sarif.model.mapToKtlintReport
import java.io.File

class SarifReportParser : KtlintReportParser {
    private val jsonParser = ObjectMapper()
        .registerKotlinModule()
        .apply {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }

    override fun parse(file: File): KtlintReport =
        jsonParser.readValue<SarifReport>(file).mapToKtlintReport()
}
