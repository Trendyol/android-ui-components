package com.trendyol.uicomponents.dialogs.configurator

import android.webkit.WebView
import com.trendyol.uicomponents.dialogs.DialogFragment

interface WebViewDownloadConfigurator {

    fun configureDownloadListener(
        fragment: DialogFragment,
        webView: WebView
    )

    companion object {
        const val TAG: String = "WebViewDownloadConfigurator"
    }
}