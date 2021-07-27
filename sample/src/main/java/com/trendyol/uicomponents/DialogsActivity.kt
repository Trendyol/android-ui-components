package com.trendyol.uicomponents

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.color
import com.trendyol.uicomponents.dialogs.*

class DialogsActivity : AppCompatActivity() {

    private val buttonInfoDialog by lazy { findViewById<Button>(R.id.button_info_dialog) }
    private val buttonAgreementDialog by lazy { findViewById<Button>(R.id.button_agreement_dialog) }
    private val buttonSelectionDialog by lazy { findViewById<Button>(R.id.button_selection_dialog) }
    private val buttonSelectionWithSearchDialog by lazy { findViewById<Button>(R.id.button_selection_with_search_dialog) }
    private val buttonInfoDialogWithWebView by lazy { findViewById<Button>(R.id.button_info_dialog_webview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)

        buttonInfoDialog.setOnClickListener { showInfoDialog() }
        buttonAgreementDialog.setOnClickListener { showAgreementDialog() }
        buttonSelectionDialog.setOnClickListener { showSelectionDialog() }
        buttonSelectionWithSearchDialog.setOnClickListener { showSelectionWithSearchDialog() }
        buttonInfoDialogWithWebView.setOnClickListener { showInfoDialogWithWebView() }
    }

    private val infoDialogClosed: (DialogFragment) -> Unit = { showToast("Info dialog closed.") }

    private val rightButtonClickListener: (DialogFragment) -> Unit = {
        it.dismiss()
        showToast("Right button clicked.")
    }

    private val leftButtonClickListener: (DialogFragment) -> Unit = {
        it.dismiss()
        showToast("Left buttonClicked")
    }

    private val onItemSelectedListener: (DialogFragment, Int) -> Unit =
        { dialogFragment, position ->
            showToast("Selection changed to ${position + 1}th ")
        }

    private val onItemReselectedListener: (DialogFragment, Int) -> Unit =
        { dialogFragment, position ->
            showToast("Reselection ${position + 1}th ")
        }

    private fun showInfoDialog() {
        infoDialog {
            title = "Info Dialog Sample"
            showCloseButton = true
            closeButtonListener = infoDialogClosed
            content = SpannableString.valueOf(getSpannableString())
            contentImage = R.mipmap.ic_launcher_round
            titleTextColor = R.color.civ_error_stroke
            titleBackgroundColor = R.color.civ_error_background
            titleTextPosition = TextPosition.CENTER
            contentTextPosition = TextPosition.END
        }.showDialog(supportFragmentManager)
    }

    private fun showAgreementDialog() {
        val agreementDialog = agreementDialog {
            title = "Agreement Dialog Sample"
            leftButtonText = "Cancel"
            rightButtonText = "Agree"
            content = getHtmlString()
            showContentAsHtml = true
            rightButtonClickListener = this@DialogsActivity.rightButtonClickListener
            leftButtonClickListener = this@DialogsActivity.leftButtonClickListener
        }

        agreementDialog.showDialog(supportFragmentManager)
    }

    private fun showSelectionDialog() {
        val selectionDialog = selectionDialog {
            title = "Selection Dialog Sample"
            content = getHtmlString()
            showContentAsHtml = true
            showCloseButton = false
            contentImage = android.R.drawable.ic_dialog_email
            items = getListItems()
            showItemsAsHtml = false
            onItemSelectedListener = this@DialogsActivity.onItemSelectedListener
            onItemReselectedListener = this@DialogsActivity.onItemReselectedListener
            selectedItemDrawable = R.drawable.ic_check
            selectedTextColor = R.color.colorPrimary
            showRadioButton = true
        }

        selectionDialog.showDialog(supportFragmentManager)
    }

    private fun showSelectionWithSearchDialog() {
        selectionDialog {
            title = "Selection Dialog with Search Sample"
            content = getHtmlString()
            showContentAsHtml = true
            showCloseButton = false
            contentImage = android.R.drawable.ic_dialog_email
            items = getListItems()
            onItemSelectedListener = this@DialogsActivity.onItemSelectedListener
            onItemReselectedListener = this@DialogsActivity.onItemReselectedListener
            enableSearch = true
            showClearSearchButton = true
            searchHint = "Hint for search"
            selectedItemDrawable = R.drawable.ic_check
            selectedTextColor = R.color.colorPrimary
        }.showDialog(supportFragmentManager)
    }

    private fun showInfoDialogWithWebView() {
        infoDialog {
            title = "Info Dialog Sample"
            showCloseButton = true
            closeButtonListener = infoDialogClosed
            webViewContent = WebViewContent.UrlContent("https://github.com/Trendyol/android-ui-components")
            webViewBuilder = {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
            }
        }.showDialog(supportFragmentManager)


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setDialogFragmentListenersIfNecessary()
    }

    private fun setDialogFragmentListenersIfNecessary() =
        DialogFragment
            .findFragment(supportFragmentManager)
            ?.apply {
                rightButtonClickListener = this@DialogsActivity.rightButtonClickListener
                leftButtonClickListener = this@DialogsActivity.leftButtonClickListener
                onItemSelectedListener = this@DialogsActivity.onItemSelectedListener
                onItemReselectedListener = this@DialogsActivity.onItemReselectedListener
                closeButtonListener = this@DialogsActivity.infoDialogClosed
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
            .append("trendyol@trendyol.com")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append("trendyol.com")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append(" Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .append("+905066979797")




    private fun getHtmlString(): String =
        "<h1>Enter the main heading, usually the same as the title.</h1>\n" +
            "<p>Be <b>bold</b> in stating your key points. Put them in a list: </p>\n" +
            "<ul>\n" +
            "<li>The first item in your list</li>\n" +
            "<li>The second item; <i>italicize</i> key words</li>\n" +
            "<a href=\"https://www.trendyol.com/\">Visit Trendyol!</a>" +
            "</ul>"

    private fun getListItems(): List<Pair<Boolean, String>> =
        mutableListOf<Pair<Boolean, String>>().apply {
            for (i in 1..15) {
                add((i == 6) to "${i}th option")
            }
        }.toList()

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
