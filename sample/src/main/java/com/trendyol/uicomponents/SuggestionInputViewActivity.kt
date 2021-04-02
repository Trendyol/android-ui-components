package com.trendyol.uicomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.trendyol.suggestioninputview.Rule
import com.trendyol.suggestioninputview.SuggestionInputItem
import com.trendyol.suggestioninputview.SuggestionItemType
import com.trendyol.uicomponents.databinding.ActivitySuggestionInputBinding

class SuggestionInputViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_suggestion_input
        )

        val rule1 = Rule.Builder()
            .smallerThan("50")
            .errorMessage("Hatali giriş")
            .build()
        val rule2 = Rule.Builder()
            .notEqualsTo("0")
            .errorMessage("0 giremezsin")
            .build()
        binding.suggestionInputView.setRuleSet(listOf(rule1, rule2))
        binding.suggestionInputView.setSuggestionItemClickListener { onSuggestionItemClicked(it) }
        binding.suggestionInputView.setItems(createSuggestionInputItems())
        binding.buttonLoad.setOnClickListener { onLoadClicked() }
    }

    private fun onLoadClicked() {
        binding.shouldShowError = true
        binding.executePendingBindings()
    }

    private fun onSuggestionItemClicked(suggestionInputItem: SuggestionInputItem) {
        binding.selectedText.text = suggestionInputItem.text
        binding.selectedValue.text = suggestionInputItem.value
    }

    private fun createSuggestionInputItems(): List<SuggestionInputItem> {
        val items = mutableListOf<SuggestionInputItem>()
        val item1 = SuggestionInputItem(
            id = 1,
            type = SuggestionItemType.SELECTABLE,
            isSelected = true,
            text = "10 ₺",
            value = "10"
        )
        val item2 = SuggestionInputItem(
            id = 2,
            type = SuggestionItemType.SELECTABLE,
            isSelected = false,
            text = "20 ₺",
            value = "20"
        )

        val item3 = SuggestionInputItem(
            id = 3,
            type = SuggestionItemType.SELECTABLE,
            isSelected = false,
            text = "30 ₺",
            value = "30"
        )

        val item4 = SuggestionInputItem(
            id = 4,
            type = SuggestionItemType.INPUT,
            isSelected = false,
            text = "Other",
            suffix = "₺",
            value = ""
        )

        items.add(item1)
        items.add(item2)
        items.add(item3)
        items.add(item4)
        return items
    }
}
