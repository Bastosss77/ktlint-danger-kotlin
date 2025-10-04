package io.github.bastosss77.danger.ktlint.reporter

import io.github.bastosss77.danger.ktlint.model.KtlintIssue
import io.github.bastosss77.danger.ktlint.model.KtlintIssueReport
import systems.danger.kotlin.sdk.DangerContext
import java.io.File

internal class DefaultReporter(
    private val context: DangerContext,
) : KtlintReporter {
    private val rootPath = File("").absolutePath

    //TODO: Add message with count of issues

    override fun report(report: KtlintIssueReport) {
        if (!report.isClean) {
            report.issues.forEach { fileIssue ->
                val filePath = fileIssue.file

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

    private fun prepareMessage(issue: KtlintIssue): String =
        """
        **Ktlint** : ${issue.message}
        **Rule** : ${issue.rule}
        """.trimIndent()

    private fun cleanFilePath(path: String): String? {
        if (path.startsWith(rootPath)) {
            return path.removePrefix(rootPath + File.separator)
        }

        return null
    }
}
