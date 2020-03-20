package com.trendyol.uicomponents.quantitypickerview

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt

data class QuantityPickerViewState(
    private val text: String,
    @ColorInt private val textColor: Int = Color.parseColor(COLOR_ORANGE),
    private val textSize: Int,
    private val textStyle: Int = 0,
    @ColorInt private val quantityTextColor: Int = Color.parseColor(COLOR_QUANTITY_TEXT),
    private val quantityTextSize: Int,
    private val quantityTextStyle: Int = 0,
    val currentQuantity: Int = 0,
    val backgroundDrawable: Drawable,
    @ColorInt val progressTintColor: Int = Color.parseColor(COLOR_ORANGE),
    val addIconDrawable: Drawable,
    private val subtractIconDrawable: Drawable,
    private val removeIconDrawable: Drawable,
    private val showLoading: Boolean = false
) {

    internal fun isInQuantityMode(): Boolean = currentQuantity > 0

    private fun isSingleQuantity(): Boolean = currentQuantity == 1

    internal fun isLoading(): Boolean = showLoading

    fun getLeftIconDrawable(): Drawable =
        if (isSingleQuantity()) removeIconDrawable else subtractIconDrawable

    fun getAddSubtractButtonVisibility(): Int =
        if (isInQuantityMode()) View.VISIBLE else View.GONE

    fun getProgressBarVisibility(): Int = if (showLoading) View.VISIBLE else View.GONE

    fun getTextVisibility(): Int = if (showLoading) View.GONE else View.VISIBLE

    fun getText(): String = if (isInQuantityMode()) currentQuantity.toString() else text

    fun getTextAppearance(): QuantityPickerTextAppearance =
        if (isInQuantityMode()) {
            QuantityPickerTextAppearance(quantityTextColor, quantityTextSize, quantityTextStyle)
        } else {
            QuantityPickerTextAppearance(textColor, textSize, textStyle)
        }

    internal fun getWithLoading(increment: Boolean? = null): QuantityPickerViewState =
        when (increment) {
            true -> copy(showLoading = true, currentQuantity = currentQuantity + 1)
            false -> copy(showLoading = true, currentQuantity = currentQuantity - 1)
            else -> copy(showLoading = true)
        }

    internal fun getWithSubtractValue(): QuantityPickerViewState? =
        if (currentQuantity >= 1) copy(currentQuantity = currentQuantity - 1) else null

    internal fun getWithAddValue(): QuantityPickerViewState =
        copy(currentQuantity = currentQuantity + 1)

    internal fun getWithQuantity(quantity: Int): QuantityPickerViewState =
        copy(currentQuantity = quantity, showLoading = false)

    internal fun stopLoading(): QuantityPickerViewState = copy(showLoading = false)

    internal fun reset(): QuantityPickerViewState = copy(currentQuantity = 0, showLoading = false)

    companion object {

        private const val COLOR_ORANGE = "#f27a1a"
        private const val COLOR_QUANTITY_TEXT = "#666666"
    }
}
