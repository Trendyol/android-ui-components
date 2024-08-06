package com.trendyol.uicomponents.dialogs.configurator

import android.webkit.WebView

interface WebViewDownloadConfigurator {

    fun configureDownloadListener(webView: WebView)

    companion object {
        const val TAG: String = "WebViewDownloadConfigurator"
    }
}