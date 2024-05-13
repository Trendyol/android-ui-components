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

        binding.toolbar.startImageClickListener = { onBackPressed() }
    }

    private fun setUpToolbar3() {
        with(binding.toolbar3) {
            viewState = ToolbarViewState(
                rightImageDrawableResId = R.drawable.ic_info,
                rightImageContentDescription = "Add",
                middleText = "<h1>HTML text</h1>",
                upperLeftTextMarginStartInPixel = 20,
                rightImageDrawableMarginEndInPixel = 0,
                rightImageDrawableVerticalMarginInPixel = 0,
                enableDotPoint = true
            )

            lowerLeftTextClickListener = { showToast("toolbar3.lowerLeftTextClickListener") }
            upperLeftTextClickListener = { showToast("toolbar3.upperLeftTextClickListener") }
            lowerRightTextClickListener = { showToast("toolbar3.lowerRightTextClickListener") }
            upperRightTextClickListener = { showToast("toolbar3.upperRightTextClickListener") }
            middleTextClickListener = { showToast("toolbar3.middleTextClickListener") }
            leftImageClickListener = { showToast("toolbar3.leftImageClickListener") }
            rightImageClickListener = {
                viewState = viewState.copy(enableDotPoint = viewState.enableDotPoint.not())
                showToast("toolbar3.rightImageClickListener")
            }
        }
    }

    private fun setUpToolbar4() {
        with(binding.toolbar4) {
            lowerStartTextClickListener = { showToast("toolbar4.lowerStartTextClickListener") }
            upperStartTextClickListener = { showToast("toolbar4.upperStartTextClickListener") }
            lowerEndTextClickListener = { showToast("toolbar4.lowerEndTextClickListener") }
            upperEndTextClickListener = { showToast("toolbar4.upperEndTextClickListener") }
            startImageClickListener = { showToast("toolbar4.startImageClickListener") }
            endImageClickListener = { showToast("toolbar4.endImageClickListener") }
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
