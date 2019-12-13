package com.trendyol.uicomponents

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_rating_bar.setOnClickListener {
            startActivity(Intent(this, RatingBarActivity::class.java))
        }
      
        button_dialogs.setOnClickListener {
            startActivity(Intent(this, DialogsActivity::class.java))
        }
      
        button_imageslider.setOnClickListener {
            startActivity(Intent(this, ImageSliderActivity::class.java))
        }
      
        button_phone_number.setOnClickListener {
            startActivity(Intent(this, PhoneNumberActivity::class.java))
        }
    }
}
