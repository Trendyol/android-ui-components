package com.trendyol.suggestioninputview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
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
    private var isSuffixVisible: Boolean = false
    private val suffixPaddingLeft =
        resources.getDimensionPixelSize(R.dimen.horizontal_padding_suggestion_item)

    fun setSuffix(suffix: String) {
        this.suffix = suffix
    }

    fun setSuffixVisible(isActive: Boolean) {
        isSuffixVisible = isActive
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(isSuffixVisible.not() && text?.isEmpty() == true) return
        val suffixWidth: Float = paint.measureText(suffix) / 2
        val textWidth: Float = paint.measureText(text.toString()) + suffixPaddingLeft
        canvas.drawText(suffix, textWidth + suffixWidth, baseline.toFloat(), paint)
    }
}