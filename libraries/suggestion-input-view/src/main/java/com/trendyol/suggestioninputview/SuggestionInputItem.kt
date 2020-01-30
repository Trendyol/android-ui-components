package com.trendyol.suggestioninputview

data class SuggestionInputItem(
    val id: Int,
    val text: String,
    val value: String,
    val isSelected: Boolean,
    val type: SuggestionItemType,
    val suffix: String = ""
)