package com.trendyol.uicomponents

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.trendyol.uicomponents.quantitypickerview.QuantityPickerViewState
import kotlinx.android.synthetic.main.activity_quantity_picker_view.*

class QuantityPickerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quantity_picker_view)

        val viewState = QuantityPickerViewState(
            text = "Fresh Money",
            textSize = (12 * resources.displayMetrics.scaledDensity).toInt(),
            quantityTextSize = (14 * resources.displayMetrics.scaledDensity).toInt(),
            backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.qpv_shape_default_background)!!,
            removeIconDrawable = ContextCompat.getDrawable(this, R.drawable.qpv_ic_default_remove)!!,
            subtractIconDrawable = ContextCompat.getDrawable(this, R.drawable.qpv_ic_default_subtract)!!,
            addIconDrawable = ContextCompat.getDrawable(this, R.drawable.qpv_ic_default_add)!!
        )

        quantity_picker_view_2.setQuantityPickerViewState(viewState)

        with(quantity_picker_view) {
            onAddClicked = { number ->
                Toast.makeText(context, "Add click: $number", Toast.LENGTH_SHORT).show()
                completeLoading()
                true
            }
            onSubtractClicked = { number ->
                Toast.makeText(context, "Subtract/Remove click: $number", Toast.LENGTH_SHORT).show()
                false
            }
        }
        button_reset.setOnClickListener {
            quantity_picker_view.reset()
            quantity_picker_view_2.reset()
        }
    }

    private fun completeLoading(delay: Long = 500L) {
        quantity_picker_view.postDelayed({
            quantity_picker_view.stopLoading()
        }, delay)
    }
}
