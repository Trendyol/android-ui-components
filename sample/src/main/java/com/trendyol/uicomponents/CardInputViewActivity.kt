package com.trendyol.uicomponents

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.trendyol.cardinputview.CardInformation
import com.trendyol.cardinputview.CardInputView
import com.trendyol.cardinputview.CardInputViewState
import com.trendyol.cardinputview.CreditCardType
import com.trendyol.uicomponents.dialogs.infoDialog
import com.trendyol.uicomponents.dialogs.selectionDialog
import java.util.Calendar

class CardInputViewActivity : AppCompatActivity() {

    private val cardInputView by lazy { findViewById<CardInputView>(R.id.card_input_view) }
    private val textCardNumber by lazy { findViewById<TextView>(R.id.text_card_number) }
    private val textCvv by lazy { findViewById<TextView>(R.id.text_cvv) }
    private val buttonValidate by lazy { findViewById<Button>(R.id.button_validate) }
    private val buttonValidateAndGet by lazy { findViewById<Button>(R.id.button_validate_and_get) }
    private val buttonReset by lazy { findViewById<Button>(R.id.button_reset) }
    private val buttonClearErrors by lazy { findViewById<Button>(R.id.button_clear_errors) }

    private val months =
        listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    private val years by lazy {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).mod(100)
        currentYear.rangeTo(currentYear + 10).toList().map { it.toString() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_input)

        with(cardInputView) {
            setSupportedCardTypes(
                CreditCardType.MASTER_CARD,
                CreditCardType.VISA,
                CreditCardType.AMERICAN_EXPRESS
            )
            onCardNumberChanged = { cardNumber ->
                if (cardNumber.length <= 1) {
                    cardInputView.setCardTypeLogoDrawable(getCardTypeLogoUrl(cardNumber))
                }
                if (cardNumber.length >= 6) {
                    cardInputView.setCardBankLogoDrawable(getCardTypeLogoUrl(cardNumber)) // dummy setting
                }
                textCardNumber.text = cardNumber
            }
            onCvvChanged = { cvv ->
                textCvv.text = cvv
            }
            onCvvInfoClicked = {
                Toast.makeText(
                    this@CardInputViewActivity,
                    "Enter $it digit cvv number",
                    Toast.LENGTH_LONG
                ).show()
            }
            onCardNumberComplete = { isValid ->
                val color = if (isValid) Color.BLUE else Color.RED
                textCardNumber.setTextColor(color)
                showMonthSelectionDialog()
            }
            onCvvComplete = { isValid ->
                val color = if (isValid) Color.BLUE else Color.RED
                textCvv.setTextColor(color)
                if (isValid) buttonValidate.performClick()
            }

            openMonthSelectionListener = { showMonthSelectionDialog() }
            openYearSelectionListener = { showYearSelectionDialog() }

            focusToCardNumberField()
        }

        buttonValidate.setOnClickListener { cardInputView.validate() }
        buttonValidateAndGet.setOnClickListener {
            val result = cardInputView.validateAndGet()
            if (result != null) showCardInformationDialog(result)
        }
        buttonReset.setOnClickListener { cardInputView.reset() }
        buttonClearErrors.setOnClickListener { cardInputView.clearErrors() }
    }

    private fun showCardInformationDialog(cardInformation: CardInformation) {
        infoDialog {
            title = "CardInformation"
            content = "CardNumber: ${cardInformation.cardNumber}\n\n" +
                "Expiry: ${cardInformation.expiryMonth}/${cardInformation.expiryYear}\n\n" +
                "Cvv: ${cardInformation.cvv}"
            showCloseButton = true
            closeButtonListener = { cardInputView.reset() }
        }.showDialog(supportFragmentManager)
    }

    private fun showMonthSelectionDialog() {
        selectionDialog {
            title = "Select Card Expiry Month"
            items = months.map { false to it }
            onItemSelectedListener = { dialog, position ->
                dialog.dismiss()
                cardInputView.setSelectedMonth(months[position])
                showYearSelectionDialog()
            }
        }.showDialog(supportFragmentManager)
    }

    private fun showYearSelectionDialog() {
        selectionDialog {
            title = "Select Card Expiry Year"
            items = years.map { false to it }
            onItemSelectedListener = { dialog, position ->
                dialog.dismiss()
                cardInputView.setSelectedYear(years[position])
                cardInputView.focusToCvvField()
            }
        }.showDialog(supportFragmentManager)
    }

    private fun getCardTypeLogoUrl(firstNumber: String): Drawable? = when {
        firstNumber.startsWith("4") ->
            ContextCompat.getDrawable(this, R.drawable.ic_visa_logo)
        firstNumber.startsWith("5") ->
            ContextCompat.getDrawable(this, R.drawable.ic_mc_logo)
        else -> null
    }
}
