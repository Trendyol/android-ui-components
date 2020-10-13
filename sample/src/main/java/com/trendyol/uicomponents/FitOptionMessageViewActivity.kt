package com.trendyol.uicomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.trendyol.uicomponents.databinding.ActivityFitOptionMessageBinding

class FitOptionMessageViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFitOptionMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_fit_option_message
        )

        // setting a custom animation
        /*binding.fitOptionMessage.revealAnimationProvider = { imageView, textView ->
            imageView.alpha = 0f
            textView.alpha = 0f
            textView.animate().alpha(1f).setDuration(1000).start()
            imageView.animate().alpha(1f).setDuration(1000).start()
        }
*/
    }
}
