package com.trendyol.uicomponents

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.uicomponents.databinding.ActivityQuantityPickerViewBinding
import com.trendyol.uicomponents.quantitypickerview.QuantityPickerView
import com.trendyol.uicomponents.quantitypickerview.QuantityPickerViewState

class QuantityPickerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuantityPickerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityQuantityPickerViewBinding.inflate(layoutInflater).also { binding = it }.root)

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
            quantityBackgroundVerticalPadding = asDP(6),
            addContentDescription = "Add",
            removeContentDescription = "Remove"
        )

        binding.quantityPickerView2.setQuantityPickerViewState(viewState)

        with(binding.quantityPickerView) {
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
        with(binding.quantityPickerViewCollapsedRight) {
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
        binding.buttonReset.setOnClickListener {
            binding.quantityPickerView.reset()
            binding.quantityPickerView2.reset()
            binding.quantityPickerViewCollapsed.reset()
            binding.quantityPickerViewCollapsedRight.reset()
        }
    }

    private fun completeLoading(quantityPickerView: QuantityPickerView, increment: Int, delay: Long = 500L) {
        quantityPickerView.postDelayed({
            quantityPickerView.incrementQuantityBy(increment)
        }, delay)
    }
}
