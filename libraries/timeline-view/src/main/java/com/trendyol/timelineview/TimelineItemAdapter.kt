package com.trendyol.timelineview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.timelineview.databinding.ItemTimelineBinding

class TimelineItemAdapter : RecyclerView.Adapter<TimelineItemAdapter.TimelineViewHolder>() {

    private var timelineItems: MutableList<TimelineItemViewState> = mutableListOf()

    @JvmOverloads
    fun submitList(list: List<TimelineItemViewState> = emptyList()) {
        this.timelineItems.clear()
        this.timelineItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val binding = DataBindingUtil.inflate<ItemTimelineBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_timeline,
            parent,
            false
        )
        return TimelineViewHolder(binding)
    }

    override fun getItemCount(): Int = timelineItems.size

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bind(timelineItems[position])
    }

    inner class TimelineViewHolder(private val binding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewState: TimelineItemViewState) {
            binding.itemViewState = itemViewState
            binding.executePendingBindings()
        }
    }
}