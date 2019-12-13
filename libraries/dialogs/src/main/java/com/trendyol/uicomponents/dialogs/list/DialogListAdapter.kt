package com.trendyol.uicomponents.dialogs.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.dialog.R
import com.trendyol.dialog.databinding.ItemListBinding
import com.trendyol.uicomponents.dialogs.inflate

internal class DialogListAdapter(
    private val showItemsAsHtml: Boolean,
    private val selectedItemDrawable: Int?,
    private val selectedTextColor: Int?
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
            binding.root.setOnClickListener {
                if (binding.viewState?.isChecked == false) {
                    onItemSelectedListener?.invoke(adapterPosition)
                }
            }
        }

        fun bind(item: Pair<Boolean, CharSequence>) {
            binding.viewState = DialogListItemViewState(
                name = item.second,
                showAsHtml = showItemsAsHtml,
                selectedItemDrawable = selectedItemDrawable,
                selectedTextColor = selectedTextColor,
                isChecked = item.first
            )
            binding.executePendingBindings()
        }
    }
}
