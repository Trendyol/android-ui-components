package com.trendyol.timelineview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

internal fun <T : ViewDataBinding> ViewGroup?.inflate(
    @LayoutRes layoutId: Int,
    attachToParent: Boolean = true
): T = DataBindingUtil.inflate(
    LayoutInflater.from(this!!.context),
    layoutId,
    this,
    attachToParent
)