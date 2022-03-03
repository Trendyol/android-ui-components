package com.trendyol.cardinputview.formatter

import com.trendyol.cardinputview.CreditCardType
import com.trendyol.cardinputview.CreditCardType.Companion.DIGIT_PATTERN_MARK
import com.trendyol.cardinputview.removeNonDigitCharacters

internal class CreditCardMasker {

    fun mask(creditCardNumber: String, supportedCardTypes: List<CreditCardType>): String {
        val clearedCreditCardNumber = creditCardNumber.removeNonDigitCharacters()
        val creditCardType = CreditCardType.getCreditCardType(supportedCardTypes, clearedCreditCardNumber)
        return format(clearedCreditCardNumber, creditCardType.digitPattern)
    }

    private fun format(cardNumber: String, pattern: String): String {
        var formattedCardNumber = ""
        var cardNumberIteratorIndex = 0
        run loop@{
            pattern.forEach { character ->
                if (character == DIGIT_PATTERN_MARK) {
                    val digit = cardNumber.getOrNull(cardNumberIteratorIndex) ?: return@loop
                    formattedCardNumber += digit
                    cardNumberIteratorIndex++
                } else {
                    formattedCardNumber += character
                }
            }
        }
        return formattedCardNumber.trim()
    }
}
