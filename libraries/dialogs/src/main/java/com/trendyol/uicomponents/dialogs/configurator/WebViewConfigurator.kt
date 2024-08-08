package com.trendyol.uicomponents.dialogs.configurator

import android.webkit.WebView
import com.trendyol.uicomponents.dialogs.DialogFragment

interface WebViewConfigurator {

    fun configureWebView(
        dialogFragment: DialogFragment,
        webView: WebView
    )

    companion object {
        const val TAG: String = "WebViewConfigurator"
    }
}