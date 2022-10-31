package com.trendyol.cardinputview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
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
    var onCvvInfoClicked: ((Int) -> Unit)? = null
    var onCardNumberComplete: ((Boolean) -> Unit)? = null
    var onCvvComplete: ((Boolean) -> Unit)? = null
    var openMonthSelectionListener: (() -> Unit)? = null
    var openYearSelectionListener: (() -> Unit)? = null
    var cardNumberInputErrorListener: (() -> Unit)? = null

    private val binding: ViewCardInputBinding = ViewCardInputBinding.inflate(LayoutInflater.from(context), this)
    var viewState: CardInputViewState = CardInputViewState.empty()
        set(value) {
            field = value
            bind()
        }

    private val validator by lazy { CreditCardValidator() }
    private lateinit var cardNumberFormatterTextWatcher: CardNumberFormatterTextWatcher

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
     * Validates all card fields and if fields are not valid,
     * it will immediately sets invalid field backgrounds with [CardInputViewState.inputErrorBackgroundDrawable].
     *
     * @return [Boolean.true] if all fields are valid, [Boolean.false] if one or more field is invalid.
     */
    fun validate(): Boolean {
        val cardInformation = viewState.cardInformation

        val isCardNumberValid = validator.isCardNumberValid(
            cardInformation.cardNumber, viewState.supportedCardTypes
        )
        val isExpiryMonthValid = validator.isExpiryMonthValid(cardInformation.expiryMonth)
        val isExpiryYearValid = validator.isExpiryYearValid(cardInformation.expiryYear)
        val isCvvValid = validator.isCvvValid(
            cardInformation.cvv, cardInformation.cardNumber, viewState.supportedCardTypes
        )

        viewState = viewState.copy(
            cardNumberValid = isCardNumberValid,
            expiryMonthValid = isExpiryMonthValid,
            expiryYearValid = isExpiryYearValid,
            cvvValid = isCvvValid,
            shouldShowErrors = true
        )
        cardNumberInputErrorListener(cardNumberValid = isCardNumberValid)

        return isCardNumberValid && isExpiryMonthValid && isExpiryYearValid && isCvvValid
    }

    private fun cardNumberInputErrorListener(cardNumberValid: Boolean) {
        if (!cardNumberValid) {
            cardNumberInputErrorListener?.invoke()
        }
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
        if (validate()) {
            viewState.cardInformation
        } else {
            null
        }

    /**
     *
     * Resets [CardInputViewState] data including [CardInputViewState.cardInformation] and
     * validity data.
     */
    fun reset() {
        viewState = viewState.reset()
        viewState.cardNumber = ""
        viewState.cvv = ""
    }

    /**
     *
     * Clears all input fields' error states
     * it will immediately sets all fields backgrounds with [CardInputViewState.inputBackgroundDrawable].
     */
    fun clearErrors() {
        viewState = viewState.copy(shouldShowErrors = false)
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
        viewState.expiryMonth = selectedMonth
        bind()
    }

    /**
     *
     * Setter for card's expiry year.
     */
    fun setSelectedYear(selectedYear: String) {
        viewState.expiryYear = selectedYear
        bind()
    }

    /**
     *
     * Setter for card's type logo.
     *
     * @param cardTypeLogoDrawable, [android.graphics.drawable.Drawable] for logo. If null, will be removed.
     */
    fun setCardTypeLogoDrawable(cardTypeLogoDrawable: Drawable?) {
        viewState = viewState.copy(cardTypeLogoDrawable = cardTypeLogoDrawable)
    }

    /**
     *
     * Setter for card's logo.
     *
     * @param cardBankLogoDrawable, [android.graphics.drawable.Drawable] for logo. If null, will be removed.
     */
    fun setCardBankLogoDrawable(cardBankLogoDrawable: Drawable?) {
        viewState = viewState.copy(cardBankLogoDrawable = cardBankLogoDrawable)
    }

    fun setSupportedCardTypes(vararg cardType: CreditCardType) {
        viewState = viewState.copy(supportedCardTypes = cardType.asList())

        binding.editTextCardNumber.removeTextChangedListener(cardNumberFormatterTextWatcher)
        cardNumberFormatterTextWatcher = CardNumberFormatterTextWatcher(cardType.asList())
            .also { watcher -> binding.editTextCardNumber.addTextChangedListener(watcher) }
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

            val viewState = CardInputViewState(
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
            this.viewState = viewState
            cardNumberFormatterTextWatcher = CardNumberFormatterTextWatcher(viewState.supportedCardTypes)
                .also { watcher -> binding.editTextCardNumber.addTextChangedListener(watcher) }
        }
    }

    private fun setUpView() {
        with(binding) {
            with(editTextCardNumber) {
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_NEXT && viewState.validationEnabled) {
                        val isValid =
                            validator.isCardNumberValid(text?.toString(), viewState.supportedCardTypes)
                        setCardNumberValidity(isValid)
                        onCardNumberComplete?.invoke(isValid)
                    }
                    actionId == EditorInfo.IME_ACTION_NEXT
                }
                addTextChangedListener(CardNumberTextWatcher())
            }
            with(editTextCvv) {
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE && viewState.validationEnabled) {
                        val isValid = validator.isCvvValid(
                            text?.toString(),
                            editTextCardNumber.text?.toString(),
                            viewState.supportedCardTypes
                        )
                        setCardCvvValidity(isValid)
                        onCvvComplete?.invoke(isValid)
                        if (isValid) editTextCvv.hideKeyboard()
                    }
                    actionId == EditorInfo.IME_ACTION_DONE
                }
                addTextChangedListener(CvvTextWatcher())
            }

            textViewCardExpiryMonth.setDebouncedOnClickListener {
                openMonthSelectionListener?.invoke()
            }
            textViewCardExpiryYear.setDebouncedOnClickListener {
                openYearSelectionListener?.invoke()
            }
            textViewCvvInfo.setOnClickListener {
                val cvvLength = CreditCardType
                    .getCreditCardType(viewState.supportedCardTypes, editTextCardNumber.text?.toString())
                    .cvvLength
                onCvvInfoClicked?.invoke(cvvLength)
            }
        }
    }

    private fun setCardNumberValidity(isValid: Boolean) {
        viewState = viewState.copy(cardNumberValid = isValid)
        cardNumberInputErrorListener(cardNumberValid = isValid)
    }

    private fun setCardCvvValidity(isValid: Boolean) {
        viewState = viewState.copy(cvvValid = isValid)
    }

    private fun bind() {
        with(binding) {
            with(textViewCardNumberTitle) {
                text = viewState.cardNumberTitle
                setTextColor(viewState.titleTextColor)
            }
            viewCardNumberBackground.background = viewState.cardNumberBackground
            with(editTextCardNumber) {
                if (text.toString() != viewState.cardNumber) {
                    setText(viewState.cardNumber)
                }
                setTextColor(viewState.inputTextColor)
            }
            imageViewBankIcon.setImageDrawable(viewState.cardBankLogoDrawable)
            viewBankIconCardIconDivider.visibility = viewState.dividerVisibility
            imageViewCardIcon.setImageDrawable(viewState.cardTypeLogoDrawable)
            with(textViewExpiryTitle) {
                text = viewState.expiryTitle
                setTextColor(viewState.titleTextColor)
            }
            with(textViewCardExpiryMonth) {
                background = viewState.expiryMonthBackground
                text = viewState.expiryMonth
                setTextColor(viewState.inputTextColor)
            }
            with(textViewCardExpiryYear) {
                background = viewState.expiryYearBackground
                text = viewState.expiryYear
                setTextColor(viewState.inputTextColor)
            }
            with(textViewCvvTitle) {
                text = viewState.cvvTitle
                setTextColor(viewState.titleTextColor)
            }
            with(editTextCvv) {
                if (text.toString() != viewState.cvv) {
                    setText(viewState.cvv)
                }
                background = viewState.cvvBackground
                setTextColor(viewState.inputTextColor)
            }
            with(textViewCvvInfo) {
                setTextColor(viewState.cvvInfoColor)
                visibility = viewState.cvvInfoButtonVisibility
            }
        }
    }

    internal inner class CardNumberTextWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            viewState.cardNumber = s?.toString() ?: ""
            onCardNumberChanged?.invoke(viewState.cardNumber)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    internal inner class CvvTextWatcher : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            viewState.cvv = s?.toString() ?: ""
            onCvvChanged?.invoke(viewState.cvv)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}
