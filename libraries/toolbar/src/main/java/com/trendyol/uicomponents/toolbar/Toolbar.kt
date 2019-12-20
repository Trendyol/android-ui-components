package com.trendyol.uicomponents.toolbar

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.trendyol.uicomponents.toolbar.databinding.ViewToolbarBinding

class Toolbar : ConstraintLayout {

    var viewState: ToolbarViewState? = null
        set(value) {
            field = value
            if (field != null) {
                binding.viewState = viewState
                binding.executePendingBindings()
            }
        }

    var leftImageClickListener: (() -> Unit)? = null
    var middleImageClickListener: (() -> Unit)? = null
    var rightImageClickListener: (() -> Unit)? = null
    var upperLeftTextClickListener: (() -> Unit)? = null
    var lowerLeftTextClickListener: (() -> Unit)? = null
    var middleTextClickListener: (() -> Unit)? = null
    var upperRightTextClickListener: (() -> Unit)? = null
    var lowerRightTextClickListener: (() -> Unit)? = null

    private val binding: ViewToolbarBinding = inflate(R.layout.view_toolbar)

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        readFromAttributes(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        readFromAttributes(attrs)
    }

    init {
        binding.imageLeft.setOnClickListener { leftImageClickListener?.invoke() }
        binding.imageMiddle.setOnClickListener { middleImageClickListener?.invoke() }
        binding.imageRight.setOnClickListener { rightImageClickListener?.invoke() }
        binding.textLeftUp.setOnClickListener { upperLeftTextClickListener?.invoke() }
        binding.textLeftDown.setOnClickListener { lowerLeftTextClickListener?.invoke() }
        binding.textMiddle.setOnClickListener { middleTextClickListener?.invoke() }
        binding.textRightUp.setOnClickListener { upperRightTextClickListener?.invoke() }
        binding.textRightDown.setOnClickListener { lowerRightTextClickListener?.invoke() }
    }

    private fun readFromAttributes(attrs: AttributeSet?) {
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.Toolbar,
            0,
            0
        )?.apply {
            val leftImageDrawableResId =
                getResourceId(R.styleable.Toolbar_leftImageDrawable, R.drawable.ic_arrow_back)
            val middleImageDrawableResId = getResourceId(R.styleable.Toolbar_middleImageDrawable, 0)
            val rightImageDrawableResId = getResourceId(R.styleable.Toolbar_rightImageDrawable, 0)

            val upperLeftText = getString(R.styleable.Toolbar_upperLeftText)
            val lowerLeftText = getString(R.styleable.Toolbar_lowerLeftText)
            val middleText = getString(R.styleable.Toolbar_middleText)
            val upperRightText = getString(R.styleable.Toolbar_upperRightText)
            val lowerRightText = getString(R.styleable.Toolbar_lowerRightText)

            val toolbarBackground =
                getResourceId(R.styleable.Toolbar_toolbarBackground, android.R.color.white)

            viewState = ToolbarViewState(
                upperLeftText = upperLeftText,
                lowerLeftText = lowerLeftText,
                middleText = middleText,
                upperRightText = upperRightText,
                lowerRightText = lowerRightText,
                leftImageDrawableResId = leftImageDrawableResId,
                middleImageDrawableResId = middleImageDrawableResId,
                rightImageDrawableResId = rightImageDrawableResId,
                toolbarBackground = toolbarBackground
            )
        }
    }
}
