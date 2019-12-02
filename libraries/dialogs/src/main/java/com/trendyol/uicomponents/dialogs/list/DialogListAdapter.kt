package com.trendyol.uicomponents.dialogs.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.dialog.R
import com.trendyol.dialog.databinding.ItemListBinding
import com.trendyol.uicomponents.dialogs.inflate

internal class DialogListAdapter(
    private val showItemsAsHtml: Boolean
) : RecyclerView.Adapter<DialogListAdapter.ItemViewHolder>() {

    private var items: List<Pair<Boolean, CharSequence>> = emptyList()
    var onItemSelectedListener: ((Int) -> Unit)? = null

    fun setItems(list: List<Pair<Boolean, CharSequence>>) {
        items = items.toMutableList().apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    private fun updateList(selectedItemPosition: Int) {
        var firstSelectedItemPosition = 0
        items = items.mapIndexed { index, item ->
            if (item.first) {
                firstSelectedItemPosition = index
            }
            item.copy(first = index == selectedItemPosition, second = item.second)
        }
        notifyItemChanged(firstSelectedItemPosition)
        notifyItemChanged(selectedItemPosition)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(parent.inflate(R.layout.item_list, false))

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(
        private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.radioButtonItem.setOnCheckedChangeListener { radioButton, isChecked ->
                if (radioButton.isPressed && isChecked) {
                    updateList(selectedItemPosition = adapterPosition)
                    onItemSelectedListener?.invoke(adapterPosition)
                }
            }
        }

        fun bind(item: Pair<Boolean, CharSequence>) {
            binding.viewState = DialogListItemViewState(
                name = item.second,
                showAsHtml = showItemsAsHtml,
                isChecked = item.first
            )
            binding.executePendingBindings()
        }
    }
}
