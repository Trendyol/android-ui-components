package com.trendyol.suggestioninputview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.animation.AnticipateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintSet
import com.trendyol.suggestioninputview.databinding.ViewSuggestionInputBinding
import com.trendyol.suggestioninputview.databinding.ViewSuggestionSelectablesBinding

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

    private var shouldShowSelectableItemError: Boolean = false

    private var shouldShowInputItemError: Boolean = false

    private var rules: List<Rule>? = null

    private var inputErrorMessage: String = ""

    private var hint: String = ""

    private var showKeyboardByDefault: Boolean = true

    private var canDeselectItem: Boolean = false

    private val bindingSelectables: ViewSuggestionSelectablesBinding =
        inflate(R.layout.view_suggestion_selectables)
    private val bindingInput: ViewSuggestionInputBinding =
        inflate(R.layout.view_suggestion_input, false)

    private var onSuggestionItemClickListener: ((SuggestionInputItem) -> Unit)? = null

    private var currentSelectedItem: SuggestionInputItem? = null

    private val itemsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SuggestionItemAdapter()
    }

    private val vibrator: Vibrator by lazy(LazyThreadSafetyMode.NONE) {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
                hint = getString(R.styleable.SuggestionInputView_inputHint) ?: ""
                showKeyboardByDefault =
                    getBoolean(R.styleable.SuggestionInputView_showKeyboardByDefault, true)
                canDeselectItem = getBoolean(R.styleable.SuggestionInputView_canDeselectItem, false)
            } finally {
                recycle()
            }

            itemsAdapter.setSuggestionItemClickListener { onSuggestionItemClicked(it) }
            bindingSelectables.buttonDone.setOnClickListener { onDoneClicked() }
            bindingSelectables.imageViewBack.setOnClickListener {
                setSelectionToCurrentSelection(true)
                showSelectableView()
            }
            bindingSelectables.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    clearInputError()
                    val selectedValue = bindingSelectables.editText.text.toString()
                    val validation = RuleValidator.validate(rules, selectedValue)
                    when {
                        validation.first -> setSelection(false)
                        selectedValue.isNotEmpty() -> {
                            showInputError(validation.second)
                            resetSelection()
                        }
                        else -> {

                        }
                    }
                }
            })
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

    fun setInputSuffix(suffix: String?) {
        this.inputSuffix = suffix ?: ""
        setViewState(createViewState())
    }

    fun setInputHint(hint: String?) {
        this.hint = hint ?: ""
        setViewState(createViewState())
    }

    fun setItems(items: List<SuggestionInputItem>) {
        this.items = mapInputItemsToItemViewState(items)
        setViewState(createViewState())
        setInitializeSelection()
    }

    fun shouldShowSelectableItemError(shouldShowError: Boolean) {
        this.shouldShowSelectableItemError = shouldShowError
        notifyErrorToItems()
        notifyAdapter()
    }

    fun setSuggestionItemClickListener(function: (SuggestionInputItem) -> (Unit)) {
        this.onSuggestionItemClickListener = function
    }

    fun setRuleSet(rules: List<Rule>) {
        this.rules = rules
    }

    private fun onSuggestionItemClicked(suggestionInputItemViewState: SuggestionInputItemViewState) {
        renderSelection(suggestionInputItemViewState)
    }

    private fun setSelectionToSelectableItem(
        selectableItem: SuggestionInputItemViewState,
        fromBack: Boolean = false
    ) {
        setSelectionToSuggestionItem(selectableItem, fromBack)
       // onSuggestionItemClickListener?.invoke(mapItemViewStateToInputItem(selectableItem))
    }

    private fun onDoneClicked() {
        val selectedValue = bindingSelectables.editText.text.toString()
        val validation = RuleValidator.validate(rules, selectedValue)
        if (validation.first) {
            currentSelectedItem = mapFreeTextToInputItem()
            setSelection(true)
            showSelectableView()
        } else {
            showInputError(validation.second)
        }
    }

    private fun setSelection(shouldSelect: Boolean) {
        if (isSelectableItemsContainsTo(bindingSelectables.editText.text.toString())) {
            setSelectionToSelectableItem(getSelectableItem(bindingSelectables.editText.text.toString()), shouldSelect)
        } else {
            val inputItem = mapFreeTextToInputItem()
            if (shouldSelect) setSelectionToInput(inputItem)
            onSuggestionItemClickListener?.invoke(inputItem)
        }
    }

    private fun resetSelection() {
        val updatedItems = mutableListOf<SuggestionInputItemViewState>()
        items.forEach { item ->
            updatedItems.add(item.copy(isSelected = false))
        }
        items = updatedItems
        notifyAdapter()
        onSuggestionItemClickListener?.invoke(
            SuggestionInputItem(
                0,
                "",
                "",
                false,
                SuggestionItemType.INPUT,
                ""
            )
        )
    }

    private fun setSelectionToCurrentSelection(fromBack: Boolean = false) {
        val currentItemValue = currentSelectedItem?.value
        if (isSelectableItemsContainsTo(currentItemValue)) {
            setSelectionToSelectableItem(getSelectableItem(currentItemValue ?: ""), fromBack)
        } else {
            if (currentSelectedItem == null) {
                resetSelection()
            } else {
                setSelectionToInput(currentSelectedItem!!)
                onSuggestionItemClickListener?.invoke(currentSelectedItem!!)
            }
        }
    }

    private fun isSelectableItemsContainsTo(value: String?): Boolean {
        items.forEach { item ->
            if (item.value == value) {
                return true
            }
        }
        return false
    }

    private fun getSelectableItem(value: String): SuggestionInputItemViewState {
        return items.find { it.value == value }!!
    }

    private fun showInputError(rule: Rule?) {
        vibrate()
        this.inputErrorMessage = rule?.errorMessage ?: ""
        this.shouldShowInputItemError = true
        setViewState(createViewState())
    }

    private fun clearInputError() {
        this.shouldShowInputItemError = false
        setViewState(createViewState())
    }

    private fun setInitializeSelection() {
        val selectedItem: SuggestionInputItemViewState? = items.firstOrNull { item ->
            item.isSelected
        }

        if (selectedItem != null) {
            renderSelection(selectedItem, showKeyboardByDefault)
        }
    }

    private fun setSelectionToSuggestionItem(
        suggestionInputItemViewState: SuggestionInputItemViewState,
        fromBack: Boolean
    ) {
        val updatedItems = mutableListOf<SuggestionInputItemViewState>()
        items.forEach { item ->
            updatedItems.add(
                item.copy(
                    isSelected = getSelectedStatus(
                        suggestionInputItemViewState,
                        item,
                        fromBack
                    )
                )
            )
        }
        items = updatedItems
        onSuggestionItemClickListener?.invoke(mapItemViewStateToInputItem(items.first { it.text == suggestionInputItemViewState.text }))
        notifyAdapter()
    }

    private fun getSelectedStatus(
        suggestionInputItemViewState: SuggestionInputItemViewState,
        item: SuggestionInputItemViewState,
        fromBack: Boolean
    ): Boolean {

        return if (canDeselectItem) {
            if (item.text == suggestionInputItemViewState.text.trim() && fromBack.not()) {
                if (item.isSelected && item.type == SuggestionItemType.SELECTABLE) {
                    currentSelectedItem = null
                }
                item.isSelected.not()
            } else {
                item.text == suggestionInputItemViewState.text.trim()
            }
        } else {
            item.text == suggestionInputItemViewState.text.trim()
        }
    }

    private fun setSelectionToInput(suggestionInputItem: SuggestionInputItem) {
        val updatedItems = mutableListOf<SuggestionInputItemViewState>()
        items.forEach { item ->
            if (item.type == SuggestionItemType.INPUT && suggestionInputItem.value.trim()
                    .isNotEmpty()
            ) {
                updatedItems.add(
                    item.copy(
                        isSelected = true,
                        text = suggestionInputItem.text,
                        value = suggestionInputItem.value
                    )
                )
            } else {
                updatedItems.add(
                    item.copy(
                        isSelected = isSelectableItemSelected(item, suggestionInputItem),
                        text = item.text
                    )
                )
            }
        }

        items = updatedItems
        notifyAdapter()
    }

    private fun isSelectableItemSelected(
        suggestionInputItemViewState: SuggestionInputItemViewState,
        suggestionInputItem: SuggestionInputItem
    ): Boolean =
        suggestionInputItemViewState.isSelected && suggestionInputItem.value.trim().isEmpty()

    private fun showInputView(showKeyboard: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(bindingInput.constraintLayoutInput)
        setTransition()
        applyConstraintSet(constraintSet)
        postDelayed({
            val inputText = getInputText()
            bindingSelectables.editText.setText(inputText)
            bindingSelectables.editText.setSelection(inputText.length)
            if (showKeyboard) {
                bindingSelectables.editText.requestFocus()
                showKeyboard()
            }
        }, 500)
    }

    private fun getInputText(): String {
        var inputText = ""
        items.forEach { item ->
            if (item.type == SuggestionItemType.INPUT) {
                inputText = item.value.replace(inputSuffix, "")
            }
        }
        return inputText
    }

    private fun renderSelection(
        suggestionInputItemViewState: SuggestionInputItemViewState,
        showKeyboard: Boolean = true
    ) {
        shouldShowSelectableItemError(false)
        val itemType = suggestionInputItemViewState.type
        if (itemType == SuggestionItemType.SELECTABLE) {
            currentSelectedItem = mapItemViewStateToInputItem(suggestionInputItemViewState)
            setSelectionToSelectableItem(suggestionInputItemViewState)
        } else {
            showInputView(showKeyboard)
        }
    }

    private fun showSelectableView() {
        clearInputError()
        val constraintSet = ConstraintSet()
        constraintSet.clone(context, R.layout.view_suggestion_selectables)
        setTransition()
        applyConstraintSet(constraintSet)
        hideKeyboard()
    }

    private fun setTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val transition = ChangeBounds()
            transition.interpolator = AnticipateInterpolator(2.0f)
            transition.duration = 400
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

    private fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
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
                shouldShowSelectableItemError = shouldShowSelectableItemError
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
        editTextErrorBackground = errorBackground,
        verticalPadding = verticalPadding,
        inputType = inputType,
        suffix = inputSuffix,
        shouldShowInputItemError = shouldShowInputItemError,
        inputErrorMessage = inputErrorMessage,
        hint = hint
    )

    private fun notifyErrorToItems() {
        items = items.map {
            it.copy(shouldShowSelectableItemError = shouldShowSelectableItemError)
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