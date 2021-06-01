package com.trendyol.uicomponents.quantitypickerview

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt

data class QuantityPickerViewState(
    private val text: String,
    @ColorInt private val textColor: Int,
    private val textSize: Int,
    private val textStyle: Int = 0,
    @ColorInt private val quantityTextColor: Int,
    private val quantityTextSize: Int,
    private val quantityTextStyle: Int = 0,
    val currentQuantity: Int = 0,
    val backgroundDrawable: Drawable,
    @ColorInt val progressTintColor: Int,
    private val addIconDrawable: Drawable,
    private val subtractIconDrawable: Drawable,
    private val removeIconDrawable: Drawable,
    private val showLoading: Boolean = false,
    private val quantityBackgroundDrawable: Drawable,
    val expansionState: ExpansionState = ExpansionState.NonCollapsible,
    val orientation: Int = QuantityPickerView.HORIZONTAL_ORIENTATION,
    val buttonHorizontalPadding: Int,
    val buttonVerticalPadding: Int,
    val progressVerticalPadding: Int,
    val quantityBackgroundVerticalPadding: Int,
    val maxQuantity: Int = -1,
    val minQuantity: Int = -1,
    val disabledAddIconDrawable: Drawable = addIconDrawable,
    val disabledSubtractIconDrawable: Drawable = subtractIconDrawable
) {

    internal fun isInQuantityMode(): Boolean = currentQuantity > 0

    internal fun isLoading(): Boolean = showLoading

    fun getLeftIconDrawable(): Drawable {
        return if (currentQuantity <= 1 && isMinQuantitySet().not()) removeIconDrawable else getSubtractIconDrawable()
    }

    fun getAddButtonVisibility(): Int =
        if (isInQuantityMode() || expansionState is ExpansionState.Collapsible || showLoading) View.VISIBLE else View.GONE

    fun getSubtractButtonVisibility(): Int =
        if (isInQuantityMode() && expansionState.isExpanded() || showLoading) View.VISIBLE else View.GONE

    fun getProgressBarVisibility(): Int =
        if (showLoading && expansionState.isExpanded()) View.VISIBLE else View.GONE

    fun getRootViewHorizontalPadding(context: Context): Int {
        return if (expansionState.isExpanded()) context.resources.getDimensionPixelSize(R.dimen.qpv_default_padding)
        else context.resources.getDimensionPixelSize(R.dimen.qpv_default_small_padding)
    }

    fun getQuantityPickerTextAppearance(): QuantityPickerTextAppearance = QuantityPickerTextAppearance(textColor, textSize, textStyle)

    fun getQuantityTextAppearance() = QuantityPickerTextAppearance(quantityTextColor, quantityTextSize, quantityTextStyle)

    fun getQuantity() = currentQuantity.takeIf { it != 0 }?.toString()

    fun getQuantityPickerText() = text

    fun getQuantityBackgroundDrawable(): Drawable = quantityBackgroundDrawable

    fun getQuantityPickerTextVisibility(): Int = if (showLoading.not() && expansionState is ExpansionState.NonCollapsible && expansionState.isExpanded() && currentQuantity == 0) View.VISIBLE else View.GONE

    fun getQuantityVisibility(): Int {
        return if (showLoading.not() && expansionState.isExpanded() && currentQuantity > 0) {
            View.VISIBLE
        } else if (showLoading) {
            View.INVISIBLE
        } else {
            View.GONE
        }
    }

    fun getQuantityBackgroundVisibility(): Int  {
        return if (showLoading.not() && isInQuantityMode() && expansionState.isExpanded()) {
            View.VISIBLE
        } else if (showLoading) {
            View.INVISIBLE
        } else {
            View.GONE
        }
    }

    fun getAddIconDrawable(): Drawable {
        return if (isAddButtonEnabled()) {
            addIconDrawable
        } else {
            disabledAddIconDrawable
        }
    }

    private fun getSubtractIconDrawable(): Drawable {
        return if (isSubtractButtonEnabled()) {
            subtractIconDrawable
        } else {
            disabledSubtractIconDrawable
        }
    }

    fun isAddButtonEnabled(): Boolean {
        return (isMaxQuantitySet() && currentQuantity >= maxQuantity).not()
    }

    fun isSubtractButtonEnabled(): Boolean {
        return (currentQuantity > minQuantity)
    }

    private fun isMinQuantitySet(): Boolean {
        return minQuantity != -1
    }

    private fun isMaxQuantitySet(): Boolean {
        return maxQuantity != -1
    }

    internal fun getWithLoading(): QuantityPickerViewState {
        return copy(
            showLoading = true,
            expansionState = expansionState.expand()
        )
    }

    internal fun getWithSubtractValue(): QuantityPickerViewState? {
        val nextQuantity = currentQuantity - 1
        val nextExpansionState =
            if (nextQuantity == 0) expansionState.collapse() else expansionState
        return if (nextQuantity < 0) null
        else copy(currentQuantity = nextQuantity, expansionState = nextExpansionState)
    }

    internal fun getWithAddValue(): QuantityPickerViewState =
        copy(currentQuantity = currentQuantity + 1, expansionState = expansionState.expand())

    internal fun getWithQuantity(quantity: Int): QuantityPickerViewState {
        val nextExpansionState = if (quantity == 0) expansionState.collapse() else expansionState.expand()
        return copy(currentQuantity = quantity, showLoading = false, expansionState = nextExpansionState)
    }

    internal fun getWithIncrementQuantity(quantity: Int): QuantityPickerViewState {
        val updatedQuantity = currentQuantity + quantity
        val nextExpansionState = if (updatedQuantity == 0) expansionState.collapse() else expansionState
        return copy(currentQuantity = updatedQuantity, showLoading = false, expansionState = nextExpansionState)
    }

    internal fun getWithMaxQuantity(maxQuantity: Int): QuantityPickerViewState {
        return copy(maxQuantity = maxQuantity, showLoading = false)
    }

    internal fun getWithMinQuantity(minQuantity: Int): QuantityPickerViewState {
        return copy(minQuantity = minQuantity, showLoading = false)
    }

    internal fun stopLoading(): QuantityPickerViewState =
        copy(showLoading = false, expansionState = expansionState.expand())

    internal fun reset(): QuantityPickerViewState = copy(currentQuantity = 0, showLoading = false)
    fun isCollapsed(): Boolean {
        return expansionState.isCollapsed()
    }
}

sealed class ExpansionState(open val expanded: Boolean) {
    abstract fun expand(): ExpansionState
    abstract fun collapse(): ExpansionState

    fun isExpanded() = expanded
    fun isCollapsed() = expanded.not()

    data class Collapsible(override val expanded: Boolean) : ExpansionState(expanded) {
        override fun expand(): ExpansionState {
            return copy(expanded = true)
        }

        override fun collapse(): ExpansionState {
            return copy(expanded = false)
        }
    }

    object NonCollapsible : ExpansionState(expanded = true) {
        override fun expand(): ExpansionState {
            return this
        }

        override fun collapse(): ExpansionState {
            return this
        }
    }
}