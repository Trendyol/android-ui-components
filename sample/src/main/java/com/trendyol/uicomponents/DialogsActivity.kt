package com.trendyol.uicomponents

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.color
import com.trendyol.uicomponents.dialogs.agreementDialog
import com.trendyol.uicomponents.dialogs.infoDialog
import kotlinx.android.synthetic.main.activity_dialogs.*

class DialogsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)

        button_info_dialog.setOnClickListener { showInfoDialog() }
        button_agreement_dialog.setOnClickListener { showAgreementDialog() }
    }

    private fun showAgreementDialog() {
        agreementDialog {
            title = "Agreement"
            leftButtonText = "Cancel"
            rightButtonText = "Agree"
            content = SpannableString.valueOf(getSpannableString())
            rightButtonClickListener = {
                it.dismiss()
                showToast("Right button clicked.")
            }
            leftButtonClickListener = {
                it.dismiss()
                showToast("Left buttonClicked")
            }
        }.showDialog(supportFragmentManager)
    }

    private fun showInfoDialog() {
        infoDialog {
            title = "hebele Hubele"
            showCloseButton = true
            closeButtonListener = { showToast("Info dailog closed.") }
            content = SpannableString.valueOf(getSpannableString())
            contentImage = android.R.drawable.btn_plus
        }.showDialog(supportFragmentManager)
    }

    private fun getSpannableString(): SpannableStringBuilder =
        SpannableStringBuilder("Lorem ipsum dolor sit amet,")
            .bold {
                append(" consectetur adipiscing elit,")
            }
            .color(R.color.colorPrimary) {
                append(" sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
            }
            .append(" Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
            .bold {
                append(" Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.")
            }
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
