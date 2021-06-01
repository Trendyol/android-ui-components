package com.trendyol.uicomponents.quantitypickerview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.use
import androidx.databinding.DataBindingUtil
import com.trendyol.uicomponents.quantitypickerview.databinding.ViewQuantityPickerBinding
import com.trendyol.uicomponents.quantitypickerview.databinding.ViewQuantityPickerVerticalBinding

class QuantityPickerView : ConstraintLayout {

    var onAddClicked: ((Int) -> Boolean)? = null
    var onSubtractClicked: ((Int) -> Boolean)? = null
    var expansionListener: ((ExpansionState) -> Unit)? = null
    var onQuantityTextClicked: ((Int) -> Unit)? = null

    private lateinit var binding: ViewQuantityPickerBinding

    constructor(context: Context) : super(context) {
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initializeView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initializeView(attrs, defStyleAttr)
    }

    private fun initializeView(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        val viewState = readAttributes(attrs, defStyleAttr)
        if (isInEditMode) {
            inflateViewForPreview(viewState)
        } else {
            inflateView()
            setUpView()
            setQuantityPickerViewState(viewState)
            applyVerticalConstraintsIfNeeded()
        }
    }

    private fun inflateView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_quantity_picker,
            this,
            true
        )
    }

    private fun inflateViewForPreview(viewState: QuantityPickerViewState) {
        val layoutId =
            if (viewState.orientation == VERTICAL_ORIENTATION) R.layout.view_quantity_picker_vertical else R.layout.view_quantity_picker
        View.inflate(context, layoutId, this)
    }

    private fun setUpView() {
        with(binding) {
            text.setOnClickListener {
                onQuantityTextClicked?.invoke(viewState?.currentQuantity ?: 0)
                if (viewState?.isLoading() == true || viewState?.isInQuantityMode() == true) return@setOnClickListener
                setQuantityPickerViewState(
                    if (onAddClicked?.invoke(viewState?.currentQuantity ?: 0) == true) {
                        viewState?.getWithLoading()
                    } else {
                        viewState?.getWithAddValue()
                    }
                )
            }
            quantityText.setOnClickListener {
                val showLoading = onAddClicked?.invoke(viewState?.currentQuantity ?: 0) == true
                val updatedViewState = if (showLoading) {
                    viewState?.getWithLoading()
                } else {
                    viewState?.getWithAddValue()
                }
                setQuantityPickerViewState(updatedViewState)

            }
            imageSubtract.setOnClickListener {
                if (viewState?.isLoading() == true) return@setOnClickListener

                setQuantityPickerViewState(
                    if (onSubtractClicked?.invoke(viewState?.currentQuantity ?: 0) == true) {
                        viewState?.getWithLoading()
                    } else {
                        viewState?.getWithSubtractValue()
                    }
                )
            }
            imageAdd.setOnClickListener {
                if (viewState?.isLoading() == true) return@setOnClickListener

                setQuantityPickerViewState(
                    if (onAddClicked?.invoke(viewState?.currentQuantity ?: 0) == true) {
                        viewState?.getWithLoading()
                    } else {
                        viewState?.getWithAddValue()
                    }
                )
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            clipToOutline = true
        }
    }

    fun setQuantityPickerViewState(quantityPickerViewState: QuantityPickerViewState?) {
        if (quantityPickerViewState == null) return
        bindRootViewProperties(quantityPickerViewState)
        val previousViewState = binding.viewState
        binding.viewState = quantityPickerViewState
        binding.executePendingBindings()
        onViewStateChanged(previousViewState, quantityPickerViewState)
    }

    private fun onViewStateChanged(
        previousViewState: QuantityPickerViewState?,
        currentViewState: QuantityPickerViewState
    ) {
        if (previousViewState?.isCollapsed() != currentViewState.isCollapsed()) {
            expansionListener?.invoke(currentViewState.expansionState)
        }
    }

    private fun bindRootViewProperties(quantityPickerViewState: QuantityPickerViewState) {
        setBackground(quantityPickerViewState.backgroundDrawable)
        //applyHorizontalPaddingToRootViewIfNeeded(quantityPickerViewState)
    }

    private fun applyHorizontalPaddingToRootViewIfNeeded(viewState: QuantityPickerViewState) {
        if (viewState.orientation != HORIZONTAL_ORIENTATION) return
        val horizontalPadding = viewState.getRootViewHorizontalPadding(context)
        setPadding(
            horizontalPadding,
            paddingTop,
            horizontalPadding,
            paddingBottom
        )
    }

    fun setQuantity(quantity: Int) =
        setQuantityPickerViewState(binding.viewState?.getWithQuantity(quantity))

    fun incrementQuantityBy(quantity: Int) {
        val viewState = binding.viewState ?: return
        val updatedViewState = viewState.getWithIncrementQuantity(quantity)
        setQuantityPickerViewState(updatedViewState)
    }

    fun setMaxQuantity(maxQuantity: Int) =
        setQuantityPickerViewState(binding.viewState?.getWithMaxQuantity(maxQuantity))

    fun setMinQuantity(minQuantity: Int) =
        setQuantityPickerViewState(binding.viewState?.getWithMinQuantity(minQuantity))

    fun stopLoading() = setQuantityPickerViewState(binding.viewState?.stopLoading())

    fun reset() = setQuantityPickerViewState(binding.viewState?.reset())

    // region vertical constraints
    private fun applyVerticalConstraintsIfNeeded() {
        if (binding.viewState?.orientation != VERTICAL_ORIENTATION) return

        val verticalConstraintLayout = ConstraintLayout(context)
        val bindingVertical = DataBindingUtil.inflate<ViewQuantityPickerVerticalBinding>(
            LayoutInflater.from(context),
            R.layout.view_quantity_picker_vertical,
            verticalConstraintLayout,
            true
        )
        bindingVertical.viewState = this.binding.viewState
        bindingVertical.executePendingBindings()
        ConstraintSet().apply {
            clone(verticalConstraintLayout)
            applyTo(this@QuantityPickerView)
        }

        applyVerticalProperties()
    }

    private fun applyVerticalProperties() {
        binding.text.minEms = 0
        rotateQuantityBackgroundPadding()
    }

    private fun rotateQuantityBackgroundPadding() {
        with(binding.imageQuantityBackground) {
            setPadding(
                paddingTop /*left*/,
                paddingLeft /*top*/,
                paddingBottom /*right*/,
                paddingLeft /*bottom*/
            )
        }
    }
    // endregion vertical constraints

    // region readAttrs
    private fun readAttributes(attrs: AttributeSet?, defStyleAttr: Int): QuantityPickerViewState {
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
                it.getDimensionPixelSize(
                    R.styleable.QuantityPickerView_qpv_textSize,
                    context.asSP(12)
                )
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
            val maxQuantity = it.getInt(R.styleable.QuantityPickerView_qpv_maxQuantity, -1)
            val minQuantity = it.getInt(R.styleable.QuantityPickerView_qpv_minQuantity, -1)
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
            val disabledAddIcon = it.getDrawable(R.styleable.QuantityPickerView_qpv_disabledAddIcon)
                ?: AppCompatResources.getDrawable(context, R.drawable.qpv_ic_default_add)!!
            val subtractIcon = it.getDrawable(R.styleable.QuantityPickerView_qpv_subtractIcon)
                ?: AppCompatResources.getDrawable(context, R.drawable.qpv_ic_default_subtract)!!
            val disabledSubtractIcon = it.getDrawable(R.styleable.QuantityPickerView_qpv_disabledSubtractIcon)
                ?: AppCompatResources.getDrawable(context, R.drawable.qpv_ic_default_subtract)!!
            val quantityBackground =
                it.getDrawable(R.styleable.QuantityPickerView_qpv_quantityBackground)
                    ?: AppCompatResources.getDrawable(context, android.R.color.transparent)!!

            val collapsible = it.getBoolean(R.styleable.QuantityPickerView_qpv_collapsible, false)

            val expansionState =
                if (collapsible) ExpansionState.Collapsible(expanded = currentQuantity > 0) else ExpansionState.NonCollapsible

            val orientation =
                it.getInt(R.styleable.QuantityPickerView_qpv_orientation, HORIZONTAL_ORIENTATION)

            val buttonHorizontalPadding = it.getDimensionPixelSize(
                R.styleable.QuantityPickerView_qpv_buttonHorizontalPadding,
                context.asDP(8)
            )
            val buttonVerticalPadding = it.getDimensionPixelSize(
                R.styleable.QuantityPickerView_qpv_buttonVerticalPadding,
                context.asDP(8)
            )
            val progressVerticalPadding = it.getDimensionPixelSize(
                R.styleable.QuantityPickerView_qpv_progressVerticalPadding,
                context.asDP(2)
            )
            val quantityBackgroundVerticalPadding = it.getDimensionPixelSize(
                R.styleable.QuantityPickerView_qpv_quantityBackgroundVerticalPadding,
                context.asDP(2)
            )

            return QuantityPickerViewState(
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
                disabledAddIconDrawable = disabledAddIcon,
                subtractIconDrawable = subtractIcon,
                disabledSubtractIconDrawable = disabledSubtractIcon,
                showLoading = false,
                quantityBackgroundDrawable = quantityBackground,
                expansionState = expansionState,
                orientation = orientation,
                buttonHorizontalPadding = buttonHorizontalPadding,
                buttonVerticalPadding = buttonVerticalPadding,
                progressVerticalPadding = progressVerticalPadding,
                quantityBackgroundVerticalPadding = quantityBackgroundVerticalPadding,
                maxQuantity = maxQuantity,
                minQuantity = minQuantity
            )
        }
    }

    // endregion readAttrs
    companion object {
        const val HORIZONTAL_ORIENTATION = 0
        const val VERTICAL_ORIENTATION = 1
    }
}