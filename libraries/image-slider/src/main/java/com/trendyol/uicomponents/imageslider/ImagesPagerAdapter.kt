package com.trendyol.uicomponents.imageslider

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.trendyol.uicomponents.imageslider.databinding.ViewImageBinding
import com.trendyol.uicomponents.imageslider.touchdelegator.WindowTouchDelegator
import com.trendyol.uicomponents.imageslider.touchdelegator.ZoomWindowTouchListener
import java.lang.ref.WeakReference

class ImagesPagerAdapter(
    private val activityWeakReference: WeakReference<Activity>,
    private val itemClickListener: SliderAdapterItemClickListener
) : PagerAdapter() {

    private var imageUrlList: MutableList<String> = mutableListOf()
    private var binding: ViewImageBinding? = null
    var imageHeight: Int? = null
    var isImageDynamic: Boolean = false
    var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP

    private val zoomWindowListener: ZoomWindowTouchListener?
        get() {
            if (activityWeakReference.get() != null) {
                if (activityWeakReference.get() is WindowTouchDelegator) {
                    val touchListeners =
                        (activityWeakReference.get() as WindowTouchDelegator).getWindowTouchListeners()
                    for (touchListenerItem in touchListeners) {
                        if (touchListenerItem is ZoomWindowTouchListener) {
                            return touchListenerItem
                        }
                    }
                }
            }
            return null
        }

    interface SliderAdapterItemClickListener {

        fun onImageItemClicked(imageUrlList: List<String>, position: Int)
    }

    fun addToImageList(imageList: List<String>) {
        imageUrlList.clear()
        imageUrlList.addAll(imageList)
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(collection.context)
        return DataBindingUtil.inflate<ViewImageBinding>(
            inflater,
            R.layout.view_image,
            collection,
            false
        )
            .apply {
                binding = this
                collection.addView(linearLayoutSliderImageWrapper)
                imageViewSlider.scaleType = scaleType
                loadImage(
                    collection,
                    imageViewSlider,
                    imageUrlList.getOrNull(position)
                )
                linearLayoutSliderImageWrapper.setOnClickListener {
                    itemClickListener.onImageItemClicked(imageUrlList, position)
                }
            }.linearLayoutSliderImageWrapper
    }

    override fun getCount(): Int {
        return imageUrlList.size
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    private fun loadImage(parent: ViewGroup, imageView: ImageView, url: String?) {
        if (url == null) return
        if (isImageDynamic) {
            imageHeight?.let { imageView.loadProductImageWithHeight(url, it) }
        } else {
            val imageWidth = getProductImageWidth(parent)
            imageView.loadProductImageWithWidth(url, imageWidth)
        }

        initializeZoomableView(imageView)
    }

    private fun getProductImageWidth(parent: ViewGroup): Int =
        activityWeakReference.get()?.deviceWidth() ?: parent.measuredWidth


    private fun initializeZoomableView(imageView: ImageView) {
        activityWeakReference.get()?.let { context ->
            CVFloatingZoomView(context, imageView, zoomWindowListener)
        }
    }
}
