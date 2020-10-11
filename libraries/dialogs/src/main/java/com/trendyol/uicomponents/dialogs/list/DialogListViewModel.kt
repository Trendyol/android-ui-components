package com.trendyol.uicomponents.dialogs.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trendyol.uicomponents.dialogs.SingleLiveEvent

class DialogListViewModel : ViewModel() {

    private val dialogSearchItemsLiveData: MutableLiveData<List<Pair<Boolean, CharSequence>>> =
        MutableLiveData()

    private lateinit var originalList: List<Pair<Boolean, CharSequence>>

    private val lastChangedItemLiveData: SingleLiveEvent<Int> = SingleLiveEvent()

    private val reselectionItemLiveData: SingleLiveEvent<Int> = SingleLiveEvent()

    fun getDialogSearchItemsLiveData(): LiveData<List<Pair<Boolean, CharSequence>>> =
        dialogSearchItemsLiveData

    fun getLastChangedItemLiveData(): LiveData<Int> = lastChangedItemLiveData

    fun getReselectionItemLiveData(): LiveData<Int> = reselectionItemLiveData

    private var searchQuery: String = ""

    fun setInitialItems(items: List<Pair<Boolean, CharSequence>>) {
        if (dialogSearchItemsLiveData.value == null) {
            dialogSearchItemsLiveData.value = items
            originalList = items
        }
    }

    fun search(query: String) {
        searchQuery = query
        val listToFilter =
            if (query.isEmpty()) originalList else dialogSearchItemsLiveData.value.orEmpty()
        dialogSearchItemsLiveData.value =
            listToFilter.filter { it.second.contains(query, ignoreCase = true) }
    }

    fun clearSearch() {
        search("")
    }

    fun onSelectionChanged(position: Int) {
        // First update all items, then update in liveData.
        originalList
            .indexOf(dialogSearchItemsLiveData.value?.get(position))
            .also { updateSelectedIndex(it) }

        search(searchQuery)
    }

    private fun updateSelectedIndex(position: Int) {
        originalList = originalList.mapIndexed { index, item ->
            val isSelected = position == index
            if (isSelected) {
                lastChangedItemLiveData.value = position
            }
            item.copy(first = isSelected)
        }
    }

    fun onReselection(position: Int) {
        reselectionItemLiveData.value = position
    }
}
