package com.trendyol.cardinputview

enum class CreditCardType(
    val prefixLength: Int,
    val prefixes: List<Int>,
    val cvvLength: Int,
    val digitPattern: String,
) {
    MASTER_CARD(2, (51..55).toList(), 3, "xxxx xxxx xxxx xxxx"),
    VISA(1, listOf(4), 3, "xxxx xxxx xxxx xxxx"),
    AMERICAN_EXPRESS(2, listOf(34, 37), 4, "xxxx xxxxxx xxxxx"),
    DEFAULT(1, (0..9).toList(), 3, "xxxx xxxx xxxx xxxx");

    companion object {

        fun getCreditCardType(creditCardTypes: List<CreditCardType>, creditCardNumber: String?): CreditCardType {
            if (creditCardNumber.isNullOrBlank() || creditCardTypes.isEmpty()) return DEFAULT

            run loop@{
                creditCardTypes.forEach { type ->
                    if (creditCardNumber.length < type.prefixLength) return@loop

                    val uniquePrefix = creditCardNumber.substring(0 until type.prefixLength).toInt()
                    val matches = type.prefixes.contains(uniquePrefix)
                    if (matches) return type
                }
            }
            return DEFAULT
        }

        const val DIGIT_PATTERN_MARK = 'x'
    }
}
