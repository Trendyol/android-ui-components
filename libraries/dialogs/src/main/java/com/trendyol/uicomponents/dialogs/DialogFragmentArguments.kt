package com.trendyol.uicomponents.dialogs

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import kotlinx.android.parcel.Parcelize

@Parcelize
class DialogFragmentArguments(
    val title: String? = null,
    val showCloseButton: Boolean? = null,
    val content: CharSequence? = null,
    val showContentAsHtml: Boolean = false,
    @DrawableRes val contentImage: Int? = null,
    val leftButtonText: String? = null,
    val rightButtonText: String? = null,
    val items: List<Pair<Boolean, CharSequence>>? = null,
    val showItemsAsHtml: Boolean = false,
    val enableSearch: Boolean = false,
    val showClearSearchButton: Boolean = false,
    val searchHint: String = ""
) : Parcelable {

    fun toBundle() = bundleOf("ARGUMENTS" to this)

    companion object {
        fun fromBundle(bundle: Bundle) = bundle
            .getParcelable<DialogFragmentArguments>("ARGUMENTS")
    }
}