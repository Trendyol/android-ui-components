package com.trendyol.uicomponents.dialogs.list

import androidx.recyclerview.widget.DiffUtil

class ListItemDiffCallback: DiffUtil.ItemCallback<Pair<Boolean, CharSequence>>() {
    override fun areContentsTheSame(
        oldItem: Pair<Boolean, CharSequence>,
        newItem: Pair<Boolean, CharSequence>
    ): Boolean {
        return oldItem.first == newItem.first && oldItem.second.toString() == newItem.second.toString()
    }

    override fun areItemsTheSame(
        oldItem: Pair<Boolean, CharSequence>,
        newItem: Pair<Boolean, CharSequence>
    ): Boolean {
        return oldItem.second == newItem.second
    }
}