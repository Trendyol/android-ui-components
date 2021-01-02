package com.trendyol.timelineview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.timelineview.TimelineOrientation.HORIZONTAL
import com.trendyol.timelineview.TimelineOrientation.VERTICAL
import com.trendyol.timelineview.databinding.ItemTimelineBinding
import com.trendyol.timelineview.databinding.ItemTimelineVerticalBinding

class TimelineItemAdapter(var timelineOrientation: TimelineOrientation = HORIZONTAL) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var timelineItems: MutableList<TimelineItemViewState> = mutableListOf()

    private val HORIZONTAL_VIEW_TYPE_ID = 1

    private val VERTICAL_VIEW_TYPE_ID = 2

    @JvmOverloads
    fun submitList(list: List<TimelineItemViewState> = emptyList()) {
        this.timelineItems.clear()
        this.timelineItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (timelineOrientation) {
            HORIZONTAL -> HORIZONTAL_VIEW_TYPE_ID
            VERTICAL -> VERTICAL_VIEW_TYPE_ID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VERTICAL_VIEW_TYPE_ID) {
            val binding = DataBindingUtil.inflate<ItemTimelineVerticalBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_timeline_vertical,
                parent,
                false
            )
            VerticalTimelineViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<ItemTimelineBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_timeline,
                parent,
                false
            )
            HorizontalTimelineViewHolder(binding)
        }

    }

    override fun getItemCount(): Int = timelineItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (timelineOrientation == VERTICAL) {
            (holder as VerticalTimelineViewHolder).bind(timelineItems[position])
        } else {
            (holder as HorizontalTimelineViewHolder).bind(timelineItems[position])
        }
    }

    inner class HorizontalTimelineViewHolder(private val binding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewState: TimelineItemViewState) {
            binding.itemViewState = itemViewState
            binding.executePendingBindings()
        }
    }

    inner class VerticalTimelineViewHolder(private val binding: ItemTimelineVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewState: TimelineItemViewState) {
            binding.itemViewState = itemViewState
            binding.executePendingBindings()
        }
    }
}