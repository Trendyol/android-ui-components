package com.trendyol.uicomponents.imageslider

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.trendyol.uicomponents.imageslider.databinding.ViewImageBinding
import com.trendyol.uicomponents.touchdelegator.WindowTouchDelegator
import com.trendyol.uicomponents.imageslider.touchdelegator.ZoomWindowTouchListener
import java.lang.ref.WeakReference

class ImagesPagerAdapter(
    private val activityWeakReference: WeakReference<Activity>,
    private val itemClickListener: SliderAdapterItemClickListener,
    private val scaleType: ImageView.ScaleType,
    private val imageUrlList: List<String>,
    @ColorInt private val backgroundColor: Int,
    private val cornerRadiusInDp: Double? = null
) : PagerAdapter() {

    private var binding: ViewImageBinding? = null

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

    override fun instantiateItem(collection: ViewGroup, position: Int): Any =
        DataBindingUtil.inflate<ViewImageBinding>(
            LayoutInflater.from(collection.context),
            R.layout.view_image,
            collection,
            false
        ).apply {
            binding = this
            collection.addView(linearLayoutSliderImageWrapper)
            imageViewSlider.scaleType = scaleType
            loadImage(
                imageViewSlider,
                imageUrlList.getOrNull(position)
            )
            setBackgroundColor(imageViewSlider)
            linearLayoutSliderImageWrapper.setOnClickListener {
                itemClickListener.onImageItemClicked(imageUrlList, position)
            }
        }.linearLayoutSliderImageWrapper

    override fun getCount(): Int = imageUrlList.size

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    private fun loadImage(imageView: ImageView, url: String?) {
        if (url == null) return

        imageView.loadImage(url, cornerRadiusInDp)

        initializeZoomableView(imageView)
    }

    private fun initializeZoomableView(imageView: ImageView) {
        activityWeakReference.get()?.let { context ->
            CVFloatingZoomView(context, imageView, zoomWindowListener)
        }
    }

    private fun setBackgroundColor(imageView: ImageView) {
        imageView.setBackgroundColor(backgroundColor)
    }
}
