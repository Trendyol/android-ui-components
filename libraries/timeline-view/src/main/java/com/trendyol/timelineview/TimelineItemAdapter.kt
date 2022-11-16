package com.trendyol.timelineview

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.timelineview.TimelineOrientation.HORIZONTAL
import com.trendyol.timelineview.TimelineOrientation.VERTICAL
import com.trendyol.timelineview.databinding.ItemTimelineBinding
import com.trendyol.timelineview.databinding.ItemTimelineVerticalBinding

class TimelineItemAdapter(var timelineOrientation: TimelineOrientation = HORIZONTAL) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val timelineItems: MutableList<TimelineItemViewState> = mutableListOf()

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
            val binding = ItemTimelineVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            VerticalTimelineViewHolder(binding)
        } else {
            val binding = ItemTimelineBinding.inflate(
                LayoutInflater.from(parent.context),
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
            with(binding) {
                root.layoutParams = root.layoutParams.apply { width = itemViewState.getItemWidth().toInt() }
                with(viewStartLine) {
                    setBackgroundColor(itemViewState.getStartLineColor())
                    visibility = itemViewState.getStartLineVisibility()
                    layoutParams = layoutParams.apply { width = itemViewState.lineWidth.toInt() }
                }
                with(viewEndLine) {
                    setBackgroundColor(itemViewState.getEndLineColor())
                    visibility = itemViewState.getEndLineVisibility()
                    layoutParams = layoutParams.apply { width = itemViewState.lineWidth.toInt() }
                }
                with(imageViewOutsideShadow) {
                    backgroundTintList = ColorStateList.valueOf(itemViewState.getInsideColor())
                    layoutParams = layoutParams.apply {
                        width = itemViewState.getShadowDotSize().toInt()
                        height = itemViewState.getShadowDotSize().toInt()
                    }
                }
                with(imageViewOutside) {
                    backgroundTintList = ColorStateList.valueOf(itemViewState.getOutsideColor())
                    layoutParams = layoutParams.apply {
                        width = itemViewState.getOutsideDotSize().toInt()
                        height = itemViewState.getOutsideDotSize().toInt()
                    }
                }
                with(imageViewInside) {
                    backgroundTintList = ColorStateList.valueOf(itemViewState.getInsideColor())
                    layoutParams = layoutParams.apply {
                        width = itemViewState.getInsideDotSize().toInt()
                        height = itemViewState.getInsideDotSize().toInt()
                    }
                }
                with(textViewTimeline) {
                    maxLines = itemViewState.getItemMaxLineCount()
                    text = itemViewState.getText()
                    setTextColor(itemViewState.getTextColor())
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, itemViewState.textSize)
                    typeface = Typeface.create(itemViewState.fontFamily, Typeface.NORMAL)
                }
            }
        }
    }

    inner class VerticalTimelineViewHolder(private val binding: ItemTimelineVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewState: TimelineItemViewState) {
            with(binding) {
                with(viewStartLine) {
                    setBackgroundColor(itemViewState.getStartLineColor())
                    visibility = itemViewState.getStartLineVisibility()
                    layoutParams = layoutParams.apply { height = itemViewState.lineWidth.toInt() }
                }
                with(viewEndLine) {
                    setBackgroundColor(itemViewState.getEndLineColor())
                    visibility = itemViewState.getEndLineVisibility()
                    layoutParams = layoutParams.apply { height = itemViewState.lineWidth.toInt() }
                }
                with(imageViewOutsideShadow) {
                    backgroundTintList = ColorStateList.valueOf(itemViewState.getInsideColor())
                    layoutParams = layoutParams.apply {
                        width = itemViewState.getShadowDotSize().toInt()
                        height = itemViewState.getShadowDotSize().toInt()
                    }
                }
                with(imageViewOutside) {
                    backgroundTintList = ColorStateList.valueOf(itemViewState.getOutsideColor())
                    layoutParams = layoutParams.apply {
                        width = itemViewState.getOutsideDotSize().toInt()
                        height = itemViewState.getOutsideDotSize().toInt()
                    }
                }
                with(imageViewInside) {
                    backgroundTintList = ColorStateList.valueOf(itemViewState.getInsideColor())
                    layoutParams = layoutParams.apply {
                        width = itemViewState.getInsideDotSize().toInt()
                        height = itemViewState.getInsideDotSize().toInt()
                    }
                }
                with(textViewTimeline) {
                    text = itemViewState.getText()
                    setTextColor(itemViewState.getTextColor())
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, itemViewState.textSize)
                    typeface = Typeface.create(itemViewState.fontFamily, Typeface.NORMAL)
                }
            }
        }
    }

    companion object {

        private const val HORIZONTAL_VIEW_TYPE_ID = 1
        private const val VERTICAL_VIEW_TYPE_ID = 2
    }
}
