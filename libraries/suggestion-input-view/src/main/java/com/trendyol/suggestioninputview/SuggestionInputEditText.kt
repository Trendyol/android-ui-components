package com.trendyol.suggestioninputview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

internal class SuggestionInputEditText : AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var suffix = ""
    private val suffixPaddingLeft =
        resources.getDimensionPixelSize(R.dimen.horizontal_padding_suggestion_item)

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val textWidth: Float = paint.measureText(suffix + text) + suffixPaddingLeft
        canvas.drawText(suffix, textWidth, baseline.toFloat(), paint)
    }
}