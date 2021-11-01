package com.trendyol.uicomponents.dialogs.list.info

import androidx.recyclerview.widget.DiffUtil

class InfoListItemDiffCallback : DiffUtil.ItemCallback<Pair<CharSequence, CharSequence>>() {
    override fun areContentsTheSame(
        oldItem: Pair<CharSequence, CharSequence>,
        newItem: Pair<CharSequence, CharSequence>
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: Pair<CharSequence, CharSequence>,
        newItem: Pair<CharSequence, CharSequence>
    ): Boolean {
        return oldItem == newItem
    }
}