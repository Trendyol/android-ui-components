package com.trendyol.uicomponents.toolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

internal fun <T : ViewDataBinding> ViewGroup.inflateCustomView(
    @LayoutRes layoutId: Int,
    afterInflate: (T) -> Unit
) {
    if (isInEditMode) {
        LayoutInflater.from(context).inflate(layoutId, this, true)
    } else {
        afterInflate.invoke(
            DataBindingUtil.inflate(
                LayoutInflater.from(this!!.context),
                layoutId,
                this,
                true
            )
        )
    }
}
