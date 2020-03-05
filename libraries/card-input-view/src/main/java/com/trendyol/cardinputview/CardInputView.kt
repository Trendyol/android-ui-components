package com.trendyol.cardinputview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
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

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

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
     * it will immediately sets invalid field backgrounds with [CardInputViewState.errorBackgroundColor].
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
     * it will immediately sets invalid field backgrounds with [CardInputViewState.errorBackgroundColor].
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
     * @param cardLogoUrl, link for logo.
     */
    fun setCardTypeLogoUrl(cardLogoUrl: String) {
        binding.viewState = binding.viewState?.copy(
            cardTypeLogoUrl = cardLogoUrl
        )
        binding.executePendingBindings()
    }

    /**
     *
     * Setter for card's logo.
     *
     * @param cardBankLogoUrl, link for logo.
     */
    fun setCardBankLogoUrl(cardBankLogoUrl: String) {
        binding.viewState = binding.viewState?.copy(
            cardBankLogoUrl = cardBankLogoUrl
        )
        binding.executePendingBindings()
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
