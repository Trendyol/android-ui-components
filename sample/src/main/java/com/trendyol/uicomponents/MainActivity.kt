package com.trendyol.uicomponents

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button_rating_bar).setOnClickListener {
            startActivity(Intent(this, RatingBarActivity::class.java))
        }
    }
}
