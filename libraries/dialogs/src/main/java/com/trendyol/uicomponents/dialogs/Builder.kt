package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import android.webkit.WebView
import androidx.annotation.DrawableRes

open class Builder internal constructor() {

    var title: String = ""
    var showCloseButton: Boolean = false
    var closeButtonListener: ((DialogFragment) -> Unit)? = null
    var animateCornerRadiusWhenExpand: Boolean = false
}

open class InfoDialogBuilder internal constructor() : Builder() {

    var content: CharSequence = SpannableString("")

    @DrawableRes
    var contentImage: Int? = null
    var showContentAsHtml: Boolean = false
    var titleBackgroundColor: Int? = null
    var titleTextColor: Int? = null
    var titleTextPosition: TextPosition? = null
    var contentTextPosition: TextPosition? = null
    var webViewContent: WebViewContent? = null
    var webViewBuilder: (WebView.() -> Unit)? = null

    internal fun buildInfoDialog(block: InfoDialogBuilder.() -> Unit): DialogFragment {
        return InfoDialogBuilder().apply(block).let {
            DialogFragment().apply {
                arguments = DialogFragmentArguments(
                    title = it.title,
                    showCloseButton = it.showCloseButton,
                    animateCornerRadiusWhenExpand = it.animateCornerRadiusWhenExpand,
                    content = SpannableString(it.content),
                    contentImage = it.contentImage,
                    showContentAsHtml = it.showContentAsHtml,
                    titleBackgroundColor = it.titleBackgroundColor,
                    titleTextColor = it.titleTextColor,
                    titleTextPosition = it.titleTextPosition,
                    contentTextPosition = it.contentTextPosition,
                    webViewContent = it.webViewContent,
                    webViewBuilder = it.webViewBuilder,
                ).toBundle()
                this.closeButtonListener = it.closeButtonListener ?: { }
            }
        }
    }
}

open class AgreementDialogBuilder internal constructor() : InfoDialogBuilder() {

    var rightButtonText: String = ""
    var leftButtonText: String = ""
    var rightButtonClickListener: ((DialogFragment) -> Unit)? = null
    var leftButtonClickListener: ((DialogFragment) -> Unit)? = null

    internal fun buildAgreementDialog(block: AgreementDialogBuilder.() -> Unit): DialogFragment =
        AgreementDialogBuilder().apply(block).let {
            DialogFragment().apply {
                arguments = DialogFragmentArguments(
                    title = it.title,
                    showCloseButton = it.showCloseButton,
                    content = SpannableString(it.content),
                    showContentAsHtml = it.showContentAsHtml,
                    contentImage = it.contentImage,
                    rightButtonText = it.rightButtonText,
                    leftButtonText = it.leftButtonText,
                    webViewContent = it.webViewContent
                ).toBundle()
                closeButtonListener = it.closeButtonListener
                rightButtonClickListener = it.rightButtonClickListener
                leftButtonClickListener = it.leftButtonClickListener
            }
        }
}

class SelectionDialogBuilder internal constructor() : InfoDialogBuilder() {

    var items: List<Pair<Boolean, CharSequence>> = emptyList()
    var showItemsAsHtml: Boolean = false
    var onItemSelectedListener: ((DialogFragment, Int) -> Unit)? = null
    var onItemReselectedListener: ((DialogFragment, Int) -> Unit)? = null
    var enableSearch: Boolean = false
    var showClearSearchButton: Boolean = false
    var searchHint: String = ""
    var selectedItemDrawable: Int? = null
    var selectedTextColor: Int? = null
    var showRadioButton: Boolean = false

    internal fun buildSelectionDialog(block: SelectionDialogBuilder.() -> Unit): DialogFragment =
        SelectionDialogBuilder().apply(block).let {
            DialogFragment().apply {
                arguments = DialogFragmentArguments(
                    title = it.title,
                    showCloseButton = it.showCloseButton,
                    content = SpannableString(it.content),
                    showContentAsHtml = it.showContentAsHtml,
                    contentImage = it.contentImage,
                    items = it.items,
                    showItemsAsHtml = it.showItemsAsHtml,
                    enableSearch = it.enableSearch,
                    showClearSearchButton = it.showClearSearchButton,
                    searchHint = it.searchHint,
                    selectedItemDrawable = it.selectedItemDrawable,
                    selectedTextColor = it.selectedTextColor,
                    showRadioButton = it.showRadioButton,
                    webViewContent = it.webViewContent
                ).toBundle()
                closeButtonListener = it.closeButtonListener
                onItemSelectedListener = it.onItemSelectedListener
                onItemReselectedListener = it.onItemReselectedListener
            }

        }
}
