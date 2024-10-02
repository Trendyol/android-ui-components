package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes

open class Builder internal constructor() {

    var title: CharSequence = ""
    var titleBackgroundColor: Int? = null
    var titleTextColor: Int? = null
    var titleTextPosition: TextPosition? = null
    var showCloseButton: Boolean = false

    @ColorInt
    var closeButtonColor: Int? = null

    @DrawableRes
    var closeButtonDrawable: Int? = null
    var closeButtonListener: ((DialogFragment) -> Unit)? = null
    var animateCornerRadiusWhenExpand: Boolean = false
    var onDialogDismissListener: ((DialogFragment) -> Unit)? = null
    var cornerRadius: Float? = null
    var isFullHeightWebView: Boolean = false

    @FontRes
    var titleFontFamily: Int? = null

    @FontRes
    var contentFontFamily: Int? = null
}

open class InfoDialogBuilder internal constructor() : Builder() {

    var content: CharSequence = SpannableString("")

    @DrawableRes
    var contentImage: Int? = null
    var showContentAsHtml: Boolean = false
    var contentTextPosition: TextPosition? = null
    var webViewContent: WebViewContent? = null
    var horizontalPadding: Float? = null
    var verticalPadding: Float? = null

    internal fun buildInfoDialog(block: InfoDialogBuilder.() -> Unit): DialogFragment {
        return InfoDialogBuilder().apply(block).let {
            DialogFragment().apply {
                arguments = DialogFragmentArguments(
                    title = it.title,
                    showCloseButton = it.showCloseButton,
                    closeButtonColor = it.closeButtonColor,
                    closeButtonDrawable = it.closeButtonDrawable,
                    animateCornerRadiusWhenExpand = it.animateCornerRadiusWhenExpand,
                    cornerRadius = it.cornerRadius,
                    horizontalPadding = it.horizontalPadding,
                    verticalPadding = it.verticalPadding,
                    content = SpannableString(it.content),
                    contentImage = it.contentImage,
                    showContentAsHtml = it.showContentAsHtml,
                    titleBackgroundColor = it.titleBackgroundColor,
                    titleTextColor = it.titleTextColor,
                    titleTextPosition = it.titleTextPosition,
                    contentTextPosition = it.contentTextPosition,
                    webViewContent = it.webViewContent,
                    isFullHeightWebView = it.isFullHeightWebView,
                    titleFontFamily = it.titleFontFamily,
                    contentFontFamily = it.contentFontFamily
                ).toBundle()
                this.closeButtonListener = it.closeButtonListener ?: { }
                this.onDismissListener = it.onDialogDismissListener ?: {}
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
                    closeButtonColor = it.closeButtonColor,
                    closeButtonDrawable = it.closeButtonDrawable,
                    content = SpannableString(it.content),
                    cornerRadius = it.cornerRadius,
                    horizontalPadding = it.horizontalPadding,
                    verticalPadding = it.verticalPadding,
                    showContentAsHtml = it.showContentAsHtml,
                    contentImage = it.contentImage,
                    rightButtonText = it.rightButtonText,
                    leftButtonText = it.leftButtonText,
                    webViewContent = it.webViewContent,
                    titleFontFamily = it.titleFontFamily,
                    contentFontFamily = it.contentFontFamily
                ).toBundle()
                closeButtonListener = it.closeButtonListener
                rightButtonClickListener = it.rightButtonClickListener
                leftButtonClickListener = it.leftButtonClickListener
                this.onDismissListener = it.onDialogDismissListener ?: {}
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
                    closeButtonColor = it.closeButtonColor,
                    closeButtonDrawable = it.closeButtonDrawable,
                    content = SpannableString(it.content),
                    showContentAsHtml = it.showContentAsHtml,
                    contentImage = it.contentImage,
                    cornerRadius = it.cornerRadius,
                    horizontalPadding = it.horizontalPadding,
                    verticalPadding = it.verticalPadding,
                    animateCornerRadiusWhenExpand = it.animateCornerRadiusWhenExpand,
                    titleTextColor = it.titleTextColor,
                    items = it.items,
                    showItemsAsHtml = it.showItemsAsHtml,
                    enableSearch = it.enableSearch,
                    showClearSearchButton = it.showClearSearchButton,
                    searchHint = it.searchHint,
                    selectedItemDrawable = it.selectedItemDrawable,
                    selectedTextColor = it.selectedTextColor,
                    showRadioButton = it.showRadioButton,
                    webViewContent = it.webViewContent,
                    titleFontFamily = it.titleFontFamily,
                    contentFontFamily = it.contentFontFamily
                ).toBundle()
                closeButtonListener = it.closeButtonListener
                onItemSelectedListener = it.onItemSelectedListener
                onItemReselectedListener = it.onItemReselectedListener
                this.onDismissListener = it.onDialogDismissListener ?: {}
            }

        }
}

class InfoListDialogBuilder internal constructor() : InfoDialogBuilder() {

    var infoListItems: List<Pair<CharSequence, CharSequence>> = emptyList()
    var itemDividers: List<ItemDivider> = emptyList()

    internal fun buildInfoListDialog(block: InfoListDialogBuilder.() -> Unit): DialogFragment =
        InfoListDialogBuilder().apply(block).let {
            DialogFragment().apply {
                arguments = DialogFragmentArguments(
                    title = it.title,
                    showCloseButton = it.showCloseButton,
                    closeButtonColor = it.closeButtonColor,
                    closeButtonDrawable = it.closeButtonDrawable,
                    content = SpannableString(it.content),
                    animateCornerRadiusWhenExpand = it.animateCornerRadiusWhenExpand,
                    cornerRadius = it.cornerRadius,
                    horizontalPadding = it.horizontalPadding,
                    verticalPadding = it.verticalPadding,
                    showContentAsHtml = it.showContentAsHtml,
                    contentImage = it.contentImage,
                    webViewContent = it.webViewContent,
                    infoListItems = it.infoListItems,
                    itemDividers = it.itemDividers,
                    titleFontFamily = it.titleFontFamily,
                    contentFontFamily = it.contentFontFamily
                ).toBundle()
                closeButtonListener = it.closeButtonListener
                this.onDismissListener = it.onDialogDismissListener ?: {}
            }

        }
}
