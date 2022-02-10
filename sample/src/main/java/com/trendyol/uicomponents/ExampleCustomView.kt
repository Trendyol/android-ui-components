package com.trendyol.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.trendyol.uicomponents.databinding.ViewCustomExampleBinding

class ExampleCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCustomExampleBinding =
        DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.view_custom_example, this, true)

    fun setButtonCancelClickListener(listener: () -> Unit) {
        binding.buttonCancel.setOnClickListener { listener.invoke() }
    }

    fun setButtonOkClickListener(listener: () -> Unit) {
        binding.buttonOk.setOnClickListener { listener.invoke() }
    }
}