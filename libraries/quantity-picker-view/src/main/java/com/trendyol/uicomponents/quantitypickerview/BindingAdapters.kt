package com.trendyol.uicomponents.quantitypickerview

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.trendyol.uicomponents.quantitypickerview.QuantityPickerTextAppearance

@BindingAdapter("qpv_textAppearance")
internal fun setTextAppearance(
    textView: AppCompatTextView,
    quantityPickerTextAppearance: QuantityPickerTextAppearance
) {
    with(textView) {
        val style = when (quantityPickerTextAppearance.textStyle) {
            0 -> Typeface.NORMAL
            1 -> Typeface.BOLD
            2 -> Typeface.ITALIC
            else -> throw IllegalArgumentException("no style attribute")
        }
        setTypeface(typeface, style)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, quantityPickerTextAppearance.textSize.toFloat())
        setTextColor(quantityPickerTextAppearance.textColor)
    }
}

@BindingAdapter("qpv_progressTint")
internal fun setProgressTint(progressBar: ProgressBar, @ColorInt tintColor: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        progressBar.indeterminateTintList = ColorStateList.valueOf(tintColor)
    } else {
        PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    }
}

@BindingAdapter("qpv_background")
internal fun setBackgroundDrawable(imageView: AppCompatImageView, drawable: Drawable?) {
    drawable?.run { imageView.background = this }
}

@BindingAdapter("qpv_src")
internal fun setDrawableSrc(imageView: AppCompatImageView, drawable: Drawable?) {
    imageView.setImageDrawable(drawable)
}

@BindingAdapter("qpv_horizontalPadding")
internal fun setHorizontalPadding(view: View, paddingValue: Int) {
    view.setPadding(paddingValue, view.paddingTop, paddingValue, view.paddingBottom)
}

@BindingAdapter("qpv_verticalPadding")
internal fun setVerticalPadding(view: View, paddingValue: Int) {
    view.setPadding(view.paddingLeft, paddingValue, view.paddingRight, paddingValue)
}
