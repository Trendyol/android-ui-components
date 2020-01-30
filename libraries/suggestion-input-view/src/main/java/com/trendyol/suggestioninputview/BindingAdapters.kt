package com.trendyol.suggestioninputview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("suggestionItems")
internal fun RecyclerView.submitList(items: List<SuggestionInputItemViewState>?) {
    (adapter as? SuggestionItemAdapter)?.submitList(items.orEmpty())
}