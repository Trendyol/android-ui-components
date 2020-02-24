package com.trendyol.suggestioninputview

object RuleValidator {

    fun validate(rules: List<Rule>?, value: String): Pair<Boolean, Rule?> {
        var valid = true
        var invalidRule: Rule? = null

        if (rules == null) valid = true
        rules?.forEach { rule ->
            when (rule.type) {
                RuleTypes.MUST_BIGGER -> {
                    if (rule.value?.toDoubleOrNull() ?: 0.0 > value.toDoubleOrNull() ?: 0.0) {
                        invalidRule = rule
                        valid = false
                    }
                }
                RuleTypes.MUST_SMALL -> {
                    if (rule.value?.toDoubleOrNull() ?: 0.0 < value.toDoubleOrNull() ?: 0.0) {
                        invalidRule = rule
                        valid = false
                    }
                }
                RuleTypes.MUST_EQUALS -> {
                    if (rule.value?.toDoubleOrNull() ?: 0.0 != value.toDoubleOrNull() ?: 0.0) {
                        invalidRule = rule
                        valid = false
                    }
                }
                RuleTypes.MUST_NOT_EQUALS -> {
                    if (rule.value?.toDoubleOrNull() ?: 0.0 == value.toDoubleOrNull() ?: 0.0) {
                        invalidRule = rule
                        valid = false
                    }
                }
            }
        }
        return Pair(valid, invalidRule)
    }
}