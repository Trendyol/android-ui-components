package com.trendyol.suggestioninputview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintSet
import com.trendyol.suggestioninputview.databinding.ViewSuggestionSelectablesBinding
import android.view.animation.AnticipateInterpolator
import android.view.inputmethod.EditorInfo
import com.trendyol.suggestioninputview.databinding.ViewSuggestionInputBinding

class SuggestionInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var items: List<SuggestionInputItemViewState> = mutableListOf()

    private var selectedBackground: Drawable? = null

    private var unselectedBackground: Drawable? = null

    private var errorBackground: Drawable? = null

    @ColorInt
    private var selectedTextColor: Int = 0

    @ColorInt
    private var unselectedTextColor: Int = 0

    @Dimension
    private var textSize: Float = 0F

    @Dimension
    private var horizontalPadding: Float = 0F

    @Dimension
    private var verticalPadding: Float = 0F

    @Dimension
    private var minWidth: Float = 0F

    private var inputButtonText: String = ""

    private var inputButtonBackground: Drawable? = null

    @ColorInt
    private var inputButtonTextColor: Int = 0

    private var inputEditTextBackground: Drawable? = null

    private var inputSuffix: String = ""

    private var inputType: Int = 0

    private var shouldShowError: Boolean = false

    private val bindingSelectables: ViewSuggestionSelectablesBinding =
        inflate(R.layout.view_suggestion_selectables)
    private val bindingInput: ViewSuggestionInputBinding =
        inflate(R.layout.view_suggestion_input, false)

    private var onSuggestionItemClickListener: ((SuggestionInputItem) -> Unit)? = null

    private val itemsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SuggestionItemAdapter()
    }

    init {
        bindingSelectables.recyclerViewSuggestionItems.adapter = itemsAdapter
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.SuggestionInputView,
            defStyleAttr,
            0
        )?.apply {
            try {
                selectedBackground = getDrawable(R.styleable.SuggestionInputView_selectedBackground)
                    ?: context.drawable(R.drawable.shape_selected_background_suggestion_item)
                unselectedBackground =
                    getDrawable(R.styleable.SuggestionInputView_unselectedBackground)
                        ?: context.drawable(R.drawable.shape_unselected_background_suggestion_item)
                errorBackground =
                    getDrawable(R.styleable.SuggestionInputView_errorBackground)
                        ?: context.drawable(R.drawable.shape_error_background_suggestion_item)
                selectedTextColor = getColor(
                    R.styleable.SuggestionInputView_selectedTextColor,
                    context.color(R.color.text_color_selected_suggestion_item)
                )
                unselectedTextColor = getColor(
                    R.styleable.SuggestionInputView_unselectedTextColor,
                    context.color(R.color.text_color_unselected_suggestion_item)
                )
                textSize = getDimension(
                    R.styleable.SuggestionInputView_textSize,
                    context.resources.getDimension(R.dimen.text_size_suggestion_input)
                )
                horizontalPadding = getDimension(
                    R.styleable.SuggestionInputView_horizontalPadding,
                    context.resources.getDimension(R.dimen.horizontal_padding_suggestion_item)
                )
                verticalPadding = getDimension(
                    R.styleable.SuggestionInputView_verticalPadding,
                    context.resources.getDimension(R.dimen.vertical_padding_suggestion_item)
                )
                minWidth = getDimension(
                    R.styleable.SuggestionInputView_minWidth,
                    context.resources.getDimension(R.dimen.minWidth_suggestion_item)
                )
                inputButtonText = getString(R.styleable.SuggestionInputView_inputButtonText) ?: ""
                inputButtonBackground =
                    getDrawable(R.styleable.SuggestionInputView_inputButtonBackground)
                        ?: context.drawable(R.drawable.shape_selected_background_suggestion_item)
                inputButtonTextColor = getColor(
                    R.styleable.SuggestionInputView_inputButtonTextColor,
                    context.color(R.color.text_color_selected_suggestion_item)
                )
                inputEditTextBackground =
                    getDrawable(R.styleable.SuggestionInputView_inputEditTextBackground)
                        ?: context.drawable(R.drawable.shape_unselected_background_suggestion_item)
                inputSuffix = getString(R.styleable.SuggestionInputView_inputSuffix) ?: ""
                inputType = getInt(
                    R.styleable.SuggestionInputView_android_inputType,
                    EditorInfo.TYPE_TEXT_VARIATION_NORMAL
                )
            } finally {
                recycle()
            }

            itemsAdapter.setSuggestionItemClickListener { onSuggestionItemClicked(it) }
            bindingSelectables.buttonDone.setOnClickListener { onDoneClicked() }
            bindingSelectables.imageViewBack.setOnClickListener { showSelectableView() }
            setViewState(createViewState())
        }
    }

    fun setSelectedBackground(drawable: Drawable?) {
        this.selectedBackground = drawable
        setViewState(createViewState())
    }

    fun setUnselectedBackground(drawable: Drawable?) {
        this.unselectedBackground = drawable
        setViewState(createViewState())
    }

    fun setSelectedTextColor(@ColorInt color: Int) {
        this.selectedTextColor = color
        setViewState(createViewState())
    }

    fun setUnselectedTextColor(@ColorInt color: Int) {
        this.unselectedTextColor = color
        setViewState(createViewState())
    }

    fun setTextSize(@Dimension textSize: Float) {
        this.textSize = textSize
        setViewState(createViewState())
    }

    fun setHorizontalPadding(@Dimension padding: Float) {
        this.horizontalPadding = padding
        setViewState(createViewState())
    }

    fun setVerticalPadding(@Dimension padding: Float) {
        this.verticalPadding = padding
        setViewState(createViewState())
    }

    fun setMinWidth(@Dimension minWidth: Float) {
        this.minWidth = minWidth
        setViewState(createViewState())
    }

    fun setInputButtonText(text: String) {
        this.inputButtonText = text
        setViewState(createViewState())
    }

    fun setInputButtonBackground(drawable: Drawable?) {
        this.inputButtonBackground = drawable
        setViewState(createViewState())
    }

    fun setInputButtonTextColor(@ColorInt color: Int) {
        this.inputButtonTextColor = color
        setViewState(createViewState())
    }

    fun setInputEditTextBackground(drawable: Drawable?) {
        this.inputEditTextBackground = drawable
        setViewState(createViewState())
    }

    fun setInputSuffix(suffix: String) {
        this.inputSuffix = suffix
        setViewState(createViewState())
    }

    fun setItems(items: List<SuggestionInputItem>) {
        this.items = mapInputItemsToItemViewState(items)
        setViewState(createViewState())
        setInitializeSelection()
    }

    fun shouldShowError(shouldShowError: Boolean) {
        this.shouldShowError = shouldShowError
        notifyErrorToItems()
        notifyAdapter()
    }

    fun setSuggestionItemClickListener(function: (SuggestionInputItem) -> (Unit)) {
        this.onSuggestionItemClickListener = function
    }

    private fun onSuggestionItemClicked(suggestionInputItemViewState: SuggestionInputItemViewState) {
        shouldShowError(false)
        val itemType = suggestionInputItemViewState.type
        if (itemType == SuggestionItemType.SELECTABLE) {
            setSelectionToSuggestionItem(suggestionInputItemViewState)
            onSuggestionItemClickListener?.invoke(
                mapItemViewStateToInputItem(
                    suggestionInputItemViewState
                )
            )
        } else {
            showInputView()
        }
    }

    private fun onDoneClicked() {
        val inputItem = mapFreeTextToInputItem()
        setSelectionToInput(inputItem)
        onSuggestionItemClickListener?.invoke(inputItem)
        showSelectableView()
    }

    private fun setInitializeSelection() {
        val selectedItem: SuggestionInputItemViewState? = items.firstOrNull { item ->
            item.isSelected
        }

        if (selectedItem != null) {
            onSuggestionItemClicked(selectedItem)
        }
    }

    private fun setSelectionToSuggestionItem(suggestionInputItemViewState: SuggestionInputItemViewState) {
        val updatedItems = mutableListOf<SuggestionInputItemViewState>()
        items.forEach { item ->
            updatedItems.add(item.copy(isSelected = item.text == suggestionInputItemViewState.text.trim()))
        }

        items = updatedItems
        notifyAdapter()
    }

    private fun setSelectionToInput(suggestionInputItem: SuggestionInputItem) {
        val updatedItems = mutableListOf<SuggestionInputItemViewState>()
        items.forEach { item ->
            if (item.type == SuggestionItemType.INPUT && suggestionInputItem.text.trim().isNotEmpty()) {
                updatedItems.add(item.copy(isSelected = true, text = suggestionInputItem.text))
            } else {
                updatedItems.add(item.copy(isSelected = false))
            }
        }

        items = updatedItems
        notifyAdapter()
    }

    private fun showInputView() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(bindingInput.constraintLayoutInput)
        setTransition()
        applyConstraintSet(constraintSet)
        postDelayed({
            bindingSelectables.editText.requestFocus()
            showKeyboard()
        }, 500)
    }

    private fun showSelectableView() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(context, R.layout.view_suggestion_selectables)
        setTransition()
        applyConstraintSet(constraintSet)
        hideKeyboard()
    }

    private fun setTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val transition = ChangeBounds()
            transition.interpolator = AnticipateInterpolator(1.0f)
            transition.duration = 700
            TransitionManager.beginDelayedTransition(
                bindingSelectables.constraintLayoutSelectables,
                transition
            )
        }
    }

    private fun applyConstraintSet(constraintSet: ConstraintSet) {
        constraintSet.applyTo(bindingSelectables.constraintLayoutSelectables)
    }

    private fun showKeyboard() {
        bindingSelectables.editText.showKeyboard()
    }

    private fun hideKeyboard() {
        bindingSelectables.editText.hideKeyboard()
    }

    private fun mapInputItemsToItemViewState(items: List<SuggestionInputItem>): List<SuggestionInputItemViewState> {
        return items.map { suggestionInputItem ->
            SuggestionInputItemViewState(
                id = suggestionInputItem.id,
                type = suggestionInputItem.type,
                isSelected = suggestionInputItem.isSelected,
                text = suggestionInputItem.text,
                value = suggestionInputItem.value,
                selectedBackground = selectedBackground,
                unselectedBackground = unselectedBackground,
                selectedTextColor = selectedTextColor,
                unselectedTextColor = unselectedTextColor,
                textSize = textSize,
                horizontalPadding = horizontalPadding,
                verticalPadding = verticalPadding,
                minWidth = minWidth,
                suffix = inputSuffix,
                errorBackground = errorBackground,
                shouldShowError = shouldShowError
            )
        }
    }

    private fun mapItemViewStateToInputItem(itemViewState: SuggestionInputItemViewState): SuggestionInputItem {
        return SuggestionInputItem(
            id = itemViewState.id,
            text = itemViewState.text,
            value = itemViewState.value,
            isSelected = itemViewState.isSelected,
            type = itemViewState.type,
            suffix = inputSuffix
        )
    }

    private fun mapFreeTextToInputItem(): SuggestionInputItem {
        val text: String = bindingSelectables.editText.text.toString() + " " + inputSuffix
        val value = bindingSelectables.editText.text.toString()
        return SuggestionInputItem(
            id = ID_FREE_TEXT,
            text = text,
            value = value,
            isSelected = true,
            type = SuggestionItemType.INPUT,
            suffix = inputSuffix
        )
    }

    private fun setViewState(viewState: SuggestionInputViewState) {
        bindingSelectables.viewState = viewState
        bindingSelectables.executePendingBindings()
    }

    private fun createViewState(): SuggestionInputViewState = SuggestionInputViewState(
        items = items,
        textSize = textSize,
        buttonBackground = inputButtonBackground,
        buttonText = inputButtonText,
        buttonTextColor = inputButtonTextColor,
        editTextBackground = inputEditTextBackground,
        verticalPadding = verticalPadding,
        inputType = inputType,
        suffix = inputSuffix
    )

    private fun notifyErrorToItems() {
        items =  items.map {
            it.copy(shouldShowError = shouldShowError)
        }
    }

    private fun notifyAdapter() {
        bindingSelectables.recyclerViewSuggestionItems.adapter = itemsAdapter
        itemsAdapter.submitList(items)
    }

    companion object {
        const val ID_FREE_TEXT = 192
    }
}