package com.trendyol.uicomponents.phonenumber

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.os.bundleOf
import com.google.android.material.textfield.TextInputEditText
import kotlinx.parcelize.Parcelize

class PhoneNumberTextInputEditText : TextInputEditText {

    var viewState: PhoneNumberTextInputEditTextViewState = PhoneNumberTextInputEditTextViewState(
        maskable = false,
        maskCharacter = '*'
    )

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initializeView()
    }

    private fun initializeView() {
        inputType = InputType.TYPE_CLASS_PHONE
        maxLines = 1
        setSingleLine()
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && (text.isNullOrEmpty() || (isMaskable() && hasMaskCharacter()))) {
                setText(FIRST_CHARACTER_ZERO)
                setSelection(AFTER_ZERO_SELECTION_INDEX)
            } else {
                if (text == null || text.toString() == FIRST_CHARACTER_ZERO) {
                    setText(EMPTY)
                    setSelection(CLEAR_SELECTION_INDEX)
                }
            }
        }

        super.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() {
            private var backspacingFlag = false
            private var editedFlag = false
            private var cursorComplement: Int = 0

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                cursorComplement = s.length - selectionStart
                backspacingFlag = count > after
                changeListeners.forEach { it?.beforeTextChanged(s, start, count, after) }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                changeListeners.forEach { it?.onTextChanged(s, start, before, count) }
            }

            override fun afterTextChanged(s: Editable) {
                val phone = parsePhoneNumber(s.toString())
                if (!editedFlag) {
                    if (backspacingFlag.not()) {
                        when {
                            phone.length >= LENGTH_NINE -> {
                                editedFlag = true
                                val ans = phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + " " + phone.substring(7, 9) + " " + phone.substring(9)
                                setText(ans)
                                text?.let { setSelection(it.length - cursorComplement) }
                                changeListeners.forEach { it?.afterTextChanged(s) }
                            }
                            phone.length >= LENGTH_SEVEN -> {
                                editedFlag = true
                                val ans = phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + " " + phone.substring(7)
                                setText(ans)
                                text?.let { setSelection(it.length - cursorComplement) }
                                changeListeners.forEach { it?.afterTextChanged(s) }
                            }
                            phone.length >= LENGTH_FOUR -> {
                                editedFlag = true
                                val ans = phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4)
                                setText(ans)
                                text?.let { setSelection(it.length - cursorComplement) }
                                changeListeners.forEach { it?.afterTextChanged(s) }
                            }
                        }
                    }
                } else {
                    editedFlag = false
                }
            }
        })
    }

    private fun hasMaskCharacter() = text?.toString()?.contains(viewState.maskCharacter) == true

    fun setMaskCharacter(maskCharacter: Char) {
        viewState = viewState.copy(maskCharacter = maskCharacter)
    }

    fun isMaskable() = viewState.maskable

    fun setMaskable(maskable: Boolean) {
        viewState = viewState.copy(maskable = maskable)
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        when (id) {
            android.R.id.paste -> {
                val clipboardManager =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                clipboardManager?.let {

                    it.primaryClip?.let { clipData ->
                        val text = clipData.getItemAt(0)?.text.toString()
                        val clearedText = readPhoneNumberFromClipboardAsDigitAndTurkishStandart(text)
                        it.setPrimaryClip(ClipData.newPlainText(EMPTY, clearedText))
                    }
                }
            }
        }
        return super.onTextContextMenuItem(id)
    }

    private fun readPhoneNumberFromClipboardAsDigitAndTurkishStandart(text: String): String {
        val digitsText = text.digitsOnly()
        val digitsTextWithoutZeros = digitsText.replaceLeadingZeros()
        val clipboardText = if (digitsTextWithoutZeros.length >= EXPECTED_TURKISH_PHONE_NUMBER_LENGTH) {
            if (digitsTextWithoutZeros.startsWith(TURKISH_NUMBER_PREFIX)) {
                val replacedText = digitsTextWithoutZeros.replace(TURKISH_NUMBER_PREFIX, EMPTY)
                if (replacedText.length >= EXPECTED_TURKISH_PHONE_NUMBER_LENGTH) {
                    replacedText.substring(0, EXPECTED_TURKISH_PHONE_NUMBER_LENGTH)
                } else {
                    replacedText
                }
            } else {
                digitsTextWithoutZeros.substring(0, EXPECTED_TURKISH_PHONE_NUMBER_LENGTH)
            }
        } else {
            digitsTextWithoutZeros
        }
        return if (getText().toString() == FIRST_CHARACTER_ZERO) {
            clipboardText
        } else {
            "${FIRST_CHARACTER_ZERO}$clipboardText"
        }
    }

    private fun parsePhoneNumber(text: String) =
        text.replace("[^\\d${viewState.maskCharacter}]".toRegex(), "")

    fun getPhoneNumber(): String {
        return parsePhoneNumber(text?.toString() ?: "")
    }

    private val changeListeners: MutableList<TextWatcher?> = mutableListOf()

    override fun addTextChangedListener(watcher: TextWatcher?) {
        changeListeners.add(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher?) {
        changeListeners.remove(watcher)
        super.removeTextChangedListener(watcher)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return bundleOf(
            SUPER_STATE_KEY to super.onSaveInstanceState(),
            STATE_KEY to viewState
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            state
                .getParcelable<PhoneNumberTextInputEditTextViewState>(STATE_KEY)
                ?.let { this.viewState = it }

            super.onRestoreInstanceState(state.getParcelable(SUPER_STATE_KEY))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    companion object {
        val SUPER_STATE_KEY = "SUPER_STATE_KEY"
        val STATE_KEY = "STATE_KEY"

        private const val FIRST_CHARACTER_ZERO = "0"
        private const val CLEAR_SELECTION_INDEX = 0
        private const val AFTER_ZERO_SELECTION_INDEX = 1
        private const val LENGTH_NINE = 9
        private const val LENGTH_SEVEN = 7
        private const val LENGTH_FOUR = 4
        private const val EXPECTED_TURKISH_PHONE_NUMBER_LENGTH = 10
        private const val TURKISH_NUMBER_PREFIX = "90"

        private const val EMPTY = ""
        private val regex = Regex("[^0-9]")

        internal fun String.digitsOnly(): String {
            return regex.replace(this, EMPTY)
        }

        internal fun String.replaceLeadingZeros(): String {
            val regex = Regex("^0+(?!$)")
            return regex.replace(this, EMPTY)
        }
    }
}

@Parcelize
data class PhoneNumberTextInputEditTextViewState(
    val maskable: Boolean,
    val maskCharacter: Char
) : Parcelable
