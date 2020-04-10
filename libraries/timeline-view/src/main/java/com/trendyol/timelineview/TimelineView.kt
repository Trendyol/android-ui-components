package com.trendyol.timelineview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.Dimension
import androidx.core.content.res.use
import com.trendyol.timelineview.databinding.ViewTimelineBinding

class TimelineView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    @Dimension
    private var dotSize: Float = 0F

    @Dimension
    private var borderWidth: Float = 0F

    @Dimension
    private var textSize: Float = 0F

    private val binding: ViewTimelineBinding = inflate(R.layout.view_timeline)

    private val timelineItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TimelineItemAdapter()
    }

    init {
        binding.recyclerViewTimelineItems.adapter = timelineItemAdapter

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TimelineView,
            defStyleAttr,
            0
        ).use { typedArray ->
            dotSize =
                typedArray.getDimension(
                    R.styleable.TimelineView_tlv_dotSize,
                    context.resources.getDimension(R.dimen.tlv_default_dot_size)
                )
            borderWidth =
                typedArray.getDimension(
                    R.styleable.TimelineView_tlv_borderWidth,
                    context.resources.getDimension(R.dimen.tlv_default_border_width)
                )
            textSize =
                typedArray.getDimension(
                    R.styleable.TimelineView_tlv_textSize,
                    context.resources.getDimension(R.dimen.tlv_default_text_size)
                )
        }
    }

    fun setDotSize(@Dimension dotSize: Float?) {
        this.dotSize = dotSize ?: context.resources.getDimension(R.dimen.tlv_default_dot_size)
    }

    fun setBorderWidth(@Dimension borderWidth: Float?) {
        this.borderWidth = borderWidth ?: context.resources.getDimension(R.dimen.tlv_default_border_width)
    }

    fun setTextSize(@Dimension textSize: Float?) {
        this.textSize = textSize ?: context.resources.getDimension(R.dimen.tlv_default_text_size)
    }

    fun setItems(items: List<TimelineItem>?) {
        binding.viewState = TimelineViewState(
            timelineItems = mapTimelineItemsToTimelineItemViewState(items)
        )
        binding.executePendingBindings()
        timelineItemAdapter.submitList(mapTimelineItemsToTimelineItemViewState(items))
    }

    private fun mapTimelineItemsToTimelineItemViewState(items: List<TimelineItem>?)
            : List<TimelineItemViewState> {
        return items?.map { timelineItem ->
            TimelineItemViewState(
                timelineItem = timelineItem,
                dotSize = dotSize,
                borderWidth = borderWidth,
                textSize = textSize
            )
        } ?: emptyList()
    }
}