package com.trendyol.uicomponents.ratingbar

import androidx.annotation.ColorInt

data class RatingBarViewState(
    private val starCount: Int,
    @ColorInt private val starHighlightColor: Int,
    @ColorInt private val starDefaultColor: Int
) {

    fun getTint1(): Int = if (1 <= starCount) starHighlightColor else starDefaultColor

    fun getTint2(): Int = if (2 <= starCount) starHighlightColor else starDefaultColor

    fun getTint3(): Int = if (3 <= starCount) starHighlightColor else starDefaultColor

    fun getTint4(): Int = if (4 <= starCount) starHighlightColor else starDefaultColor

    fun getTint5(): Int = if (5 <= starCount) starHighlightColor else starDefaultColor
}
