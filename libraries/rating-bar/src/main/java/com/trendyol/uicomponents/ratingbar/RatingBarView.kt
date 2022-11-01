package com.trendyol.uicomponents.ratingbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.trendyol.uicomponents.ratingbar.databinding.ViewRatingBarBinding

class RatingBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var starCount: Int = 0

    @ColorInt
    private var starHighlightColor: Int = Color.YELLOW

    @ColorInt
    private var starDefaultColor: Int = Color.GRAY

    private var viewState: RatingBarViewState = createViewState()
        set(value) {
            field = value
            bind()
        }

    private val binding: ViewRatingBarBinding = ViewRatingBarBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.theme?.obtainStyledAttributes(
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

                viewState = createViewState()
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
        viewState = createViewState()
    }

    /**
     * Sets star highlight color.
     *
     * @param color: Color for highlighted star.
     */
    fun setHighlightColor(@ColorInt color: Int) {
        this.starHighlightColor = color
        viewState = createViewState()
    }

    /**
     *
     * Sets star default color. Stars that will not be selected will get this color.
     *
     * @param color: Color for star.
     */
    fun setDefaultStarColor(@ColorInt color: Int) {
        this.starDefaultColor = color
        viewState = createViewState()
    }

    private fun createViewState(): RatingBarViewState = RatingBarViewState(
        starCount = starCount,
        starDefaultColor = starDefaultColor,
        starHighlightColor = starHighlightColor
    )

    private fun bind() {
        with(binding) {
            star1.setTintCompat(viewState.getTint1())
            star2.setTintCompat(viewState.getTint2())
            star3.setTintCompat(viewState.getTint3())
            star4.setTintCompat(viewState.getTint4())
            star5.setTintCompat(viewState.getTint5())
        }
    }
}
