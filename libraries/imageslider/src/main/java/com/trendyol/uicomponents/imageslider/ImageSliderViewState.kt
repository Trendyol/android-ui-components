package com.trendyol.uicomponents.imageslider

data class ImageSliderViewState(
    val imageList: List<String>,
    var isImageDynamic: Boolean? = null,
    var imageHeight: Int? = null
) {

    fun isSingleImage() = imageList.size == 1

    fun isSingleImageVisible() = isSingleImage()

    private fun isMultiImage() = imageList.size.greaterThan(1)
}
