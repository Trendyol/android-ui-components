package com.trendyol.uicomponents.dialogs

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trendyol.dialog.R

internal fun <T : ViewDataBinding> ViewGroup?.inflate(
    @LayoutRes layoutId: Int,
    attachToParent: Boolean = true
): T {
    if (this?.isInEditMode == true) {
        View.inflate(context, layoutId, parent as? ViewGroup?)
    }
    return DataBindingUtil.inflate(
        LayoutInflater.from(this!!.context),
        layoutId,
        this,
        attachToParent
    )
}

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
    observe(owner, Observer {
        if (it != null) {
            onNotNull.invoke(it)
        }
    })

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
    val outlineProvider = BottomSheetOutlineProvider(radius = roundedCardRadius)

    val outlineUpdater = ValueAnimator.AnimatorUpdateListener {
        outlineProvider.radius = it.animatedValue as Float
        binding.cardView.outlineProvider = outlineProvider
    }
    expandAnimator.addUpdateListener(outlineUpdater)
    collapseAnimator.addUpdateListener(outlineUpdater)

    getBottomSheetBehavior<View>()
        ?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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
        }
    })
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
