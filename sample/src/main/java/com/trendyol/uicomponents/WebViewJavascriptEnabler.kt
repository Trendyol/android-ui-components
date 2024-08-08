package com.trendyol.uicomponents

import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.trendyol.uicomponents.dialogs.DialogFragment
import com.trendyol.uicomponents.dialogs.configurator.WebViewConfigurator

class WebViewJavascriptEnabler : WebViewConfigurator, Fragment() {

    override fun configureWebView(dialogFragment: DialogFragment, webView: WebView) {
        webView.settings.javaScriptEnabled = true
    }
}