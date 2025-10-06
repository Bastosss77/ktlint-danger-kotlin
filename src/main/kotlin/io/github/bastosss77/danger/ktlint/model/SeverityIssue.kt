package io.github.bastosss77.danger.ktlint.model

enum class SeverityIssue {
    ERROR,
    WARNING,
    INFO;

    companion object {
        fun from(value: String): SeverityIssue? =
            when(value) {
                "error" -> ERROR
                "warning" -> WARNING
                "info" -> INFO
                else -> null
            }
    }
}