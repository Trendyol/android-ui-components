package com.trendyol.uicomponents.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout

class Toolbar : ConstraintLayout {

    var viewState: ToolbarViewState? = ToolbarViewState()
        set(value) {
            if (value == null) return
            field = value
            applyViewState()
        }

    var leftImageClickListener: (() -> Unit)? = null
    var middleImageClickListener: (() -> Unit)? = null
    var rightImageClickListener: (() -> Unit)? = null
    var upperLeftTextClickListener: (() -> Unit)? = null
    var lowerLeftTextClickListener: (() -> Unit)? = null
    var middleTextClickListener: (() -> Unit)? = null
    var upperRightTextClickListener: (() -> Unit)? = null
    var lowerRightTextClickListener: (() -> Unit)? = null

    private val imageBackground: AppCompatImageView by lazy { findViewById<AppCompatImageView>(R.id.imageBackground) }
    private val imageLeft: AppCompatImageView by lazy { findViewById<AppCompatImageView>(R.id.imageLeft) }
    private val imageRight: AppCompatImageView by lazy { findViewById<AppCompatImageView>(R.id.imageRight) }
    private val imageMiddle: AppCompatImageView by lazy { findViewById<AppCompatImageView>(R.id.imageMiddle) }
    private val textLeftUp: AppCompatTextView by lazy { findViewById<AppCompatTextView>(R.id.textLeftUp) }
    private val textLeftDown: AppCompatTextView by lazy { findViewById<AppCompatTextView>(R.id.textLeftDown) }
    private val textRightUp: AppCompatTextView by lazy { findViewById<AppCompatTextView>(R.id.textRightUp) }
    private val textRightDown: AppCompatTextView by lazy { findViewById<AppCompatTextView>(R.id.textRightDown) }
    private val textMiddle: AppCompatTextView by lazy { findViewById<AppCompatTextView>(R.id.textMiddle) }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        readFromAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        readFromAttributes(attrs, defStyleAttr)
    }

    init {
        inflate(context, R.layout.view_toolbar, this)

        imageLeft.setOnClickListener { leftImageClickListener?.invoke() }
        imageMiddle.setOnClickListener { middleImageClickListener?.invoke() }
        imageRight.setOnClickListener { rightImageClickListener?.invoke() }
        textLeftUp.setOnClickListener { upperLeftTextClickListener?.invoke() }
        textLeftDown.setOnClickListener { lowerLeftTextClickListener?.invoke() }
        textMiddle.setOnClickListener { middleTextClickListener?.invoke() }
        textRightUp.setOnClickListener { upperRightTextClickListener?.invoke() }
        textRightDown.setOnClickListener { lowerRightTextClickListener?.invoke() }
    }

    private fun readFromAttributes(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.Toolbar,
            defStyleAttr,
            0
        )?.apply {
            val toolbarBackground = getResourceId(R.styleable.Toolbar_toolbarBackground, android.R.color.white)
            val leftImageDrawableResId =
                getResourceId(
                    R.styleable.Toolbar_leftImageDrawable,
                    R.drawable.trendyol_uicomponents_toolbar_arrow_back
                )
            val middleImageDrawableResId = getResourceId(R.styleable.Toolbar_middleImageDrawable, 0)
            val rightImageDrawableResId = getResourceId(R.styleable.Toolbar_rightImageDrawable, 0)

            val upperLeftText = getString(R.styleable.Toolbar_upperLeftText)
            val lowerLeftText = getString(R.styleable.Toolbar_lowerLeftText)
            val middleText = getString(R.styleable.Toolbar_middleText)
            val upperRightText = getString(R.styleable.Toolbar_upperRightText)
            val lowerRightText = getString(R.styleable.Toolbar_lowerRightText)

            val leftTextDefaultMarginStart =
                resources.getDimensionPixelOffset(R.dimen.trendyol_uicomponents_toolbar_margin_left_side_text)
            val rightTextDefaultMarginEnd =
                resources.getDimensionPixelOffset(R.dimen.trendyol_uicomponents_toolbar_margin_outer)

            val upperLeftTextMarginStart =
                getDimensionPixelOffset(R.styleable.Toolbar_upperLeftTextMarginStart, leftTextDefaultMarginStart)
            val lowerLeftTextMarginStart =
                getDimensionPixelOffset(R.styleable.Toolbar_lowerLeftTextMarginStart, leftTextDefaultMarginStart)

            val upperRightTextMarginEnd =
                getDimensionPixelOffset(R.styleable.Toolbar_upperRightTextMarginEnd, rightTextDefaultMarginEnd)
            val lowerRightTextMarginEnd =
                getDimensionPixelOffset(R.styleable.Toolbar_lowerRightTextMarginEnd, rightTextDefaultMarginEnd)
            val rightImageDrawableMarginEnd =
                getDimensionPixelOffset(R.styleable.Toolbar_rightImageDrawableMarginEnd, 0)
            val leftImageDrawableMarginStart =
                getDimensionPixelOffset(R.styleable.Toolbar_leftImageDrawableMarginStart, 0)
            val hideLeftImage = getBoolean(R.styleable.Toolbar_hideLeftImage, false)

            viewState = ToolbarViewState(
                upperLeftText = upperLeftText,
                lowerLeftText = lowerLeftText,
                middleText = middleText,
                upperRightText = upperRightText,
                lowerRightText = lowerRightText,
                leftImageDrawableResId = leftImageDrawableResId,
                middleImageDrawableResId = middleImageDrawableResId,
                rightImageDrawableResId = rightImageDrawableResId,
                toolbarBackground = toolbarBackground,
                upperLeftTextMarginStartInPixel = upperLeftTextMarginStart,
                lowerLeftTextMarginStartInPixel = lowerLeftTextMarginStart,
                upperRightTextMarginEndInPixel = upperRightTextMarginEnd,
                lowerRightTextMarginEndInPixel = lowerRightTextMarginEnd,
                rightImageDrawableMarginEndInPixel = rightImageDrawableMarginEnd,
                leftImageDrawableMarginStartInPixel = leftImageDrawableMarginStart,
                hideLeftImage = hideLeftImage
            )
        }
    }

    private fun applyViewState() {
        with(viewState!!) {
            imageBackground.setDrawableResource(toolbarBackground)

            imageLeft.setDrawableResource(leftImageDrawableResId)
            imageLeft.setStartMargin(leftImageDrawableMarginStartInPixel)
            imageLeft.visibility = if (hideLeftImage) View.GONE else View.VISIBLE

            imageMiddle.setDrawableResource(middleImageDrawableResId)

            imageRight.setDrawableResource(rightImageDrawableResId)
            imageRight.setEndMargin(rightImageDrawableMarginEndInPixel)

            textLeftUp.text = upperLeftTextValue
            textLeftUp.visibility = upperLeftTextVisibility
            textLeftUp.setStyle(upperLeftTextAppearance)
            textLeftUp.setStartMargin(upperLeftTextMarginStartInPixel)

            textLeftDown.text = lowerLeftTextValue
            textLeftDown.visibility = lowerLeftTextVisibility
            textLeftDown.setStyle(lowerLeftTextAppearance)
            textLeftDown.setStartMargin(lowerLeftTextMarginStartInPixel)

            textMiddle.text = middleTextValue
            textMiddle.visibility = middleTextVisibility
            textMiddle.setStyle(middleTextAppearance)

            textRightUp.text = upperRightTextValue
            textRightUp.visibility = upperRightTextVisibility
            textRightUp.setStyle(upperRightTextAppearance)
            textRightUp.setEndMargin(upperRightTextMarginEndInPixel)
            textRightUp.isEnabled = isUpperRightTextEnabled

            textRightDown.text = lowerRightTextValue
            textRightDown.visibility = lowerRightTextVisibility
            textRightDown.setStyle(lowerRightTextAppearance)
            textRightDown.setEndMargin(lowerRightTextMarginEndInPixel)
        }
    }
}
