package com.trendyol.uicomponents

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.uicomponents.databinding.ActivityToolbarBinding
import com.trendyol.uicomponents.toolbar.ToolbarViewState

class ToolbarActivity : AppCompatActivity() {

    private var _binding: ActivityToolbarBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar3()
        setUpToolbar4()

        binding.toolbar.leftImageClickListener = { onBackPressed() }
    }

    private fun setUpToolbar3() {
        with(binding.toolbar3) {
            viewState = ToolbarViewState(
                rightImageDrawableResId = android.R.drawable.ic_menu_add,
                rightImageContentDescription = "Add",
                middleText = "<h1>HTML text</h1>",
                upperLeftTextMarginStartInPixel = 20
            )

            lowerLeftTextClickListener = { showToast("toolbar3.lowerLeftTextClickListener") }
            upperLeftTextClickListener = { showToast("toolbar3.upperLeftTextClickListener") }
            lowerRightTextClickListener = { showToast("toolbar3.lowerRightTextClickListener") }
            upperRightTextClickListener = { showToast("toolbar3.upperRightTextClickListener") }
            middleTextClickListener = { showToast("toolbar3.middleTextClickListener") }
            leftImageClickListener = { showToast("toolbar3.leftImageClickListener") }
            rightImageClickListener = { showToast("toolbar3.rightImageClickListener") }
        }
    }

    private fun setUpToolbar4() {
        with(binding.toolbar4) {
            lowerLeftTextClickListener = { showToast("toolbar4.lowerLeftTextClickListener") }
            upperLeftTextClickListener = { showToast("toolbar4.upperLeftTextClickListener") }
            lowerRightTextClickListener = { showToast("toolbar4.lowerRightTextClickListener") }
            upperRightTextClickListener = { showToast("toolbar4.upperRightTextClickListener") }
            leftImageClickListener = { showToast("toolbar4.leftImageClickListener") }
            rightImageClickListener = { showToast("toolbar4.rightImageClickListener") }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
