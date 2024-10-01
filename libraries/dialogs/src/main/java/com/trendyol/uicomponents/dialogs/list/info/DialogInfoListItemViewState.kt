package com.trendyol.uicomponents.dialogs.list.info

import android.content.Context
import androidx.core.content.res.ResourcesCompat

data class DialogInfoListItemViewState(
    val key: CharSequence,
    val value: CharSequence,
){

    fun getFontFamily(context: Context, fontFamily:Int?) = fontFamily?.let { ResourcesCompat.getFont(context, it) }
}

