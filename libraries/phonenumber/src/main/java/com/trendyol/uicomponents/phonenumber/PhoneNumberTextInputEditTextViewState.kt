package com.trendyol.uicomponents.phonenumber

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneNumberTextInputEditTextViewState(
    val maskable: Boolean,
    val maskCharacter: Char
) : Parcelable
