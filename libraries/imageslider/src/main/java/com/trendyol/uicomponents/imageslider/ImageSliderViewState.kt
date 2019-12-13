package com.trendyol.uicomponents.imageslider

import android.view.View

data class ImageSliderViewState(
    val imageList: List<String>,
    var isImageDynamic: Boolean? = null,
    var imageHeight: Int? = null,
    var isIndicatorAlwaysVisible: Boolean = false
) {

    fun isSingleImage() = imageList.size == 1

    fun isSingleImageVisible() = isSingleImage()

    fun isMultiImage() = imageList.size.greaterThan(1)

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
}
