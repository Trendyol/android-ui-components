package com.trendyol.fitoptionmessageview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.doOnNextLayout

class FitOptionMessageView : LinearLayout {

    private val imageView by lazy { children.first { it is ImageView } as ImageView }
    private val textView by lazy { children.first { it is TextView } as TextView }
    private val initialXPositionOfTextView by lazy { textView.x }
    private val initialLeftPaddingOfTextView by lazy { textView.paddingLeft }

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initialize(attrs)
    }

    private var cradleMargin: Float = 0f
    private var playRevealAnimation: Boolean = false
    private var revealAnimationDuration: Long = 0

    private fun initialize(attrs: AttributeSet? = null) {
        initializeLinearLayout()
        attrs?.let { readAttrs(it) }
        if (playRevealAnimation) {
            doOnNextLayout { startRevealAnimation() }
        }
    }

    private fun initializeLinearLayout() {
        orientation = HORIZONTAL
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    private fun readAttrs(attrs: AttributeSet?) {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.FitOptionMessageView,
            0,
            0
        ).use {
            playRevealAnimation =
                it.getBoolean(R.styleable.FitOptionMessageView_revealAnimation, true)
            revealAnimationDuration =
                it.getInt(R.styleable.FitOptionMessageView_revealAnimationDuration, 2000).toLong()
            cradleMargin = it.getDimension(R.styleable.FitOptionMessageView_cradleMargin, 0f)
        }
    }

    var revealAnimationProvider: (ImageView, TextView) -> Unit = { imageView, _ ->
        val (centerX, centerY) = imageView.x + imageView.measuredWidth / 2.0 to imageView.y + imageView.measuredWidth / 2.0
        val finalRadius = measuredWidth.toFloat()
        ViewAnimationUtils
            .createCircularReveal(
                this@FitOptionMessageView,
                centerX.toInt(),
                centerY.toInt(),
                /*startRadius: */0f,
                finalRadius)
            .setDuration(revealAnimationDuration)
            .start()
    }

    private fun startRevealAnimation() {
        revealAnimationProvider.invoke(imageView, textView)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val radius = imageView.measuredHeight / 2f
        textView.x = initialXPositionOfTextView - radius
        imageView.y = if (isHeightDeterminedByImage()) 0f else (textView.measuredHeight / 2f) - radius
        imageView.elevation = textView.elevation + 0.01f
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val radius = imageView.measuredHeight / 2f
        adjustMeasureSpec(heightMeasureSpec)
        textView.setPadding(
            initialLeftPaddingOfTextView + cradleMargin.toInt() + radius.toInt(),
            textView.paddingTop,
            textView.paddingRight,
            textView.paddingBottom
        )
    }

    private fun adjustMeasureSpec(heightMeasureSpec: Int) {
        val lp = textView.layoutParams as LayoutParams
        val radius = imageView.measuredHeight / 2

        val childHeightMeasureSpec = getChildMeasureSpec(
            heightMeasureSpec,
            paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin,
            lp.height
        )
        val childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth-radius, MeasureSpec.EXACTLY)
        textView.measure(childWidthMeasureSpec, childHeightMeasureSpec)
    }

    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.white)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

    private val maskPath = Path()
    private val clipPath = Path()
    private val drawPath = Path()

    private fun calculateMaskPath() {
        clipPath.reset()
        drawPath.reset()
        val (centerX, centerY) = textView.x to if (isHeightDeterminedByImage()) imageView.measuredHeight / 2f else textView.measuredHeight / 2f
        val radius = imageView.measuredHeight / 2f
        clipPath.addCircle(centerX, centerY, radius + cradleMargin, Path.Direction.CCW)
        drawPath.addCircle(centerX, centerY, radius, Path.Direction.CCW)
        clipPath.op(drawPath, Path.Op.DIFFERENCE)
        maskPath.set(clipPath)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val saveCount = canvas.save()
        super.dispatchDraw(canvas)
        calculateMaskPath()
        canvas.drawPath(maskPath, maskPaint)
        canvas.restoreToCount(saveCount)
    }

    private fun isHeightDeterminedByImage(): Boolean =
        imageView.measuredHeight > textView.measuredHeight

}