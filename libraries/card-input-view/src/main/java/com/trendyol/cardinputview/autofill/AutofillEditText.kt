package com.trendyol.cardinputview.autofill

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.autofill.AutofillValue
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText

internal class AutofillAppCompatEditText : TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var autofillValueListener: ((String) -> Unit)? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun autofill(value: AutofillValue?) {
        if (value?.isText == true) {
            autofillValueListener?.invoke(value.textValue.toString())
        }
    }
}
