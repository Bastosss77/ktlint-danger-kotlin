package org.example.org.jazzilla.danger.ktlint.model

import kotlinx.serialization.Serializable

@Serializable
data class KtlintIssue(
    val line: Int,
    val column: Int,
    val message: String,
    val rule: String,
)
