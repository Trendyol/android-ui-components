package com.trendyol.uicomponents.dialogs

import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("drawableResource")
internal fun AppCompatImageView.setDrawableResource(@DrawableRes drawableResId: Int?) {
    if (drawableResId != null) {
        visibility = View.VISIBLE
        setImageResource(drawableResId)
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter("isVisible")
internal fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


@BindingAdapter("textColorResource")
internal fun TextView.setTextColorResource(@ColorRes colorRes: Int) {
    setTextColor(ContextCompat.getColor(context, colorRes))
}


@BindingAdapter("loadWebViewContent")
internal fun WebView.loadWebViewContent(webViewContent: WebViewContent?) {
    webViewContent?.also { content ->
        when (content) {
            is WebViewContent.UrlContent -> loadUrl(content.url)
            is WebViewContent.DataContent -> loadData(content.data, "text/html", "UTF-8")
        }
    }
}