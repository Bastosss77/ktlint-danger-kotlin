package io.github.bastosss77.danger.ktlint.parser.sarif.model

import io.github.bastosss77.danger.ktlint.model.FileIssueReport
import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import io.github.bastosss77.danger.ktlint.model.RuleReport
import io.github.bastosss77.danger.ktlint.model.SeverityIssue
import kotlinx.serialization.Serializable

@Serializable
data class SarifReport(
    val runs: List<SarifRunReport>
)

fun SarifReport.mapToKtlintReport() : KtlintReport {
    val filesReport = runs.flatMap { run ->
        run.results
            .groupBy { it.locations.firstOrNull()?.physicalLocation?.artifactLocation?.uri }
            .mapNotNull { (fileUri, runResult) ->
                if(fileUri == null) return@mapNotNull null

                val issues = mutableSetOf<IssueReport>()

                runResult.forEach { run ->
                    issues.add(
                        IssueReport(
                            line = run.locations.first().physicalLocation.region.startLine,
                            column = run.locations.first().physicalLocation.region.startColumn,
                            message = run.message.text,
                            rule = RuleReport.parse(run.ruleId),
                            severity = SeverityIssue.from(run.level)
                        )
                    )
                }

                if(issues.isEmpty()) return@mapNotNull null

                return@mapNotNull FileIssueReport(
                    name = fileUri,
                    issues = issues.toSet()
                )
            }
    }


    return KtlintReport(filesReport.toSet())
}
