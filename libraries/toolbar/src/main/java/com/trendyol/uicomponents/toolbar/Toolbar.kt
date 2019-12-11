package com.trendyol.uicomponents.toolbar

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout
import com.trendyol.uicomponents.toolbar.databinding.ViewToolbarBinding

class Toolbar @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null
) : AppBarLayout(context, attrs) {

    var leftImageClickListener: (() -> Unit)? = null
    var leftTextClickListener: (() -> Unit)? = null
    var rightImageClickListener: (() -> Unit)? = null
    var rightTextClickListener: (() -> Unit)? = null

    private val binding: ViewToolbarBinding = inflate(R.layout.view_toolbar)

    init {

    }
}
