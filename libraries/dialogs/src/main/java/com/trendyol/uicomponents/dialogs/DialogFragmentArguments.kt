package com.trendyol.uicomponents.dialogs

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize

@Parcelize
class DialogFragmentArguments(
    val title: CharSequence? = null,
    val showCloseButton: Boolean? = null,
    @ColorInt val closeButtonColor: Int? = null,
    @DrawableRes val closeButtonDrawable: Int? = null,
    val animateCornerRadiusWhenExpand: Boolean = true,
    val cornerRadius: Float? = null,
    val horizontalPadding: Float? = null,
    val verticalPadding: Float? = null,
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
    val infoListItems: List<Pair<CharSequence, CharSequence>>? = null,
    val itemDividers: List<ItemDivider> = emptyList(),
    val isFullHeightWebView: Boolean = false,
    @FontRes val titleFontFamily: Int? = null,
    @FontRes val contentFontFamily: Int? = null
) : Parcelable {

    fun toBundle() = bundleOf("ARGUMENTS" to this)

    companion object {
        fun fromBundle(bundle: Bundle) = bundle
            .getParcelable<DialogFragmentArguments>("ARGUMENTS")
    }
}