package com.trendyol.suggestioninputview

class Rule(val type: RuleTypes?, val value: String?, val errorMessage: String?) {

    data class Builder(
        var type: RuleTypes? = null,
        var value: String? = null,
        var errorMessage: String? = null
    ) {
        fun biggerThan(value: String) = apply {
            this.type = RuleTypes.MUST_BIGGER
            this.value = value
        }

        fun smallerThan(value: String) = apply {
            this.type = RuleTypes.MUST_SMALL
            this.value = value
        }

        fun equalsTo(value: String) = apply {
            this.type = RuleTypes.MUST_EQUALS
            this.value = value
        }

        fun notEqualsTo(value: String) = apply {
            this.type = RuleTypes.MUST_NOT_EQUALS
            this.value = value
        }

        fun errorMessage(message: String) = apply {
            this.errorMessage = message
        }

        fun build(): Rule = Rule(type, value, errorMessage)
    }
}

enum class RuleTypes {
    MUST_SMALL, MUST_BIGGER, MUST_EQUALS, MUST_NOT_EQUALS
}