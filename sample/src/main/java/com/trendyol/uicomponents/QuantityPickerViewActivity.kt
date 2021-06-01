package com.trendyol.uicomponents

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.uicomponents.quantitypickerview.QuantityPickerView
import com.trendyol.uicomponents.quantitypickerview.QuantityPickerViewState

class QuantityPickerViewActivity : AppCompatActivity() {

    private val quantityPickerView2 by lazy { findViewById<QuantityPickerView>(R.id.quantity_picker_view_2) }
    private val quantityPickerView by lazy { findViewById<QuantityPickerView>(R.id.quantity_picker_view) }
    private val quantityPickerViewCollapsedRight by lazy { findViewById<QuantityPickerView>(R.id.quantity_picker_view_collapsed_right) }
    private val buttonReset by lazy { findViewById<Button>(R.id.button_reset) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quantity_picker_view)

        val viewState = QuantityPickerViewState(
            text = "Fresh Money",
            textSize = asSP(12),
            quantityTextSize = asSP(14),
            backgroundDrawable = drawable(R.drawable.qpv_shape_default_background),
            removeIconDrawable = drawable(R.drawable.qpv_ic_default_remove),
            subtractIconDrawable = drawable(R.drawable.qpv_ic_default_subtract),
            addIconDrawable = drawable(R.drawable.qpv_ic_default_add),
            quantityBackgroundDrawable = drawable(R.drawable.qpv_shape_default_background),
            textColor = themeColor(R.attr.colorAccent),
            progressTintColor = themeColor(R.attr.colorAccent),
            quantityTextColor = themeColor(R.attr.colorPrimary),
            buttonHorizontalPadding = asDP(8),
            buttonVerticalPadding = asDP(8),
            progressVerticalPadding = asDP(6),
            quantityBackgroundVerticalPadding = asDP(6)
        )

        quantityPickerView2.setQuantityPickerViewState(viewState)

        with(quantityPickerView) {
            onAddClicked = { number ->
                Toast.makeText(context, "Add click: $number", Toast.LENGTH_SHORT).show()
                completeLoading(this, increment = +1)
                true
            }
            onSubtractClicked = { number ->
                Toast.makeText(context, "Subtract/Remove click: $number", Toast.LENGTH_SHORT).show()
                false
            }

            onQuantityTextClicked = { number ->
                Toast.makeText(context, "Quantity text click: $number", Toast.LENGTH_SHORT).show()
            }

        }
        with(quantityPickerViewCollapsedRight) {
            onAddClicked = { number ->
                Toast.makeText(context, "Add click: $number", Toast.LENGTH_SHORT).show()
                completeLoading(this, increment = +1)
                true
            }
            onSubtractClicked = { number ->
                Toast.makeText(context, "Subtract/Remove click: $number", Toast.LENGTH_SHORT).show()
                completeLoading(this, increment = -1)
                true
            }
            expansionListener = {
                Toast.makeText(context, "$it ${it.expanded}", Toast.LENGTH_LONG).show()
            }
        }
        buttonReset.setOnClickListener {
            quantityPickerView.reset()
            quantityPickerView2.reset()
            quantityPickerViewCollapsedRight.reset()
        }
    }

    private fun completeLoading(quantityPickerView: QuantityPickerView, increment: Int, delay: Long = 500L) {
        quantityPickerView.postDelayed({
            quantityPickerView.incrementQuantityBy(increment)
        }, delay)
    }
}
