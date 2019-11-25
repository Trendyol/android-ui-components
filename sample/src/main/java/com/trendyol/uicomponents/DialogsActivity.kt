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

    private fun showInfoDialog() {
        infoDialog {
            title = "Info Dialog Sample"
            showCloseButton = true
            closeButtonListener = { showToast("Info dailog closed.") }
            content = SpannableString.valueOf(getSpannableString())
            contentImage = R.mipmap.ic_launcher_round
        }.showDialog(supportFragmentManager)
    }

    private fun showAgreementDialog() {
        val dialog = agreementDialog {
            title = "Agreement Dialog Sample"
            leftButtonText = "Cancel"
            rightButtonText = "Agree"
            content = getHtmlString()
            showContentAsHtml = true
            rightButtonClickListener = {
                it.dismiss()
                showToast("Right button clicked.")
            }
            leftButtonClickListener = {
                it.dismiss()
                showToast("Left buttonClicked")
            }
        }

        dialog.showDialog(supportFragmentManager)
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

    private fun getHtmlString(): String =
        "<h1>Enter the main heading, usually the same as the title.</h1>\n" +
                "<p>Be <b>bold</b> in stating your key points. Put them in a list: </p>\n" +
                "<ul>\n" +
                "<li>The first item in your list</li>\n" +
                "<li>The second item; <i>italicize</i> key words</li>\n" +
                "</ul>"

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
