package com.trendyol.uicomponents.imageslider

import android.app.Activity

interface ImageSlider {

    fun setImageSliderViewListener(imageSliderViewListener: ImageSliderView.ImageSliderViewListener)

    fun setActivityInstance(activity: Activity)
}
