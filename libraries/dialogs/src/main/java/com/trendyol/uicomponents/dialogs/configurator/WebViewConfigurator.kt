package com.trendyol.uicomponents.dialogs.configurator

import android.webkit.WebView

interface WebViewConfigurator {

    fun configureWebView(webView: WebView)

    companion object {
        const val TAG: String = "WebViewConfigurator"
    }
}