package com.trendyol.uicomponents.dialogs

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

sealed class ItemDivider : Parcelable {

    internal open fun decorate(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
    }

    internal open fun onDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
    }

    @Parcelize
    class MarginDivider(
        private val marginSize: Int,
        private val marginDirections: List<MarginDirection> = listOf(
            MarginDirection.START,
            MarginDirection.TOP,
            MarginDirection.END,
            MarginDirection.BOTTOM,
        ),
        private val shouldAddInitialTopMargin: Boolean = false,
    ) : ItemDivider() {

        override fun decorate(
            outRect: Rect,
            view: View,
            recyclerView: RecyclerView,
            state: RecyclerView.State
        ) {
            if (shouldAddInitialTopMargin) {
                val adapterPosition = recyclerView.getChildAdapterPosition(view)
                if (adapterPosition == 0) {
                    outRect.top = marginSize
                }
            }
            marginDirections.forEach {
                when (it) {
                    MarginDirection.START -> {
                        outRect.left = marginSize
                    }
                    MarginDirection.TOP -> {
                        outRect.top = marginSize
                    }
                    MarginDirection.END -> {
                        outRect.right = marginSize
                    }
                    MarginDirection.BOTTOM -> {
                        outRect.bottom = marginSize
                    }
                }
            }
        }

        enum class MarginDirection {
            START, END, TOP, BOTTOM;
        }
    }

    @Parcelize
    class DrawableDivider(
        @DrawableRes private val drawableResource: Int
    ) : ItemDivider() {

        @IgnoredOnParcel
        private var drawable: Drawable? = null

        @IgnoredOnParcel
        private val mBounds = Rect()

        override fun decorate(
            outRect: Rect,
            view: View,
            recyclerView: RecyclerView,
            state: RecyclerView.State,
        ) {
            if (drawable == null) {
                drawable = ContextCompat.getDrawable(recyclerView.context, drawableResource)
            }
            outRect.set(0, 0, 0, drawable?.intrinsicHeight ?: 0)
        }

        override fun onDraw(
            canvas: Canvas,
            recyclerView: RecyclerView,
            state: RecyclerView.State
        ) {
            val drawable = drawable ?: return
            canvas.save()
            val left: Int
            val right: Int
            //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
            if (recyclerView.clipToPadding) {
                left = recyclerView.paddingLeft
                right = recyclerView.width - recyclerView.paddingRight
                canvas.clipRect(left, recyclerView.paddingTop, right, recyclerView.height - recyclerView.paddingBottom)
            } else {
                left = 0
                right = recyclerView.width
            }

            val childCount = recyclerView.childCount
            for (position in 0 until childCount) {
                val child = recyclerView.getChildAt(position)
                recyclerView.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + child.translationY.roundToInt()
                val top = bottom - drawable.intrinsicHeight
                drawable.setBounds(left, top, right, bottom)
                drawable.draw(canvas)
            }
            canvas.restore()
        }

    }
}