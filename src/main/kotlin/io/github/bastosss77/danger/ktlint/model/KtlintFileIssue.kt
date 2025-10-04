package io.github.bastosss77.danger.ktlint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtlintFileIssue(
    val file: String,
    @SerialName("errors")
    val issues: Set<KtlintIssue>,
)
