package com.trendyol.uicomponents.toolbar

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import com.trendyol.uicomponents.toolbar.databinding.ViewToolbarBinding

class Toolbar @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null
) : AppBarLayout(context, attrs) {

    var leftImageClickListener: (() -> Unit)? = null
    var middleImageClickListener: (() -> Unit)? = null
    var rightImageClickListener: (() -> Unit)? = null
    var upperLeftTextClickListener: (() -> Unit)? = null
    var lowerLeftTextClickListener: (() -> Unit)? = null
    var middleTextClickListener: (() -> Unit)? = null
    var upperRightTextClickListener: (() -> Unit)? = null
    var lowerRightTextClickListener: (() -> Unit)? = null

    private val binding: ViewToolbarBinding = inflate(R.layout.view_toolbar)

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

    fun setViewState(viewState: ToolbarViewState?) {
        if (viewState != null) {
            binding.viewState = viewState
            binding.executePendingBindings()
        }
    }
}
