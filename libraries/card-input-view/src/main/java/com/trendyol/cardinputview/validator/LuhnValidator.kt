package com.trendyol.cardinputview.validator

import com.trendyol.cardinputview.CreditCardType

internal class LuhnValidator {

    fun isValid(input: String?, supportedCardTypes: List<CreditCardType>): Boolean {
        val creditCardNumber = input ?: return false
        val creditCardType = CreditCardType.getCreditCardType(supportedCardTypes, creditCardNumber)
        val sanitizedInput = creditCardNumber.replace(" ", "")
        val sanitizedCardTypePattern = creditCardType.digitPattern.replace(" ", "")

        if (sanitizedInput.length < sanitizedCardTypePattern.length) return false

        return when {
            valid(sanitizedInput) -> checksum(sanitizedInput) % 10 == 0
            else -> false
        }
    }

    private fun valid(input: String) = input.all(Char::isDigit) && input.length > 1

    private fun checksum(input: String) = addends(input).sum()

    private fun addends(input: String) = input.digits().mapIndexed { i, j ->
        when {
            (input.length - i + 1) % 2 == 0 -> j
            j >= 5 -> j * 2 - 9
            else -> j * 2
        }
    }

    private fun String.digits() = this.map(Character::getNumericValue)
}
