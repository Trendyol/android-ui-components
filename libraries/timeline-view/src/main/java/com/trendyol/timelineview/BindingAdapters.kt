package com.trendyol.timelineview

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("timelineItems")
internal fun RecyclerView.submitList(items: List<TimelineItemViewState>?) {
    (adapter as? TimelineItemAdapter)?.submitList(items.orEmpty())
}

@BindingAdapter("bind:layout_height")
fun setLayoutHeight(view: View, height: Float) {
    view.layoutParams = view.layoutParams.apply { this.height = height.toInt() }
}

@BindingAdapter("bind:layout_width")
fun setLayoutWidth(view: View, height: Float) {
    view.layoutParams = view.layoutParams.apply { this.width = height.toInt() }
}