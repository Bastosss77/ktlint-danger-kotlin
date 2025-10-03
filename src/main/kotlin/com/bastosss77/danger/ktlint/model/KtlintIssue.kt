package com.bastosss77.danger.ktlint.model

import kotlinx.serialization.Serializable

@Serializable
data class KtlintIssue(
    val line: Int,
    val column: Int,
    val message: String,
    val rule: String,
)
