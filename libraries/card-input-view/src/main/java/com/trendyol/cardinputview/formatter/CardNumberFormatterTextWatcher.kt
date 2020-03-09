package com.trendyol.cardinputview.formatter

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher

/**
 *
 * @see <a href="https://gist.github.com/hleinone/5b445e5475ca9f8a3bdc6a44998f4edd">hleinone gist</a>
 */
internal class CardNumberFormatterTextWatcher : TextWatcher {

    private var current = ""

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (s.toString() != current) {
            val userInput = s.toString().replace(nonDigits, "")
            if (userInput.length <= 16) {
                current = userInput.chunked(4).joinToString(" ")
                s.filters = arrayOfNulls<InputFilter>(0)
            }
            s.replace(0, s.length, current, 0, current.length)
        }
    }

    companion object {
        private val nonDigits = Regex("[^\\d]")
    }
}
