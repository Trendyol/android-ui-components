package com.trendyol.suggestioninputview

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.suggestioninputview.databinding.ItemSuggestionBinding

internal class SuggestionItemAdapter :
    RecyclerView.Adapter<SuggestionItemAdapter.SuggestionItemViewHolder>() {

    private var onSuggestionItemClickListener: ((SuggestionInputItemViewState) -> Unit)? = null
    private var list: MutableList<SuggestionInputItemViewState> = mutableListOf()

    fun submitList(list: List<SuggestionInputItemViewState>) {
        this.list.clear()
        this.list.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionItemViewHolder {
        val binding = ItemSuggestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SuggestionItemViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SuggestionItemViewHolder, position: Int) {
        holder.viewState = list[position]
    }

    fun setSuggestionItemClickListener(function: (SuggestionInputItemViewState) -> (Unit)) {
        this.onSuggestionItemClickListener = function
    }

    inner class SuggestionItemViewHolder(val binding: ItemSuggestionBinding) : RecyclerView.ViewHolder(binding.root) {

        var viewState: SuggestionInputItemViewState = SuggestionInputItemViewState.empty()
            set(value) {
                field = value
                bind()
            }

        init {
            binding.root.setOnClickListener { onSuggestionItemClickListener?.invoke(viewState) }
        }

        private fun bind() {
            with(binding.suggestionText) {
                background = viewState.getBackground()
                setPadding(
                    viewState.getHorizontalPadding(),
                    viewState.getVerticalPadding(),
                    viewState.getHorizontalPadding(),
                    viewState.getVerticalPadding()
                )
                minWidth = viewState.getMinimumWidth()
                text = viewState.text
                setTextColor(viewState.getTextColor())
                setTextSize(TypedValue.COMPLEX_UNIT_PX, viewState.textSize)
            }
            with(binding.badgeText) {
                background = viewState.getBadgeBackground()
                setPadding(
                    viewState.getBadgeHorizontalPadding(),
                    viewState.getBadgeVerticalPadding(),
                    viewState.getBadgeHorizontalPadding(),
                    viewState.getBadgeVerticalPadding()
                )
                text = viewState.getBadgeText()
                setTextColor(viewState.getBadgeTextColor())
                setTextSize(TypedValue.COMPLEX_UNIT_PX, viewState.getBadgeTextSize())
                visibility = viewState.getBadgeVisibility()
            }
        }
    }
}