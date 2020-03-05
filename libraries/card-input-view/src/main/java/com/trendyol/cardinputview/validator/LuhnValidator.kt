package com.trendyol.cardinputview.validator

internal class LuhnValidator {

    fun isValid(input: String?): Boolean {
        if (input?.replace(" ", "")?.length != LENGTH_CARD_NUMBER) return false

        val sanitizedInput = input.replace(" ", "")

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

    companion object {
        private const val LENGTH_CARD_NUMBER = 16
    }
}