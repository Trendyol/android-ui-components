package com.trendyol.uicomponents.ratingbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.trendyol.uicomponents.ratingbar.databinding.ViewRatingBarBinding

class RatingBarView : LinearLayout {

    private var starCount: Int = 0
    @ColorInt
    private var starHighlightColor: Int = Color.YELLOW
    @ColorInt
    private var starDefaultColor: Int = Color.GRAY

    private val binding: ViewRatingBarBinding = inflate(R.layout.view_rating_bar)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        context?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.RatingBarView,
            defStyleAttr,
            0
        )?.apply {
            try {
                starCount = getInteger(R.styleable.RatingBarView_starCount, 0)
                starHighlightColor =
                    getColor(
                        R.styleable.RatingBarView_starHighlightColor,
                        context.color(R.color.star_highlight)
                    )
                starDefaultColor = getColor(
                    R.styleable.RatingBarView_starDefaultColor,
                    context.color(R.color.star_default)
                )

                setViewState(createViewState())
            } finally {
                recycle()
            }
        }
    }

    /**
     *
     * Sets star count to highlight with provided @param starHighlightColor
     *
     * @param starCount: starCount to highlight
     */
    fun setStarCount(starCount: Int) {
        this.starCount = starCount
        setViewState(createViewState())
    }

    /**
     * Sets star highlight color.
     *
     * @param color: Color for highlighted star.
     */
    fun setHightlightColor(@ColorInt color: Int) {
        this.starHighlightColor = color
        setViewState(createViewState())
    }

    /**
     *
     * Sets star default color. Stars that will not be selected will get this color.
     *
     * @param color: Color for star.
     */
    fun setDefaultStarColor(@ColorInt color: Int) {
        this.starDefaultColor = color
        setViewState(createViewState())
    }

    private fun createViewState(): RatingBarViewState = RatingBarViewState(
        starCount = starCount,
        starDefaultColor = starDefaultColor,
        starHighlightColor = starHighlightColor
    )

    private fun setViewState(viewState: RatingBarViewState?) {
        binding.viewState = viewState
        binding.executePendingBindings()
    }
}
