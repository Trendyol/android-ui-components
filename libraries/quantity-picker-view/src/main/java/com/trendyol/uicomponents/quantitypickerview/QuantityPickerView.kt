package com.trendyol.uicomponents.quantitypickerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.databinding.DataBindingUtil
import com.trendyol.uicomponents.quantitypickerview.databinding.ViewQuantityPickerBinding

class QuantityPickerView : ConstraintLayout {

    var onAddClicked: ((Int) -> Boolean)? = null
    var onSubtractClicked: ((Int) -> Boolean)? = null

    private lateinit var binding: ViewQuantityPickerBinding

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setUpAttributes(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setUpAttributes(attrs, defStyleAttr)
    }

    init {
        if (isInEditMode) {
            View.inflate(context, R.layout.view_quantity_picker, this)
        } else {
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.view_quantity_picker,
                this,
                true
            )
        }
        setUpView()
    }

    fun setQuantityPickerViewState(quantityPickerViewState: QuantityPickerViewState?) =
        with(binding) {
            viewState = quantityPickerViewState
            executePendingBindings()
        }

    fun setQuantity(quantity: Int) =
        setQuantityPickerViewState(binding.viewState?.getWithQuantity(quantity))

    fun stopLoading() = setQuantityPickerViewState(binding.viewState?.stopLoading())

    fun reset() = setQuantityPickerViewState(binding.viewState?.reset())

    private fun setUpView() {
        with(binding) {
            text.setOnClickListener {
                if (viewState?.isLoading() == true || viewState?.isInQuantityMode() == true) return@setOnClickListener

                setQuantityPickerViewState(
                    if (onAddClicked?.invoke(viewState?.currentQuantity ?: 0) == true) {
                        viewState?.getWithLoading(increment = true)
                    } else {
                        viewState?.getWithAddValue()
                    }
                )
            }
            imageSubtract.setOnClickListener {
                if (viewState?.isLoading() == true) return@setOnClickListener

                setQuantityPickerViewState(
                    if (onSubtractClicked?.invoke(viewState?.currentQuantity ?: 0) == true) {
                        viewState?.getWithLoading(increment = false)
                    } else {
                        viewState?.getWithSubtractValue()
                    }
                )
            }
            imageAdd.setOnClickListener {
                if (viewState?.isLoading() == true) return@setOnClickListener

                setQuantityPickerViewState(
                    if (onAddClicked?.invoke(viewState?.currentQuantity ?: 0) == true) {
                        viewState?.getWithLoading(true)
                    } else {
                        viewState?.getWithAddValue()
                    }
                )
            }
        }
    }

    private fun setUpAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.QuantityPickerView,
            defStyleAttr,
            0
        ).use {
            val text = it.getString(R.styleable.QuantityPickerView_qpv_text) ?: ""
            val textColor = it.getColor(
                R.styleable.QuantityPickerView_qpv_textColor,
                context.themeColor(R.attr.colorAccent)
            )
            val textSize =
                it.getDimensionPixelSize(R.styleable.QuantityPickerView_qpv_textSize, context.asSP(12))
            val textStyle = it.getInt(R.styleable.QuantityPickerView_qpv_textStyle, 0)
            val quantityTextColor =
                it.getColor(
                    R.styleable.QuantityPickerView_qpv_quantityTextColor,
                    context.themeColor(R.attr.colorPrimary)
                )
            val quantityTextSize =
                it.getDimensionPixelSize(
                    R.styleable.QuantityPickerView_qpv_quantityTextSize,
                    context.asSP(14)
                )
            val quantityTextStyle =
                it.getInt(R.styleable.QuantityPickerView_qpv_quantityTextStyle, 0)
            val currentQuantity = it.getInt(R.styleable.QuantityPickerView_qpv_currentQuantity, 0)
            val background = it.getDrawable(R.styleable.QuantityPickerView_android_background)
                ?: AppCompatResources.getDrawable(
                    context,
                    R.drawable.qpv_shape_default_background
                )!!
            val progressTintColor =
                it.getColor(
                    R.styleable.QuantityPickerView_android_progressTint,
                    context.themeColor(R.attr.colorPrimary)
                )
            val removeIcon = it.getDrawable(R.styleable.QuantityPickerView_qpv_removeIcon)
                ?: AppCompatResources.getDrawable(context, R.drawable.qpv_ic_default_remove)!!
            val addIcon = it.getDrawable(R.styleable.QuantityPickerView_qpv_addIcon)
                ?: AppCompatResources.getDrawable(context, R.drawable.qpv_ic_default_add)!!
            val subtractIcon = it.getDrawable(R.styleable.QuantityPickerView_qpv_subtractIcon)
                ?: AppCompatResources.getDrawable(context, R.drawable.qpv_ic_default_subtract)!!
            val quantityBackground =
                it.getDrawable(R.styleable.QuantityPickerView_qpv_quantityBackground)
                    ?: AppCompatResources.getDrawable(context, android.R.color.transparent)!!

            val quantityPickerViewState = QuantityPickerViewState(
                text = text,
                textColor = textColor,
                textSize = textSize,
                textStyle = textStyle,
                quantityTextColor = quantityTextColor,
                quantityTextSize = quantityTextSize,
                quantityTextStyle = quantityTextStyle,
                currentQuantity = currentQuantity,
                backgroundDrawable = background,
                progressTintColor = progressTintColor,
                removeIconDrawable = removeIcon,
                addIconDrawable = addIcon,
                subtractIconDrawable = subtractIcon,
                showLoading = false,
                quantityBackgroundDrawable = quantityBackground
            )
            setQuantityPickerViewState(quantityPickerViewState)
        }
    }
}
