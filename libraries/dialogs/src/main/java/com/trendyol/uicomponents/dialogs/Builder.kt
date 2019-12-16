package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import androidx.annotation.DrawableRes

open class Builder internal constructor() {

    var title: String = ""
    var showCloseButton: Boolean = false
    var closeButtonListener: ((DialogFragment) -> Unit)? = null
}

open class InfoDialogBuilder internal constructor() : Builder() {

    var content: CharSequence = SpannableString("")
    @DrawableRes
    var contentImage: Int = 0
    var showContentAsHtml: Boolean = false

    internal fun buildInfoDialog(block: InfoDialogBuilder.() -> Unit): DialogFragment {
        return InfoDialogBuilder().apply(block).let {
            DialogFragment().apply {
                arguments = DialogFragmentArguments(
                    title = it.title,
                    showCloseButton = it.showCloseButton,
                    content = SpannableString(it.content),
                    contentImage = it.contentImage,
                    showContentAsHtml = it.showContentAsHtml
                ).toBundle()
                this.closeButtonListener = it.closeButtonListener ?: { }
            }
        }
    }
}

class ClickEvent() {

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
                    leftButtonText = it.leftButtonText
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
    var enableSearch: Boolean = false
    var showClearSearchButton: Boolean = false
    var searchHint: String = ""
    var selectedItemDrawable: Int? = null
    var selectedTextColor: Int? = null

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
                    selectedTextColor = it.selectedTextColor
                ).toBundle()
                closeButtonListener = it.closeButtonListener
                onItemSelectedListener = it.onItemSelectedListener
            }

        }
}
