package com.trendyol.uicomponents.imageslider

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.trendyol.uicomponents.imageslider.databinding.ViewImageSliderBinding
import java.lang.ref.WeakReference

class ImageSliderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr),
    ImagesPagerAdapter.SliderAdapterItemClickListener,
    ImageSlider {

    private var binding: ViewImageSliderBinding = inflate(R.layout.view_image_slider)
    private var hostActivityWeakReference: WeakReference<Activity> = WeakReference<Activity>(null)
    private var imageSliderViewListener: ImageSliderViewListener? = null

    interface ImageSliderViewListener {

        fun onImageSliderItemClicked(imageUrlList: List<String>, position: Int)
    }

    override fun setImageSliderViewListener(imageSliderViewListener: ImageSliderViewListener) {
        this.imageSliderViewListener = imageSliderViewListener
    }

    override fun setActivityInstance(activity: Activity) {
        hostActivityWeakReference = WeakReference(activity)
    }

    fun setViewState(imageSliderViewState: ImageSliderViewState?) {
        imageSliderViewState?.let {
            binding.viewState = it
            binding.executePendingBindings()

            setImages(imageSliderViewState)
        }
    }

    private fun setImages(viewState: ImageSliderViewState) {
        val pagerAdapter = ImagesPagerAdapter(
            activityWeakReference = hostActivityWeakReference,
            itemClickListener = this,
            scaleType = viewState.scaleType,
            imageUrlList = viewState.imageList,
            backgroundColor = viewState.backgroundColor,
            cornerRadiusInDp = viewState.cornerRadiusInDp
        )
        binding.viewPagerImageSlider.adapter = pagerAdapter
        binding.indicatorImageSlider.setViewPager(binding.viewPagerImageSlider)
    }

    override fun onImageItemClicked(imageUrlList: List<String>, position: Int) {
        imageSliderViewListener?.onImageSliderItemClicked(imageUrlList, position)
    }
}
