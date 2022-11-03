package com.trendyol.uicomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.uicomponents.databinding.ActivityPhoneNumberBinding
import com.trendyol.uicomponents.phonenumber.PhoneNumberTextInputEditTextViewState

class PhoneNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.editTextPhoneDot) {
            setMaskCharacter('●')
            setMaskable(true)
            setText("0555●●●●●55")
        }
        with(binding.editTextPhoneStar) {
            viewState = PhoneNumberTextInputEditTextViewState(maskable = true, maskCharacter = '*')
            setText("0555*****55")
        }
    }
}
