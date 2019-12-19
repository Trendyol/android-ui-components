package com.trendyol.uicomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.trendyol.uicomponents.databinding.ActivityPhoneNumberBinding
import com.trendyol.uicomponents.phonenumber.PhoneNumberTextInputEditTextViewState

class PhoneNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityPhoneNumberBinding>(
            this,
            R.layout.activity_phone_number
        ).apply {
            viewState = PhoneNumberTextInputEditTextViewState(maskable = true, maskCharacter = '*')
            executePendingBindings()
            editTextPhoneDot.setText("0555●●●●●55")
            editTextPhoneStar.setText("0555*****55")

        }
    }
}