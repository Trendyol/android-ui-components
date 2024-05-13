package com.trendyol.uicomponents.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.trendyol.uicomponents.toolbar.databinding.ViewToolbarBinding

class Toolbar : ConstraintLayout {

    private var binding: ViewToolbarBinding =
        ViewToolbarBinding.inflate(LayoutInflater.from(context), this)

    var viewState: ToolbarViewState = ToolbarViewState()
        set(value) {
            field = value
            applyViewState()
        }

    var startImageClickListener: (() -> Unit)? = null

    @Deprecated("Replaced with startImageClickListener", replaceWith = ReplaceWith("startImageClickListener"))
    var leftImageClickListener: (() -> Unit)? = null

    var middleImageClickListener: (() -> Unit)? = null

    var endImageClickListener: (() -> Unit)? = null

    @Deprecated("Replaced with endImageClickListener", replaceWith = ReplaceWith("endImageClickListener"))
    var rightImageClickListener: (() -> Unit)? = null

    var upperStartTextClickListener: (() -> Unit)? = null

    @Deprecated("Replaced with upperStartTextClickListener", replaceWith = ReplaceWith("upperStartTextClickListener"))
    var upperLeftTextClickListener: (() -> Unit)? = null

    var lowerStartTextClickListener: (() -> Unit)? = null

    @Deprecated("Replaced with lowerStartTextClickListener", replaceWith = ReplaceWith("lowerStartTextClickListener"))
    var lowerLeftTextClickListener: (() -> Unit)? = null

    var middleTextClickListener: (() -> Unit)? = null

    var upperEndTextClickListener: (() -> Unit)? = null

    @Deprecated("Replaced with upperEndTextClickListener", replaceWith = ReplaceWith("upperEndTextClickListener"))
    var upperRightTextClickListener: (() -> Unit)? = null

    var lowerEndTextClickListener: (() -> Unit)? = null

    @Deprecated("Replaced with lowerEndTextClickListener", replaceWith = ReplaceWith("lowerEndTextClickListener"))
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
        binding.imageLeft.setOnClickListener {
            startImageClickListener?.invoke()
            leftImageClickListener?.invoke()
        }
        binding.imageMiddle.setOnClickListener { middleImageClickListener?.invoke() }
        binding.imageRight.setDebouncedOnClickListener {
            endImageClickListener?.invoke()
            rightImageClickListener?.invoke()
        }
        binding.textLeftUp.setOnClickListener {
            upperStartTextClickListener?.invoke()
            upperLeftTextClickListener?.invoke()
        }
        binding.textLeftDown.setOnClickListener {
            lowerStartTextClickListener?.invoke()
            lowerLeftTextClickListener?.invoke()
        }
        binding.textMiddle.setOnClickListener { middleTextClickListener?.invoke() }
        binding.textRightUp.setOnClickListener {
            upperEndTextClickListener?.invoke()
            upperRightTextClickListener?.invoke()
        }
        binding.textRightDown.setOnClickListener {
            lowerEndTextClickListener?.invoke()
            lowerRightTextClickListener?.invoke()
        }
    }

    private fun readFromAttributes(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.Toolbar,
            defStyleAttr,
            0
        )?.apply {
            val toolbarBackground = getResourceId(R.styleable.Toolbar_toolbarBackground, android.R.color.white)
            val startImageDrawableResId = getResourceIdOrDefault(
                R.styleable.Toolbar_startImageDrawable,
                R.styleable.Toolbar_leftImageDrawable,
                R.drawable.trendyol_uicomponents_toolbar_arrow_back
            )
            val middleImageDrawableResId = getResourceId(R.styleable.Toolbar_middleImageDrawable, 0)
            val endImageDrawableResId = getResourceIdOrDefault(
                R.styleable.Toolbar_endImageDrawable,
                R.styleable.Toolbar_rightImageDrawable,
                0
            )

            val upperStartText = getString(R.styleable.Toolbar_upperStartText)
                ?: getString(R.styleable.Toolbar_upperLeftText)
            val lowerStartText = getString(R.styleable.Toolbar_lowerStartText)
                ?: getString(R.styleable.Toolbar_lowerLeftText)
            val middleText = getString(R.styleable.Toolbar_middleText)
            val upperEndText = getString(R.styleable.Toolbar_upperEndText)
                ?: getString(R.styleable.Toolbar_upperRightText)
            val lowerEndText = getString(R.styleable.Toolbar_lowerEndText)
                ?: getString(R.styleable.Toolbar_lowerRightText)

            val startTextDefaultMarginStart =
                resources.getDimensionPixelOffset(R.dimen.trendyol_uicomponents_toolbar_margin_start_side_text)
            val endTextDefaultMarginEnd =
                resources.getDimensionPixelOffset(R.dimen.trendyol_uicomponents_toolbar_margin_outer)

            val upperStartTextMarginStart = getDimensionPixelOffsetOrDefault(
                R.styleable.Toolbar_upperStartTextMarginStart,
                R.styleable.Toolbar_upperLeftTextMarginStart,
                startTextDefaultMarginStart
            )
            val lowerStartTextMarginStart = getDimensionPixelOffsetOrDefault(
                R.styleable.Toolbar_lowerStartTextMarginStart,
                R.styleable.Toolbar_lowerLeftTextMarginStart,
                startTextDefaultMarginStart
            )
            val upperEndTextMarginEnd = getDimensionPixelOffsetOrDefault(
                R.styleable.Toolbar_upperEndTextMarginEnd,
                R.styleable.Toolbar_upperRightTextMarginEnd,
                endTextDefaultMarginEnd
            )
            val lowerEndTextMarginEnd = getDimensionPixelOffsetOrDefault(
                R.styleable.Toolbar_lowerEndTextMarginEnd,
                R.styleable.Toolbar_lowerRightTextMarginEnd,
                endTextDefaultMarginEnd
            )
            val endImageDrawableMarginEnd = getDimensionPixelOffsetOrDefault(
                R.styleable.Toolbar_endImageDrawableMarginEnd,
                R.styleable.Toolbar_rightImageDrawableMarginEnd,
                0
            )
            val endImageDrawableVerticalMargin = getDimensionPixelOffsetOrDefault(
                R.styleable.Toolbar_endImageDrawableVerticalMargin,
                R.styleable.Toolbar_rightImageDrawableVerticalMargin,
                0
            )
            val enableDotPoint = getBoolean(R.styleable.Toolbar_enableDotPoint, false)
            val startImageDrawableMarginStart = getDimensionPixelOffsetOrDefault(
                R.styleable.Toolbar_startImageDrawableMarginStart,
                R.styleable.Toolbar_leftImageDrawableMarginStart,
                0
            )
            val hideStartImage = (getBoolean(R.styleable.Toolbar_hideStartImage, false)
                || getBoolean(R.styleable.Toolbar_hideLeftImage, false))
            val startImageContentDescription = getStringOrEmpty(
                R.styleable.Toolbar_startImageContentDescription,
                R.styleable.Toolbar_leftImageContentDescription
            )
            val endImageContentDescription = getStringOrEmpty(
                R.styleable.Toolbar_endImageContentDescription,
                R.styleable.Toolbar_rightImageContentDescription
            )

            viewState = ToolbarViewState(
                upperStartText = upperStartText,
                lowerStartText = lowerStartText,
                middleText = middleText,
                upperEndText = upperEndText,
                lowerEndText = lowerEndText,
                startImageDrawableResId = startImageDrawableResId,
                middleImageDrawableResId = middleImageDrawableResId,
                endImageDrawableResId = endImageDrawableResId,
                toolbarBackground = toolbarBackground,
                upperStartTextMarginStartInPixel = upperStartTextMarginStart,
                lowerStartTextMarginStartInPixel = lowerStartTextMarginStart,
                upperEndTextMarginEndInPixel = upperEndTextMarginEnd,
                lowerEndTextMarginEndInPixel = lowerEndTextMarginEnd,
                endImageDrawableMarginEndInPixel = endImageDrawableMarginEnd,
                startImageDrawableMarginStartInPixel = startImageDrawableMarginStart,
                endImageDrawableVerticalMarginInPixel = endImageDrawableVerticalMargin,
                enableDotPoint = enableDotPoint,
                hideStartImage = hideStartImage,
                startImageContentDescription = startImageContentDescription,
                endImageContentDescription = endImageContentDescription
            )
        }
    }

    private fun applyViewState() {
        with(viewState) {
            binding.imageBackground.setDrawableResource(toolbarBackground)

            binding.imageLeft.setDrawableResource(startImageDrawableResId)
            binding.imageLeft.setStartMargin(startImageDrawableMarginStartInPixel)
            binding.imageLeft.visibility = if (hideStartImage) View.GONE else View.VISIBLE
            binding.imageLeft.contentDescription = startImageContentDescription

            binding.imageMiddle.setDrawableResource(middleImageDrawableResId)

            binding.imageRight.setDrawableResource(endImageDrawableResId)
            binding.imageRight.setEndMargin(endImageDrawableMarginEndInPixel)
            binding.imageRight.contentDescription = endImageContentDescription
            binding.imageRight.setVerticalPadding(endImageDrawableVerticalMarginInPixel)

            binding.imageViewDot.isVisible = enableDotPoint

            binding.textLeftUp.text = upperStartTextValue
            binding.textLeftUp.visibility = upperStartTextVisibility
            binding.textLeftUp.setStyle(upperStartTextAppearance)
            binding.textLeftUp.setStartMargin(upperStartTextMarginStartInPixel)

            binding.textLeftDown.text = lowerStartTextValue
            binding.textLeftDown.visibility = lowerStartTextVisibility
            binding.textLeftDown.setStyle(lowerStartTextAppearance)
            binding.textLeftDown.setStartMargin(lowerStartTextMarginStartInPixel)

            binding.textMiddle.text = middleTextValue
            binding.textMiddle.visibility = middleTextVisibility
            binding.textMiddle.setStyle(middleTextAppearance)

            binding.textRightUp.text = upperEndTextValue
            binding.textRightUp.visibility = upperEndTextVisibility
            binding.textRightUp.setStyle(upperEndTextAppearance)
            binding.textRightUp.setEndMargin(upperEndTextMarginEndInPixel)
            binding.textRightUp.isEnabled = isUpperEndTextEnabled

            binding.textRightDown.text = lowerEndTextValue
            binding.textRightDown.visibility = lowerEndTextVisibility
            binding.textRightDown.setStyle(lowerRightTextAppearance)
            binding.textRightDown.setEndMargin(lowerEndTextMarginEndInPixel)
        }
    }
}
