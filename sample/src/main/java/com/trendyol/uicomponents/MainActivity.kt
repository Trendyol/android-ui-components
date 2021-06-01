package com.trendyol.uicomponents

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val buttonRatingBar by lazy { findViewById<Button>(R.id.button_rating_bar) }
    private val buttonDialogs by lazy { findViewById<Button>(R.id.button_dialogs) }
    private val buttonImageslider by lazy { findViewById<Button>(R.id.button_imageslider) }
    private val buttonPhoneNumber by lazy { findViewById<Button>(R.id.button_phone_number) }
    private val buttonToolbar by lazy { findViewById<Button>(R.id.button_toolbar) }
    private val buttonSuggestionInputView by lazy { findViewById<Button>(R.id.button_suggestion_input_view) }
    private val buttonCardInput by lazy { findViewById<Button>(R.id.button_card_input) }
    private val buttonQuantityPicker by lazy { findViewById<Button>(R.id.button_quantity_picker) }
    private val buttonTimelineView by lazy { findViewById<Button>(R.id.button_timeline_view) }
    private val buttonFitOptionMessageView by lazy { findViewById<Button>(R.id.button_fit_option_message_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRatingBar.setOnClickListener {
            startActivity(Intent(this, RatingBarActivity::class.java))
        }
        buttonDialogs.setOnClickListener {
            startActivity(Intent(this, DialogsActivity::class.java))
        }
        buttonImageslider.setOnClickListener {
            startActivity(Intent(this, ImageSliderActivity::class.java))
        }
        buttonPhoneNumber.setOnClickListener {
            startActivity(Intent(this, PhoneNumberActivity::class.java))
        }
        buttonToolbar.setOnClickListener {
            startActivity(Intent(this, ToolbarActivity::class.java))
        }
        buttonSuggestionInputView.setOnClickListener {
            startActivity(Intent(this, SuggestionInputViewActivity::class.java))
        }
        buttonCardInput.setOnClickListener {
            startActivity(Intent(this, CardInputViewActivity::class.java))
        }
        buttonQuantityPicker.setOnClickListener {
            startActivity(Intent(this, QuantityPickerViewActivity::class.java))
        }
        buttonTimelineView.setOnClickListener {
            startActivity(Intent(this, TimelineViewActivity::class.java))
        }
        buttonFitOptionMessageView.setOnClickListener {
            startActivity(Intent(this, FitOptionMessageViewActivity::class.java))
        }
    }
}
