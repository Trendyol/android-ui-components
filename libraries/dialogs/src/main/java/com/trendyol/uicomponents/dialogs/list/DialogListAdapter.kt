package com.trendyol.uicomponents.dialogs.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.dialog.R
import com.trendyol.dialog.databinding.ItemListBinding
import com.trendyol.uicomponents.dialogs.inflate

internal class DialogListAdapter(
    private val showItemsAsHtml: Boolean,
    private val selectedItemDrawable: Int?,
    private val selectedTextColor: Int?,
    private val showRadioButton: Boolean
) : ListAdapter<Pair<Boolean, CharSequence>, DialogListAdapter.ItemViewHolder>(ListItemDiffCallback()) {
    var onItemSelectedListener: ((Int) -> Unit)? = null
    var onItemReselectedListener: ((Int) -> Unit)? = null

    fun setItems(list: List<Pair<Boolean, CharSequence>>) {
        submitList(list.toMutableList())
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(parent.inflate(R.layout.item_list, false))

    inner class ItemViewHolder(
        private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (binding.viewState?.isChecked == false) {
                    onItemSelectedListener?.invoke(adapterPosition)
                } else {
                    onItemReselectedListener?.invoke(adapterPosition)
                }
            }
        }

        fun bind(item: Pair<Boolean, CharSequence>) {
            binding.viewState = DialogListItemViewState(
                name = item.second,
                showAsHtml = showItemsAsHtml,
                selectedItemDrawable = selectedItemDrawable,
                selectedTextColor = selectedTextColor,
                isChecked = item.first,
                showRadioButton = showRadioButton
            )
            binding.executePendingBindings()
        }
    }
}