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
            DialogFragment(
                title = it.title,
                showCloseButton = it.showCloseButton,
                closeButtonListener = it.closeButtonListener ?: { },
                content = SpannableString(it.content),
                contentImage = it.contentImage,
                showContentAsHtml = it.showContentAsHtml
            )
        }
    }
}

class AgreementDialogBuilder internal constructor() : InfoDialogBuilder() {

    var rightButtonText: String = ""
    var leftButtonText: String = ""
    var rightButtonClickListener: ((DialogFragment) -> Unit)? = null
    var leftButtonClickListener: ((DialogFragment) -> Unit)? = null

    internal fun buildAgreementDialog(block: AgreementDialogBuilder.() -> Unit): DialogFragment =
        AgreementDialogBuilder().apply(block).let {
            DialogFragment(
                title = it.title,
                showCloseButton = it.showCloseButton,
                closeButtonListener = it.closeButtonListener,
                content = SpannableString(it.content),
                showContentAsHtml = it.showContentAsHtml,
                contentImage = it.contentImage,
                rightButtonText = it.rightButtonText,
                leftButtonText = it.leftButtonText,
                rightButtonClickListener = it.rightButtonClickListener,
                leftButtonClickListener = it.leftButtonClickListener
            )
        }
}
