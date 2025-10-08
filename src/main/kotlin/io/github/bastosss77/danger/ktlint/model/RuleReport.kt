package io.github.bastosss77.danger.ktlint.model

sealed interface RuleReport {
    val name: String
    val fullName: String

    data class Standard(
        override val name: String,
    ) : RuleReport {
        override val fullName: String
            get() = "standard:$name"
    }

    data class Experimental(
        override val name: String,
    ) : RuleReport {
        override val fullName: String
            get() = "experimental:$name"
    }

    companion object {
        fun parse(rule: String): RuleReport {
            val (type, rule) =
                rule.split(':').let {
                    it.first() to it[1]
                }

            return when (type) {
                "standard" -> Standard(rule)
                "experimental" -> Experimental(rule)
                else -> throw IllegalStateException("Unknown rule type $type")
            }
        }
    }
}
