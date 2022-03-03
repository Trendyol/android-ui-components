package com.trendyol.cardinputview.validator

import com.trendyol.cardinputview.CreditCardType

internal class CreditCardValidator {

    fun isCardNumberValid(cardNumber: String?, supportedCardTypes: List<CreditCardType>): Boolean {
        return LuhnValidator().isValid(cardNumber, supportedCardTypes)
    }

    fun isCvvValid(cvv: String?, creditCardNumber: String?, supportedCardTypes: List<CreditCardType>): Boolean {
        val cvvLength = CreditCardType.getCreditCardType(supportedCardTypes, creditCardNumber).cvvLength
        var cvvRegex = ""
        repeat(cvvLength) { cvvRegex += NUMBER_REGEX_PATTERN }

        return cvv?.matches(cvvRegex.toRegex()) == true
    }

    fun isExpiryMonthValid(month: String?) = month?.matches("\\d\\d".toRegex()) == true

    fun isExpiryYearValid(year: String?) = year?.matches("\\d\\d".toRegex()) == true

    companion object {
        private const val NUMBER_REGEX_PATTERN = "\\d"
    }
}
