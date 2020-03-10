package com.trendyol.cardinputview

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt

data class CardInputViewState(
    val cardNumberTitle: String,
    val expiryTitle: String,
    val cvvTitle: String,
    private val expiryMonthTitle: String,
    private val expiryYearTitle: String,
    val validationEnabled: Boolean = false,
    val showCvvInfoButton: Boolean = true,
    private val inputBackgroundDrawable: Drawable? = null,
    private val inputErrorBackgroundDrawable: Drawable? = null,
    @ColorInt val inputTextColor: Int = Color.DKGRAY,
    @ColorInt val titleTextColor: Int = Color.BLACK,
    @ColorInt val cvvInfoColor: Int = Color.RED,
    val cardTypeLogoDrawable: Drawable? = null,
    val cardBankLogoDrawable: Drawable? = null,
    private val cardNumberValid: Boolean = true,
    private val expiryMonthValid: Boolean = true,
    private val expiryYearValid: Boolean = true,
    private val cvvValid: Boolean = true,
    var cardInformation: CardInformation = CardInformation()
) {

    var cardNumber: String = cardInformation.cardNumber
        set(value) {
            cardInformation = cardInformation.copy(cardNumber = value)
            field = value
        }
    var cvv: String = cardInformation.cvv
        set(value) {
            cardInformation = cardInformation.copy(cvv = value)
            field = value
        }
    var expiryMonth: String =
        if (cardInformation.expiryMonth.isNotEmpty()) cardInformation.expiryMonth else expiryMonthTitle
        set(value) {
            cardInformation = cardInformation.copy(expiryMonth = value)
            field = value
        }
    var expiryYear: String =
        if (cardInformation.expiryYear.isNotEmpty()) cardInformation.expiryYear else expiryYearTitle
        set(value) {
            cardInformation = cardInformation.copy(expiryYear = value)
            field = value
        }
    val dividerVisibility: Int = if (cardBankLogoDrawable != null) View.VISIBLE else View.GONE
    val cvvInfoButtonVisibility: Int = if (showCvvInfoButton) View.VISIBLE else View.GONE
    val cardNumberBackground: Drawable? =
        if (cardNumberValid) {
            inputBackgroundDrawable
        } else {
            inputErrorBackgroundDrawable
        }?.constantState?.newDrawable()
    val expiryMonthBackground: Drawable? =
        if (expiryMonthValid) {
            inputBackgroundDrawable
        } else {
            inputErrorBackgroundDrawable
        }?.constantState?.newDrawable()
    val expiryYearBackground: Drawable? =
        if (expiryYearValid) {
            inputBackgroundDrawable
        } else {
            inputErrorBackgroundDrawable
        }?.constantState?.newDrawable()
    val cvvBackground: Drawable? =
        if (cvvValid) {
            inputBackgroundDrawable
        } else {
            inputErrorBackgroundDrawable
        }?.constantState?.newDrawable()

    internal fun reset(): CardInputViewState = copy(
        cardInformation = CardInformation(),
        cardTypeLogoDrawable = null,
        cardBankLogoDrawable = null,
        cardNumberValid = true,
        expiryMonthValid = true,
        expiryYearValid = true,
        cvvValid = true
    )
}
