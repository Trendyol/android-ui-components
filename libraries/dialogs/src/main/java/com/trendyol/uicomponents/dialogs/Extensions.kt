package com.trendyol.uicomponents.dialogs

import android.animation.ObjectAnimator
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
    val expandAnimator =
        ObjectAnimator.ofFloat(
            binding.cardView,
            "radius",
            requireContext().pixel(R.dimen.dialogs_corner_radius),
            0F
        ).apply {
            duration = 250
        }
    val collapseAnimator =
        ObjectAnimator.ofFloat(
            binding.cardView,
            "radius",
            0F,
            requireContext().pixel(R.dimen.dialogs_corner_radius)
        ).apply {
            duration = 250
        }

    getBottomSheetBehavior<View>()?.addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {

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
