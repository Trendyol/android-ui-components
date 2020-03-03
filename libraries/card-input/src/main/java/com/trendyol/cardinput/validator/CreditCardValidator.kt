package com.trendyol.cardinput.validator

internal class CreditCardValidator {

    fun isCardNumberValid(cardNumber: String?): Boolean = LuhnValidator.isValid(cardNumber)

    fun isCvvValid(cvv: String?): Boolean = cvv?.matches("\\d\\d\\d".toRegex()) == true

    fun isExpiryMonthValid(month: String?) = month?.matches("\\d\\d".toRegex()) == true

    fun isExpiryYearValid(year: String?) = year?.matches("\\d\\d".toRegex()) == true
}
