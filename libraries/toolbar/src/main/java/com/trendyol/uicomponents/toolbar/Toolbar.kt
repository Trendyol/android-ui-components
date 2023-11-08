package com.trendyol.uicomponents.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.trendyol.uicomponents.toolbar.databinding.ViewToolbarBinding

class Toolbar : ConstraintLayout {

    private var binding: ViewToolbarBinding =
        ViewToolbarBinding.inflate(LayoutInflater.from(context), this)

    var viewState: ToolbarViewState = ToolbarViewState()
        set(value) {
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
        binding.imageLeft.setOnClickListener { leftImageClickListener?.invoke() }
        binding.imageMiddle.setOnClickListener { middleImageClickListener?.invoke() }
        binding.imageRight.setDebouncedOnClickListener { rightImageClickListener?.invoke() }
        binding.textLeftUp.setOnClickListener { upperLeftTextClickListener?.invoke() }
        binding.textLeftDown.setOnClickListener { lowerLeftTextClickListener?.invoke() }
        binding.textMiddle.setOnClickListener { middleTextClickListener?.invoke() }
        binding.textRightUp.setOnClickListener { upperRightTextClickListener?.invoke() }
        binding.textRightDown.setOnClickListener { lowerRightTextClickListener?.invoke() }
    }

    private fun readFromAttributes(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.Toolbar,
            defStyleAttr,
            0
        )?.apply {
            val toolbarBackground =
                getResourceId(R.styleable.Toolbar_toolbarBackground, android.R.color.white)
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
                getDimensionPixelOffset(
                    R.styleable.Toolbar_upperLeftTextMarginStart,
                    leftTextDefaultMarginStart
                )
            val lowerLeftTextMarginStart =
                getDimensionPixelOffset(
                    R.styleable.Toolbar_lowerLeftTextMarginStart,
                    leftTextDefaultMarginStart
                )

            val upperRightTextMarginEnd =
                getDimensionPixelOffset(
                    R.styleable.Toolbar_upperRightTextMarginEnd,
                    rightTextDefaultMarginEnd
                )
            val lowerRightTextMarginEnd =
                getDimensionPixelOffset(
                    R.styleable.Toolbar_lowerRightTextMarginEnd,
                    rightTextDefaultMarginEnd
                )
            val rightImageDrawableMarginEnd =
                getDimensionPixelOffset(R.styleable.Toolbar_rightImageDrawableMarginEnd, 0)
            val leftImageDrawableMarginStart =
                getDimensionPixelOffset(R.styleable.Toolbar_leftImageDrawableMarginStart, 0)
            val hideLeftImage = getBoolean(R.styleable.Toolbar_hideLeftImage, false)
            val leftImageContentDescription =
                getString(R.styleable.Toolbar_leftImageContentDescription) ?: ""
            val rightImageContentDescription =
                getString(R.styleable.Toolbar_rightImageContentDescription) ?: ""

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
                hideLeftImage = hideLeftImage,
                leftImageContentDescription = leftImageContentDescription,
                rightImageContentDescription = rightImageContentDescription
            )
        }
    }

    private fun applyViewState() {
        with(viewState) {
            binding.imageBackground.setDrawableResource(toolbarBackground)

            binding.imageLeft.setDrawableResource(leftImageDrawableResId)
            binding.imageLeft.setStartMargin(leftImageDrawableMarginStartInPixel)
            binding.imageLeft.visibility = if (hideLeftImage) View.GONE else View.VISIBLE
            binding.imageLeft.contentDescription = leftImageContentDescription

            binding.imageMiddle.setDrawableResource(middleImageDrawableResId)

            binding.imageRight.setDrawableResource(rightImageDrawableResId)
            binding.imageRight.setEndMargin(rightImageDrawableMarginEndInPixel)
            binding.imageRight.contentDescription = rightImageContentDescription

            binding.textLeftUp.text = upperLeftTextValue
            binding.textLeftUp.visibility = upperLeftTextVisibility
            binding.textLeftUp.setStyle(upperLeftTextAppearance)
            binding.textLeftUp.setStartMargin(upperLeftTextMarginStartInPixel)

            binding.textLeftDown.text = lowerLeftTextValue
            binding.textLeftDown.visibility = lowerLeftTextVisibility
            binding.textLeftDown.setStyle(lowerLeftTextAppearance)
            binding.textLeftDown.setStartMargin(lowerLeftTextMarginStartInPixel)

            binding.textMiddle.text = middleTextValue
            binding.textMiddle.visibility = middleTextVisibility
            binding.textMiddle.setStyle(middleTextAppearance)

            binding.textRightUp.text = upperRightTextValue
            binding.textRightUp.visibility = upperRightTextVisibility
            binding.textRightUp.setStyle(upperRightTextAppearance)
            binding.textRightUp.setEndMargin(upperRightTextMarginEndInPixel)
            binding.textRightUp.isEnabled = isUpperRightTextEnabled

            binding.textRightDown.text = lowerRightTextValue
            binding.textRightDown.visibility = lowerRightTextVisibility
            binding.textRightDown.setStyle(lowerRightTextAppearance)
            binding.textRightDown.setEndMargin(lowerRightTextMarginEndInPixel)
        }
    }
}
