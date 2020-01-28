package com.trendyol.suggestioninputview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.suggestioninputview.databinding.ItemSuggestionBinding

internal class SuggestionItemAdapter :
    RecyclerView.Adapter<SuggestionItemAdapter.SuggestionItemViewHolder>() {

    private var onSuggestionItemClickListener: ((SuggestionInputItemViewState) -> Unit)? = null
    private var list: MutableList<SuggestionInputItemViewState> = mutableListOf()

    @JvmOverloads
    fun submitList(list: List<SuggestionInputItemViewState>) {
        this.list.clear()
        this.list.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemSuggestionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_suggestion,
            parent,
            false
        )
        return SuggestionItemViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SuggestionItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setSuggestionItemClickListener(function: (SuggestionInputItemViewState) -> (Unit)) {
        this.onSuggestionItemClickListener = function
    }

    inner class SuggestionItemViewHolder(val binding: ItemSuggestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onSuggestionItemClickListener?.invoke(binding.viewState!!) }
        }

        fun bind(itemViewState: SuggestionInputItemViewState) {
            binding.viewState = itemViewState
            binding.executePendingBindings()
        }
    }
}