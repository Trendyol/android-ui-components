package com.trendyol.uicomponents

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import android.webkit.DownloadListener
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.trendyol.uicomponents.dialogs.DialogFragment
import com.trendyol.uicomponents.dialogs.configurator.WebViewDownloadConfigurator

class WebViewDownloadListenerConfigurator : WebViewDownloadConfigurator, Fragment() {
    private val downloadManager: DownloadManager? by lazy(LazyThreadSafetyMode.NONE) {
        ContextCompat.getSystemService(requireContext(), DownloadManager::class.java)
    }

    override fun configureDownloadListener(fragment: DialogFragment, webView: WebView) {
        webView.setDownloadListener(DownloadListener { url, _, contentDisposition, mimetype, _ ->
            val downloadRequest = DownloadManager.Request(Uri.parse(url))
                .setTitle(requireArguments().getString("title"))
                .setMimeType(mimetype)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    URLUtil.guessFileName(url, contentDisposition, mimetype)
                )
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            downloadManager?.enqueue(downloadRequest)
        })
    }

    companion object {
        fun newInstance(title: String): WebViewDownloadListenerConfigurator =
            WebViewDownloadListenerConfigurator().apply {
                this.arguments = bundleOf("title" to title)
            }
    }

}