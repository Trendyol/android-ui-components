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

    private val imageView by lazy { children.first { it is ImageView } as ImageView }
    private val textView by lazy { children.first { it is TextView } as TextView }
    private val initialXPositionOfTextView by lazy { textView.x }
    private val initialLeftPaddingOfTextView by lazy { textView.paddingStart }
    private var cradleMargin: Float = 0f
    private var playRevealAnimation: Boolean = false
    private var revealAnimationDuration: Long = 0
    private var revealAnimationProvider: (ImageView, TextView) -> Unit = { imageView, _ ->
        val (centerX, centerY) = imageView.x + imageView.measuredWidth / 2.0 to imageView.y + imageView.measuredWidth / 2.0
        val finalRadius = measuredWidth.toFloat()
        ViewAnimationUtils
            .createCircularReveal(
                this@FitOptionMessageView,
                centerX.toInt(),
                centerY.toInt(),
                /*startRadius: */0f,
                finalRadius
            )
            .setDuration(revealAnimationDuration)
            .start()
    }
    private val maskPath = Path()
    private val clipPath = Path()
    private val drawPath = Path()
    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.white)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

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

    private fun startRevealAnimation() {
        revealAnimationProvider.invoke(imageView, textView)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        textView.x = getTextViewInitialX()
        imageView.y = getImageViewInitialY()
        imageView.elevation = textView.elevation + 0.01f
        if (layoutDirection == LAYOUT_DIRECTION_RTL) {
            imageView.x = measuredWidth - 2 * getRadius()
        }
    }

    private fun getTextViewInitialX(): Float {
        return if (layoutDirection == LAYOUT_DIRECTION_LTR) {
            initialXPositionOfTextView - getRadius()
        } else {
            initialXPositionOfTextView
        }
    }

    private fun getImageViewInitialY(): Float {
        return if (isHeightDeterminedByImage()) {
            0f
        } else {
            (textView.measuredHeight / 2f) - getRadius()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        adjustMeasureSpec(heightMeasureSpec)
        textView.setPadding(
            getTextViewLeftPadding(),
            textView.paddingTop,
            getTextViewRightPadding(),
            textView.paddingBottom
        )
    }

    private fun getTextViewLeftPadding(): Int {
        return if (layoutDirection == LAYOUT_DIRECTION_LTR) {
            initialLeftPaddingOfTextView + cradleMargin.toInt() + getRadius().toInt()
        } else {
            textView.paddingLeft
        }
    }

    private fun getTextViewRightPadding(): Int {
        return if (layoutDirection == LAYOUT_DIRECTION_LTR) {
            textView.paddingRight
        } else {
            initialLeftPaddingOfTextView + cradleMargin.toInt() + getRadius().toInt()
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        val saveCount = canvas.save()
        super.dispatchDraw(canvas)
        calculateMaskPath()
        canvas.drawPath(maskPath, maskPaint)
        canvas.restoreToCount(saveCount)
    }

    private fun adjustMeasureSpec(heightMeasureSpec: Int) {
        val lp = textView.layoutParams as LayoutParams

        val childHeightMeasureSpec = getChildMeasureSpec(
            heightMeasureSpec,
            paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin,
            lp.height
        )
        val childWidthMeasureSpec =
            MeasureSpec.makeMeasureSpec(measuredWidth - getRadius().toInt(), MeasureSpec.EXACTLY)
        textView.measure(childWidthMeasureSpec, childHeightMeasureSpec)
    }

    private fun calculateMaskPath() {
        clipPath.reset()
        drawPath.reset()
        val (centerX, centerY) = getCenterX() to getCenterY()
        clipPath.addCircle(centerX, centerY, getRadius() + cradleMargin, Path.Direction.CCW)
        drawPath.addCircle(centerX, centerY, getRadius(), Path.Direction.CCW)
        clipPath.op(drawPath, Path.Op.DIFFERENCE)
        maskPath.set(clipPath)
    }

    private fun getCenterX(): Float {
        return if (layoutDirection == LAYOUT_DIRECTION_LTR) {
            textView.x
        } else {
            textView.x + textView.measuredWidth
        }
    }

    private fun getCenterY(): Float {
        return if (isHeightDeterminedByImage()) {
            getRadius()
        } else {
            textView.measuredHeight / 2f
        }
    }

    private fun isHeightDeterminedByImage(): Boolean =
        imageView.measuredHeight > textView.measuredHeight

    private fun getRadius(): Float = imageView.measuredHeight / 2f
}