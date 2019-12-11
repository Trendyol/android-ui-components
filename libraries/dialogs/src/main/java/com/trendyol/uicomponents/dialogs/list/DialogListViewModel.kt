package com.trendyol.uicomponents.dialogs.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trendyol.uicomponents.dialogs.SingleLiveEvent

class DialogListViewModel : ViewModel() {

    private val dialogSearchItemsLiveData: MutableLiveData<List<Pair<Boolean, CharSequence>>> =
        MutableLiveData()

    private val lastChangedItemLiveData: SingleLiveEvent<Int> = SingleLiveEvent()

    fun getDialogSearchItemsLiveData(): LiveData<List<Pair<Boolean, CharSequence>>> =
        dialogSearchItemsLiveData

    fun getLastChangedItemLiveData(): LiveData<Int> = lastChangedItemLiveData

    private lateinit var items: List<Pair<Boolean, CharSequence>>

    private var searchQuery: String = ""

    fun setInitialItems(items: List<Pair<Boolean, CharSequence>>) {
        this.items = items
        dialogSearchItemsLiveData.value = items
    }

    fun search(query: String) {
        searchQuery = query
        dialogSearchItemsLiveData.value =
            items.filter { it.second.contains(query, ignoreCase = true) }
    }

    fun clearSearch() {
        searchQuery = ""
        dialogSearchItemsLiveData.value = items
    }

    fun onSelectionChanged(position: Int) {
        // First update all items, then update in liveData.
        items.indexOf(dialogSearchItemsLiveData.value?.get(position)).also {
            updateSelectedIndex(it)
        }

        search(searchQuery)
    }

    private fun updateSelectedIndex(position: Int) {
        items = items.toMutableList().mapIndexed { index, item ->
            item.copy(first = position == index).also {
                if (it.first) {
                    lastChangedItemLiveData.value = position
                }
            }
        }
    }
}
