package io.github.bastosss77.danger.ktlint.reporter

import io.github.bastosss77.danger.ktlint.model.IssueReport
import io.github.bastosss77.danger.ktlint.model.KtlintReport
import systems.danger.kotlin.sdk.DangerContext
import java.io.File

internal class DefaultReporter(
    private val context: DangerContext,
) : KtlintReporter {
    private val rootPath = File("").absolutePath

    override fun report(report: KtlintReport) {
        if (!report.isClean) {
            report.issues.forEach { fileIssue ->
                val filePath = fileIssue.name

                fileIssue.issues.forEach { issue ->
                    val message = prepareMessage(issue)
                    val path = cleanFilePath(filePath)

                    if (path != null) {
                        context.fail(message, path, issue.line)
                    } else {
                        context.fail(message)
                    }
                }
            }
        }
    }

    private fun prepareMessage(issue: IssueReport): String =
        """
        **Ktlint** : ${issue.message}
        **Rule** : ${issue.rule.fullName}
        """.trimIndent()

    private fun cleanFilePath(path: String): String? {
        if (path.startsWith(rootPath)) {
            return path.removePrefix(rootPath + File.separator)
        }

        return null
    }
}
