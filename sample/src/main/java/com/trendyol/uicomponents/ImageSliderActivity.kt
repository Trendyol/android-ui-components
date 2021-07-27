package com.trendyol.uicomponents

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.trendyol.uicomponents.databinding.ActivityImageSliderBinding
import com.trendyol.uicomponents.imageslider.ImageSliderViewState

class ImageSliderActivity : AppCompatActivity() {


    private lateinit var binding: ActivityImageSliderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_slider)
        binding.imageSliderView.setActivityInstance(this)
        binding.imageSliderView.setViewState(
            ImageSliderViewState(
                listOf(
                    "https://picsum.photos/id/1026/1920/1280",
                    "https://picsum.photos/id/1024/1920/1280",
                    "https://picsum.photos/id/1027/1920/1280",
                    "https://picsum.photos/id/1028/1920/1280",
                    "https://picsum.photos/id/1029/1920/1280"
                ),
                cornerRadiusInDp = 8.0,
                scaleType = ImageView.ScaleType.FIT_CENTER
            )
        )
    }
}
