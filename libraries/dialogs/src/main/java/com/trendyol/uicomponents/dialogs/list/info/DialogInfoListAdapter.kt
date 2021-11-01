package com.trendyol.uicomponents.dialogs.list.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.dialog.databinding.ItemUiComponentsInfoListDialogBinding

internal class DialogInfoListAdapter(
) : ListAdapter<Pair<CharSequence, CharSequence>, DialogInfoListAdapter.ItemViewHolder>(InfoListItemDiffCallback()) {

    fun setItems(list: List<Pair<CharSequence, CharSequence>>) {
        submitList(list.toMutableList())
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemUiComponentsInfoListDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class ItemViewHolder(
        private val binding: ItemUiComponentsInfoListDialogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<CharSequence, CharSequence>) {
            val viewState = DialogInfoListItemViewState(
                key = item.first,
                value = item.second
            )

            with(binding) {
                textKey.text = viewState.key
                textValue.text = viewState.value
            }
        }
    }
}
