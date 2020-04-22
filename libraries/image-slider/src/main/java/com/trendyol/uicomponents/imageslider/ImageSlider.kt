package com.trendyol.uicomponents.imageslider

import androidx.appcompat.app.AppCompatActivity


interface ImageSlider {

    fun setImageSliderViewListener(imageSliderViewListener: ImageSliderView.ImageSliderViewListener)

    fun setActivityInstance(activity: AppCompatActivity)
}
