package com.trendyol.uicomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trendyol.suggestioninputview.Rule
import com.trendyol.suggestioninputview.SuggestionInputItem
import com.trendyol.suggestioninputview.SuggestionItemType
import com.trendyol.uicomponents.databinding.ActivitySuggestionInputBinding

class SuggestionInputViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.suggestionInputView.shouldShowSelectableItemError(true)
    }

    private fun onSuggestionItemClicked(suggestionInputItem: SuggestionInputItem) {
        if(suggestionInputItem.isSelected) {
            binding.selectedText.text = suggestionInputItem.text
            binding.selectedValue.text = suggestionInputItem.value
        } else {
            binding.selectedText.text = ""
            binding.selectedValue.text = ""
        }
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
            value = "20",
            badgeText = "Popüler"
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
