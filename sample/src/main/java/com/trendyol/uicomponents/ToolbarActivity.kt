package com.trendyol.uicomponents

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.uicomponents.toolbar.Toolbar
import com.trendyol.uicomponents.toolbar.ToolbarViewState

class ToolbarActivity : AppCompatActivity() {

    private val toolbar3 by lazy { findViewById<Toolbar>(R.id.toolbar3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)

        toolbar3.viewState = ToolbarViewState(
            rightImageDrawableResId = android.R.drawable.ic_menu_add,
            middleText = getString(android.R.string.unknownName),
            upperLeftText = "<h1>HTML text</h1>",
            upperLeftTextMarginStartInPixel = 20
        )

        toolbar3.upperLeftTextClickListener = {
            Toast.makeText(this, "toolbar3.upperLeftTextClickListener", Toast.LENGTH_SHORT).show()
        }
    }
}
