package com.trendyol.timelineview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.trendyol.timelineview.databinding.ViewTimelineBinding

class TimelineView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        readAttributes(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        readAttributes(attrs, defStyleAttr)
    }

    @Dimension
    private var dotSize: Float = 0F

    @Dimension
    private var borderWidth: Float = 0F

    @Dimension
    private var textSize: Float = 0F

    private var fontFamily: String = ""

    private val binding: ViewTimelineBinding = inflate(R.layout.view_timeline)

    private val timelineItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TimelineItemAdapter()
    }

    init {
        if (isInEditMode) View.inflate(context, R.layout.view_timeline, this)
        binding.recyclerViewTimelineItems.adapter = timelineItemAdapter
    }

    private fun readAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
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
            fontFamily =
                typedArray.getString(
                    R.styleable.TimelineView_android_fontFamily
                ) ?: context.resources.getString(R.string.tlv_default_font)
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

    fun setFontFamily(fontFamily: String?) {
        this.fontFamily = fontFamily ?: context.resources.getString(R.string.tlv_default_font)
    }

    fun setItems(items: List<TimelineItem>?) {
        binding.viewState = TimelineViewState(
            timelineItems = mapTimelineItemsToTimelineItemViewState(items)
        )
        binding.executePendingBindings()
    }

    private fun mapTimelineItemsToTimelineItemViewState(items: List<TimelineItem>?)
            : List<TimelineItemViewState> {
        return items?.map { timelineItem ->
            TimelineItemViewState(
                timelineItem = timelineItem,
                dotSize = dotSize,
                borderWidth = borderWidth,
                textSize = textSize,
                fontFamily = fontFamily
            )
        } ?: emptyList()
    }
}