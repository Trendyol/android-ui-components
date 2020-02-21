package com.trendyol.suggestioninputview

object RuleValidator {

    fun isValid(rule: Rule?, value: String): Boolean {
        if(rule == null) return true
        return when(rule.type) {
            RuleTypes.MUST_BIGGER -> {
                rule.value?.toDoubleOrNull() ?: 0.0 < value.toDoubleOrNull() ?: 0.0
            }
            RuleTypes.MUST_SMALL -> {
                rule.value?.toDoubleOrNull() ?: 0.0 > value.toDoubleOrNull() ?: 0.0
            }
            RuleTypes.MUST_EQUALS -> {
                rule.value?.toDoubleOrNull() ?: 0.0 == value.toDoubleOrNull() ?: 0.0
            }
            RuleTypes.MUST_NOT_EQUALS -> {
                rule.value?.toDoubleOrNull() ?: 0.0 != value.toDoubleOrNull() ?: 0.0
            }
            else -> {
                false
            }
        }
    }
}