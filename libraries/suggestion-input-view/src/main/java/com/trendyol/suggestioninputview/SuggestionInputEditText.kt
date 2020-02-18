package com.trendyol.suggestioninputview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class SuggestionInputEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private var suffix = ""
    private val suffixPaddingLeft = context.resources.getDimensionPixelSize(R.dimen.horizontal_padding_suggestion_item)
    private val suffixRect = Rect()

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        paint.getTextBounds(suffix, 0, suffix.length, suffixRect)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

            val textWidth: Float = paint.measureText(suffix + text) + suffixPaddingLeft
            canvas.drawText(suffix, textWidth, baseline.toFloat(), paint)

    }
}