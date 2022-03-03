package com.trendyol.cardinputview.formatter

import android.text.Editable
import android.text.TextWatcher
import com.trendyol.cardinputview.CreditCardType
import com.trendyol.cardinputview.updateText
import java.util.concurrent.atomic.AtomicBoolean

internal class CardNumberFormatterTextWatcher(
    private val supportedCardTypes: List<CreditCardType>,
) : TextWatcher {

    private var editing: AtomicBoolean = AtomicBoolean(false)
    private val creditCardMasker = CreditCardMasker()

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (editing.get()) return
        editing.set(true)

        val maskedCardNumber = creditCardMasker.mask(s.toString(), supportedCardTypes)
        s.updateText(maskedCardNumber)

        editing.set(false)
    }
}
