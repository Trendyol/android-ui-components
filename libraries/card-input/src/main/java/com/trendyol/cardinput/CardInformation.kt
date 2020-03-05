package com.trendyol.cardinput

data class CardInformation(
    val cardNumber: String = "",
    val cvv: String = "",
    val expiryMonth: String = "",
    val expiryYear: String = ""
)
