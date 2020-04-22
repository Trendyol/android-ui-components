package com.trendyol.uicomponents.imageslider

import android.view.View
import android.widget.ImageView

data class ImageSliderViewState(
    val imageList: List<String>,
    var isImageDynamic: Boolean? = null,
    var imageHeight: Int? = null,
    var isIndicatorAlwaysVisible: Boolean = false,
    val scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
) {

    fun getIndicatorVisibility(): Int {
        return if (isIndicatorAlwaysVisible) {
            View.VISIBLE
        } else {
            if (isMultiImage()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun isMultiImage() = imageList.size.greaterThan(1)
}
