package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import androidx.annotation.DrawableRes

abstract class Builder internal constructor() {

    var title: String = ""
    var showCloseButton: Boolean = false
    var closeButtonListener: ((Dialog) -> Unit)? = null
}

open class InfoDialogBuilder internal constructor() : Builder() {

    var content: SpannableString = SpannableString("")
    @DrawableRes
    var contentImage: Int = 0
    var showContentAsHtml: Boolean = false

    internal fun buildInfoDialog(block: InfoDialogBuilder.() -> Unit): Dialog {
        return InfoDialogBuilder().apply(block).let {
            Dialog(
                title = it.title,
                showCloseButton = it.showCloseButton,
                closeButtonListener = it.closeButtonListener ?: { },
                content = it.content,
                contentImage = it.contentImage,
                showContentAsHtml = it.showContentAsHtml
            )
        }
    }
}

class AgreementDialogBuilder internal constructor() : InfoDialogBuilder() {

    var rightButtonText: String = ""
    var leftButtonText: String = ""
    var rightButtonClickListener: ((Dialog) -> Unit)? = null
    var leftButtonClickListener: ((Dialog) -> Unit)? = null

    internal fun buildAgreementDialog(block: AgreementDialogBuilder.() -> Unit): Dialog =
        AgreementDialogBuilder().apply(block).let {
            Dialog(
                title = it.title,
                showCloseButton = it.showCloseButton,
                closeButtonListener = it.closeButtonListener,
                content = it.content,
                contentImage = it.contentImage,
                rightButtonText = it.rightButtonText,
                leftButtonText = it.leftButtonText,
                rightButtonClickListener = it.rightButtonClickListener,
                leftButtonClickListener = it.leftButtonClickListener
            )
        }
}
