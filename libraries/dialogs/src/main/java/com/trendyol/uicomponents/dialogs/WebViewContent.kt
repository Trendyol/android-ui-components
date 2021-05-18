package com.trendyol.uicomponents.dialogs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class WebViewContent : Parcelable {

    @Parcelize
    data class UrlContent(val url: String) : WebViewContent()

    @Parcelize
    data class DataContent(val data: String) : WebViewContent()
}