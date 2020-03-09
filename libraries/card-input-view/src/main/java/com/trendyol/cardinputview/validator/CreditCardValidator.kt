package com.trendyol.cardinputview.validator

internal class CreditCardValidator {

    fun isCardNumberValid(cardNumber: String?): Boolean {
        return LuhnValidator().isValid(cardNumber)
    }

    fun isCvvValid(cvv: String?): Boolean = cvv?.matches("\\d\\d\\d".toRegex()) == true

    fun isExpiryMonthValid(month: String?) = month?.matches("\\d\\d".toRegex()) == true

    fun isExpiryYearValid(year: String?) = year?.matches("\\d\\d".toRegex()) == true
}
