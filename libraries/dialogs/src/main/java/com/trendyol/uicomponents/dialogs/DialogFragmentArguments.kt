package com.trendyol.uicomponents.dialogs

import android.os.Bundle
import android.os.Parcelable
import android.webkit.WebView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize

@Parcelize
class DialogFragmentArguments(
    val title: String? = null,
    val showCloseButton: Boolean? = null,
    val animateCornerRadiusWhenExpand: Boolean = true,
    val content: CharSequence? = null,
    val showContentAsHtml: Boolean = false,
    @DrawableRes val contentImage: Int? = null,
    val leftButtonText: String? = null,
    val rightButtonText: String? = null,
    val items: List<Pair<Boolean, CharSequence>>? = null,
    val showItemsAsHtml: Boolean = false,
    val enableSearch: Boolean = false,
    val showClearSearchButton: Boolean = false,
    val searchHint: String = "",
    @DrawableRes val selectedItemDrawable: Int? = null,
    @ColorRes val selectedTextColor: Int? = null,
    val showRadioButton: Boolean = false,
    @ColorRes val titleBackgroundColor: Int? = null,
    @ColorRes val titleTextColor: Int? = null,
    val titleTextPosition: TextPosition? = null,
    val contentTextPosition: TextPosition? = null,
    val webViewContent: WebViewContent? = null,
    val webViewBuilder: (WebView.() -> Unit)? = null
) : Parcelable {

    fun toBundle() = bundleOf("ARGUMENTS" to this)

    companion object {
        fun fromBundle(bundle: Bundle) = bundle
            .getParcelable<DialogFragmentArguments>("ARGUMENTS")
    }
}