package com.trendyol.timelineview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.Dimension
import androidx.core.content.res.use
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.timelineview.databinding.ViewTimelineBinding

class TimelineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    @Dimension
    private var dotSize: Float = 0F

    @Dimension
    private var borderWidth: Float = 0F

    @Dimension
    private var textSize: Float = 0F

    @Dimension
    private var lineWidth: Float = 0F

    private var fontFamily: String = ""

    private var maxLineCount: Int = 2

    private val binding: ViewTimelineBinding = ViewTimelineBinding.inflate(LayoutInflater.from(context), this)

    private var timelineOrientation: TimelineOrientation = TimelineOrientation.HORIZONTAL

    private val timelineItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TimelineItemAdapter()
    }

    init {
        binding.recyclerViewTimelineItems.adapter = timelineItemAdapter
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.TimelineView, defStyleAttr, 0
        ).use { typedArray ->
            dotSize = typedArray.getDimension(
                R.styleable.TimelineView_tlv_dotSize, context.resources.getDimension(R.dimen.tlv_default_dot_size)
            )
            borderWidth = typedArray.getDimension(
                R.styleable.TimelineView_tlv_borderWidth,
                context.resources.getDimension(R.dimen.tlv_default_border_width)
            )
            textSize = typedArray.getDimension(
                R.styleable.TimelineView_tlv_textSize, context.resources.getDimension(R.dimen.tlv_default_text_size)
            )
            lineWidth = typedArray.getDimension(
                R.styleable.TimelineView_tlv_lineWidth, context.resources.getDimension(R.dimen.tlv_width_lines)
            )
            fontFamily = typedArray.getString(
                R.styleable.TimelineView_android_fontFamily
            ) ?: context.resources.getString(R.string.tlv_default_font)
            timelineOrientation = if (typedArray.getInt(R.styleable.TimelineView_tlv_orientation, 0) == 0) {
                TimelineOrientation.HORIZONTAL
            } else {
                TimelineOrientation.VERTICAL
            }
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

    fun setLineWidth(@Dimension lineWidth: Float?) {
        this.lineWidth = lineWidth ?: context.resources.getDimension(R.dimen.tlv_width_lines)
    }

    fun setFontFamily(fontFamily: String?) {
        this.fontFamily = fontFamily ?: context.resources.getString(R.string.tlv_default_font)
    }

    fun setOrientation(timelineOrientation: TimelineOrientation) {
        this.timelineOrientation = timelineOrientation
    }

    fun setMaxLineCount(maxLineCount: Int?) {
        this.maxLineCount = maxLineCount ?: 2
    }

    fun setItems(items: List<TimelineItem>?) {
        with(binding.recyclerViewTimelineItems) {
            val viewState = TimelineViewState(
                timelineOrientation = timelineOrientation,
                timelineItems = mapTimelineItemsToTimelineItemViewState(items)
            )
            (adapter as? TimelineItemAdapter)?.apply {
                submitList(viewState.timelineItems)
                (layoutManager as? LinearLayoutManager)?.orientation =
                    if (viewState.timelineOrientation == TimelineOrientation.VERTICAL) {
                        RecyclerView.VERTICAL
                    } else {
                        RecyclerView.HORIZONTAL
                    }
                this.timelineOrientation = viewState.timelineOrientation
            }
        }
    }

    fun startTooltipAnimation(delay: Long, milliSecondsPerInch: Float) {
        postDelayed({
            binding.recyclerViewTimelineItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView, newState: Int
                ) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        binding.recyclerViewTimelineItems.removeOnScrollListener(this)
                        val layoutManager: RecyclerView.LayoutManager =
                            binding.recyclerViewTimelineItems.layoutManager ?: return
                        val smoothScroller = SmoothScroller(context, milliSecondsPerInch)
                        smoothScroller.targetPosition = -10
                        layoutManager.startSmoothScroll(smoothScroller)
                    }
                }
            })

            val lm = binding.recyclerViewTimelineItems.layoutManager
            val smoothScroller = SmoothScroller(context, milliSecondsPerInch)
            smoothScroller.targetPosition = timelineItemAdapter.itemCount - 1
            lm?.startSmoothScroll(smoothScroller)
        }, delay)
    }

    private fun mapTimelineItemsToTimelineItemViewState(items: List<TimelineItem>?): List<TimelineItemViewState> {
        return items?.map { timelineItem ->
            TimelineItemViewState(
                timelineItem = timelineItem,
                dotSize = dotSize,
                borderWidth = borderWidth,
                textSize = textSize,
                lineWidth = lineWidth,
                fontFamily = fontFamily,
                maxLineCount = maxLineCount
            )
        } ?: emptyList()
    }
}
