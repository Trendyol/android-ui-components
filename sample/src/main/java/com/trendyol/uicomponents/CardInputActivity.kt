package com.trendyol.uicomponents

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.cardinput.CardInformation
import com.trendyol.cardinput.CardInputViewState
import com.trendyol.uicomponents.dialogs.infoDialog
import com.trendyol.uicomponents.dialogs.selectionDialog
import kotlinx.android.synthetic.main.activity_card_input.*

class CardInputActivity : AppCompatActivity() {

    private val months =
        listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    private val years = listOf("20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_input)

        val cardInputViewState = CardInputViewState(
            cardNumberTitle = "Card Number",
            expiryTitle = "Expiry Date",
            expiryMonthTitle = "MM",
            expiryYearTitle = "YY",
            cvvTitle = "CVV",
            validationEnabled = true,
            inputTextColor = Color.BLACK
        )

        with(card_input_view) {
            setViewState(cardInputViewState)

            onCardNumberChanged = { cardNumber ->
                if (cardNumber.length == 1) {
                    val cardLogoUrl = getCardTypeLogoUrl(cardNumber)
                    card_input_view.setCardTypeLogoUrl(cardLogoUrl)
                }
                text_card_number.text = cardNumber
            }
            onCvvChanged = { cvv ->
                text_cvv.text = cvv
            }
            onCvvInfoClicked = {
                Toast.makeText(
                    this@CardInputActivity,
                    "CVV number is on the back of your card.",
                    Toast.LENGTH_LONG
                ).show()
            }
            onCardNumberComplete = { isValid ->
                val color = if (isValid) Color.BLUE else Color.RED
                text_card_number.setTextColor(color)
                showMonthSelectionDialog()
            }
            onCvvComplete = { isValid ->
                val color = if (isValid) Color.BLUE else Color.RED
                text_cvv.setTextColor(color)
                if (isValid) button_validate.performClick()
            }

            openMonthSelectionListener = { showMonthSelectionDialog() }
            openYearSelectionListener = { showYearSelectionDialog() }

            focusToCardNumberField()
        }

        button_validate.setOnClickListener { card_input_view.validate() }
        button_validate_and_get.setOnClickListener {
            val result = card_input_view.validateEndGet()
            if (result != null) showCardInformationDialog(result)
        }
        button_reset.setOnClickListener { card_input_view.reset() }
    }

    private fun showCardInformationDialog(cardInformation: CardInformation) {
        infoDialog {
            title = "CardInformation"
            content = "CardNumber: ${cardInformation.cardNumber}\n\n" +
                    "Expiry: ${cardInformation.expiryMonth}/${cardInformation.expiryYear}\n\n" +
                    "Cvv: ${cardInformation.cvv}"
            showCloseButton = true
            closeButtonListener = { card_input_view.reset() }
        }.showDialog(supportFragmentManager)
    }

    private fun showMonthSelectionDialog() {
        selectionDialog {
            title = "Select Card Expiry Month"
            items = months.map { false to it }
            onItemSelectedListener = { dialog, position ->
                dialog.dismiss()
                card_input_view.setSelectedMonth(months[position])
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
                card_input_view.setSelectedYear(years[position])
                card_input_view.focusToCvvField()
            }
        }.showDialog(supportFragmentManager)
    }

    private fun getCardTypeLogoUrl(firstNumber: String): String = when {
        firstNumber.startsWith("4") ->
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Visa_2014_logo_detail.svg/320px-Visa_2014_logo_detail.svg.png"
        firstNumber.startsWith("5") ->
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Mastercard_2019_logo.svg/320px-Mastercard_2019_logo.svg.png"
        else -> ""
    }
}
