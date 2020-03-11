package com.trendyol.cardinputview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.trendyol.cardinputview.databinding.ViewCardInputBinding
import com.trendyol.cardinputview.formatter.CardNumberFormatterTextWatcher
import com.trendyol.cardinputview.validator.CreditCardValidator

class CardInputView : ConstraintLayout {

    var onCardNumberChanged: ((String) -> Unit)? = null
    var onCvvChanged: ((String) -> Unit)? = null
    var onCvvInfoClicked: (() -> Unit)? = null
    var onCardNumberComplete: ((Boolean) -> Unit)? = null
    var onCvvComplete: ((Boolean) -> Unit)? = null
    var openMonthSelectionListener: (() -> Unit)? = null
    var openYearSelectionListener: (() -> Unit)? = null

    private val binding: ViewCardInputBinding = inflate(R.layout.view_card_input)

    private val validator by lazy { CreditCardValidator() }

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

    init {
        if (isInEditMode) View.inflate(context, R.layout.view_card_input, this)
        setUpView()
    }

    /**
     *
     * Sets the view fields with given [CardInputViewState].
     *
     * @param viewState is [CardInputViewState].
     */
    fun setViewState(viewState: CardInputViewState?) {
        viewState?.run {
            binding.viewState = this
            binding.executePendingBindings()
        }
    }

    /**
     *
     * Validates all card fields and if fields are not valid,
     * it will immediately sets invalid field backgrounds with [CardInputViewState.inputErrorBackgroundDrawable].
     *
     * @return [Boolean.true] if all fields are valid, [Boolean.false] if one or more field is invalid.
     */
    fun validate(): Boolean {
        val cardInformation = binding.viewState?.cardInformation ?: CardInformation()

        val isCardNumberValid = validator.isCardNumberValid(cardInformation.cardNumber)
        val isExpiryMonthValid = validator.isExpiryMonthValid(cardInformation.expiryMonth)
        val isExpiryYearValid = validator.isExpiryYearValid(cardInformation.expiryYear)
        val isCvvValid = validator.isCvvValid(cardInformation.cvv)

        binding.viewState = binding.viewState?.copy(
            cardNumberValid = isCardNumberValid,
            expiryMonthValid = isExpiryMonthValid,
            expiryYearValid = isExpiryYearValid,
            cvvValid = isCvvValid
        )
        binding.executePendingBindings()

        return isCardNumberValid && isExpiryMonthValid && isExpiryYearValid && isCvvValid
    }

    /**
     *
     * Validates all card fields and if fields are not valid,
     * it will immediately sets invalid field backgrounds with [CardInputViewState.inputErrorBackgroundDrawable].
     *
     * @see validate
     *
     * @return [CardInformation] if all fields are valid, if not returns null.
     */
    fun validateAndGet(): CardInformation? =
        if (validate()) binding.viewState?.cardInformation else null

    /**
     *
     * Resets [CardInputViewState] data including [CardInputViewState.cardInformation] and
     * validity data.
     */
    fun reset() {
        binding.viewState = binding.viewState?.reset()
        binding.executePendingBindings()
    }

    /**
     *
     * Card number field will be focused and keyboard will open.
     * It might be useful if you want to keyboard open when page is started.
     */
    fun focusToCardNumberField() {
        binding.editTextCardNumber.showKeyboard()
    }

    /**
     *
     * Cvv field will be focused and keyboard will open.
     * It might be useful to call after [setSelectedYear].
     */
    fun focusToCvvField() {
        binding.editTextCvv.showKeyboard()
    }

    /**
     *
     * Setter for card's expiry month.
     */
    fun setSelectedMonth(selectedMonth: String) {
        binding.viewState?.expiryMonth = selectedMonth
        binding.textViewCardExpiryMonth.text = selectedMonth
    }

    /**
     *
     * Setter for card's expiry year.
     */
    fun setSelectedYear(selectedYear: String) {
        binding.viewState?.expiryYear = selectedYear
        binding.textViewCardExpiryYear.text = selectedYear
    }

    /**
     *
     * Setter for card's type logo.
     *
     * @param cardTypeLogoDrawable, [android.graphics.drawable.Drawable] for logo. If null, will be removed.
     */
    fun setCardTypeLogoDrawable(cardTypeLogoDrawable: Drawable?) {
        binding.viewState = binding.viewState?.copy(
            cardTypeLogoDrawable = cardTypeLogoDrawable
        )
        binding.executePendingBindings()
    }

    /**
     *
     * Setter for card's logo.
     *
     * @param cardBankLogoDrawable, [android.graphics.drawable.Drawable] for logo. If null, will be removed.
     */
    fun setCardBankLogoDrawable(cardBankLogoDrawable: Drawable?) {
        binding.viewState = binding.viewState?.copy(
            cardBankLogoDrawable = cardBankLogoDrawable
        )
        binding.executePendingBindings()
    }

    private fun readAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CardInputView,
            defStyleAttr,
            0
        ).use {
            val cardNumberTitleText =
                it.getString(R.styleable.CardInputView_civ_cardNumberTitle) ?: ""
            val expiryTitle = it.getString(R.styleable.CardInputView_civ_expiryTitle) ?: ""
            val expiryMonthTitle =
                it.getString(R.styleable.CardInputView_civ_expiryMonthTitle) ?: ""
            val expiryYearTitle = it.getString(R.styleable.CardInputView_civ_expiryYearTitle) ?: ""
            val cvvTitle = it.getString(R.styleable.CardInputView_civ_cvvTitle) ?: ""
            val titleTextColor =
                it.getColor(R.styleable.CardInputView_civ_titleTextColor, Color.BLACK)
            val inputTextColor =
                it.getColor(R.styleable.CardInputView_civ_inputTextColor, Color.DKGRAY)
            val cvvInfoColor =
                it.getColor(R.styleable.CardInputView_civ_cvvInfoColor, Color.RED)
            val showCvvInfoButton =
                it.getBoolean(R.styleable.CardInputView_civ_showCvvInfoButton, true)
            val validationEnabled =
                it.getBoolean(R.styleable.CardInputView_civ_validationEnabled, false)
            val inputBackground = it.getDrawable(R.styleable.CardInputView_civ_inputBackground)
                ?: context.drawable(R.drawable.shape_card_input_field_background)
            val inputErrorBackground =
                it.getDrawable(R.styleable.CardInputView_civ_inputErrorBackground)
                    ?: context.drawable(R.drawable.shape_card_input_field_error_background)

            binding.viewState = CardInputViewState(
                cardNumberTitle = cardNumberTitleText,
                expiryTitle = expiryTitle,
                expiryMonthTitle = expiryMonthTitle,
                expiryYearTitle = expiryYearTitle,
                cvvTitle = cvvTitle,
                cvvInfoColor = cvvInfoColor,
                showCvvInfoButton = showCvvInfoButton,
                inputTextColor = inputTextColor,
                titleTextColor = titleTextColor,
                validationEnabled = validationEnabled,
                inputBackgroundDrawable = inputBackground?.constantState?.newDrawable(),
                inputErrorBackgroundDrawable = inputErrorBackground?.constantState?.newDrawable()
            )
            binding.executePendingBindings()
        }
    }

    private fun setUpView() {
        with(binding) {
            with(editTextCardNumber) {
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_NEXT && viewState?.validationEnabled == true) {
                        val isValid = validator.isCardNumberValid(text?.toString())
                        setCardNumberValidity(isValid)
                        onCardNumberComplete?.invoke(isValid)
                    }
                    actionId == EditorInfo.IME_ACTION_NEXT
                }
                addTextChangedListener(CardNumberTextWatcher())
                addTextChangedListener(CardNumberFormatterTextWatcher())
            }
            with(editTextCvv) {
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE && viewState?.validationEnabled == true) {
                        val isValid = validator.isCvvValid(text?.toString())
                        setCardCvvValidity(isValid)
                        onCvvComplete?.invoke(isValid)
                        if (isValid) editTextCvv.hideKeyboard()
                    }
                    actionId == EditorInfo.IME_ACTION_DONE
                }
                addTextChangedListener(CvvTextWatcher())
            }

            textViewCardExpiryMonth.setOnClickListener {
                openMonthSelectionListener?.invoke()
            }
            textViewCardExpiryYear.setOnClickListener {
                openYearSelectionListener?.invoke()
            }
            textViewCvvInfo.setOnClickListener { onCvvInfoClicked?.invoke() }
        }
    }

    private fun setCardNumberValidity(isValid: Boolean) {
        val viewState = binding.viewState
        binding.viewState = viewState?.copy(cardNumberValid = isValid)
    }

    private fun setCardCvvValidity(isValid: Boolean) {
        val viewState = binding.viewState
        binding.viewState = viewState?.copy(cvvValid = isValid)
    }

    internal inner class CardNumberTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            onCardNumberChanged?.invoke(s?.toString() ?: "")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    internal inner class CvvTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            onCvvChanged?.invoke(s?.toString() ?: "")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}
