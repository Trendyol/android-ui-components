package com.trendyol.uicomponents

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.trendyol.cardinputview.CardInformation
import com.trendyol.cardinputview.CreditCardType
import com.trendyol.uicomponents.databinding.ActivityCardInputBinding
import com.trendyol.uicomponents.dialogs.infoDialog
import com.trendyol.uicomponents.dialogs.selectionDialog
import java.util.Calendar

class CardInputViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardInputBinding
    private val months =
        listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    private val years by lazy {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).mod(100)
        currentYear.rangeTo(currentYear + 10).toList().map { it.toString() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.cardInputView) {
            setSupportedCardTypes(
                CreditCardType.MASTER_CARD,
                CreditCardType.VISA,
                CreditCardType.AMERICAN_EXPRESS
            )
            onCardNumberChanged = { cardNumber ->
                if (cardNumber.length <= 1) {
                    binding.cardInputView.setCardTypeLogoDrawable(getCardTypeLogoUrl(cardNumber))
                }
                if (cardNumber.length >= 6) {
                    binding.cardInputView.setCardBankLogoDrawable(getCardTypeLogoUrl(cardNumber)) // dummy setting
                }
                binding.textCardNumber.text = cardNumber
            }
            onCvvChanged = { cvv ->
                binding.textCvv.text = cvv
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
                binding.textCardNumber.setTextColor(color)
                showMonthSelectionDialog()
            }
            onCvvComplete = { isValid ->
                val color = if (isValid) Color.BLUE else Color.RED
                binding.textCvv.setTextColor(color)
                if (isValid) binding.buttonValidate.performClick()
            }

            cardNumberInputErrorListener = {
                binding.cardInputView.postDelayed({
                    binding.cardInputView.clearErrors()
                }, 5000)
            }

            openMonthSelectionListener = { showMonthSelectionDialog() }
            openYearSelectionListener = { showYearSelectionDialog() }

            focusToCardNumberField()
        }

        binding.buttonValidate.setOnClickListener { binding.cardInputView.validate() }
        binding.buttonValidateAndGet.setOnClickListener {
            val result = binding.cardInputView.validateAndGet()
            if (result != null) showCardInformationDialog(result)
        }
        binding.buttonReset.setOnClickListener { binding.cardInputView.reset() }
        binding.buttonClearErrors.setOnClickListener { binding.cardInputView.clearErrors() }
    }

    private fun showCardInformationDialog(cardInformation: CardInformation) {
        infoDialog {
            title = "CardInformation"
            content = "CardNumber: ${cardInformation.cardNumber}\n\n" +
                "Expiry: ${cardInformation.expiryMonth}/${cardInformation.expiryYear}\n\n" +
                "Cvv: ${cardInformation.cvv}"
            showCloseButton = true
            closeButtonListener = { binding.cardInputView.reset() }
        }.showDialog(supportFragmentManager)
    }

    private fun showMonthSelectionDialog() {
        selectionDialog {
            title = "Select Card Expiry Month"
            items = months.map { false to it }
            onItemSelectedListener = { dialog, position ->
                dialog.dismiss()
                binding.cardInputView.setSelectedMonth(months[position])
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
                binding.cardInputView.setSelectedYear(years[position])
                binding.cardInputView.focusToCvvField()
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
