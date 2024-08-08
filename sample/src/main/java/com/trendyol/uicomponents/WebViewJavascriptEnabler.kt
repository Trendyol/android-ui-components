package com.trendyol.uicomponents

import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.trendyol.uicomponents.dialogs.configurator.WebViewConfigurator

class WebViewJavascriptEnabler : WebViewConfigurator, Fragment() {

    override fun configureWebView(webView: WebView) {
        webView.settings.javaScriptEnabled = true
    }
}