package com.trendyol.uicomponents.dialogs.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.dialog.databinding.ItemListBinding

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
        ItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class ItemViewHolder(
        private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (!binding.imageViewLeftImageDrawable.isChecked) {
                    onItemSelectedListener?.invoke(adapterPosition)
                } else {
                    onItemReselectedListener?.invoke(adapterPosition)
                }
            }
        }

        fun bind(item: Pair<Boolean, CharSequence>) {
            val viewState = DialogListItemViewState(
                name = item.second,
                showAsHtml = showItemsAsHtml,
                selectedItemDrawable = selectedItemDrawable,
                selectedTextColor = selectedTextColor,
                isChecked = item.first,
                showRadioButton = showRadioButton
            )

            with(binding) {
                with(imageViewLeftImageDrawable) {
                    isChecked = viewState.getRadioButtonChecked()
                    visibility = viewState.getRadioButtonVisibility()
                }
                with(radioButtonItem) {
                    text = viewState.getText()
                    setTextColor(viewState.getSelectedTextColor(context))
                }
                with(imageViewCheckedDrawable) {
                    setImageDrawable(viewState.getSelectedItemDrawable(context))
                    visibility = viewState.getSelectedItemDrawableVisibility()
                }
            }
        }
    }
}
