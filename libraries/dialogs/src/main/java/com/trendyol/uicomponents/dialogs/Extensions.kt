package com.trendyol.uicomponents.dialogs

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import androidx.annotation.DimenRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trendyol.dialog.R

internal fun Context.pixel(@DimenRes dimenResId: Int): Float =
    resources.getDimensionPixelSize(dimenResId).toFloat()

internal fun <V : View> BottomSheetDialogFragment.getBottomSheetBehavior(dialog: Dialog? = getDialog()): BottomSheetBehavior<V>? {
    return dialog
        ?.findViewById<V>(com.google.android.material.R.id.design_bottom_sheet)
        ?.let { BottomSheetBehavior.from(it) }
}

internal fun BottomSheetDialogFragment.setBottomSheetState(state: Int) {
    getBottomSheetBehavior<View>()?.state = state
}

internal fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, onNotNull: (T) -> Unit) =
    observe(owner) {
        if (it != null) {
            onNotNull.invoke(it)
        }
    }

internal fun DialogFragment.animateCornerRadiusWithStateChanged() {
    val roundedCardRadius = requireContext().pixel(R.dimen.dialogs_corner_radius)
    val expandAnimator =
        ValueAnimator.ofFloat(roundedCardRadius, 0.0f)
            .apply {
                duration = 250
            }
    val collapseAnimator =
        ValueAnimator.ofFloat(0.0f, roundedCardRadius)
            .apply {
                duration = 250
            }

    binding.cardView.clipToOutline = true
    val outlineProvider = DialogFragment.BottomSheetOutlineProvider(radius = roundedCardRadius)

    val outlineUpdater = ValueAnimator.AnimatorUpdateListener {
        outlineProvider.radius = it.animatedValue as Float
        binding.cardView.outlineProvider = outlineProvider
    }
    expandAnimator.addUpdateListener(outlineUpdater)
    collapseAnimator.addUpdateListener(outlineUpdater)

    getBottomSheetBehavior<View>()?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    expandAnimator.start()
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    collapseAnimator.start()
                    binding.editTextSearch.hideKeyboard()
                }
                else -> {
                    binding.editTextSearch.hideKeyboard()
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            // do nothing
        }
    })
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

internal fun WebView.loadWebViewContent(webViewContent: WebViewContent?) {
    webViewContent?.also { content ->
        when (content) {
            is WebViewContent.UrlContent -> loadUrl(content.url)
            is WebViewContent.DataContent -> loadDataWithBaseURL("", content.data, "text/html", "UTF-8", null)
        }
    }
}
