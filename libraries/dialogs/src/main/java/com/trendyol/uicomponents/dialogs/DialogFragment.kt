package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import android.view.ViewGroup.FOCUS_AFTER_DESCENDANTS
import androidx.annotation.DrawableRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trendyol.dialog.R
import com.trendyol.dialog.databinding.FragmentDialogBinding
import com.trendyol.uicomponents.dialogs.list.DialogListAdapter
import com.trendyol.uicomponents.dialogs.list.DialogListViewModel

class DialogFragment internal constructor(
    private val title: String? = null,
    private val showCloseButton: Boolean? = null,
    private val closeButtonListener: ((DialogFragment) -> Unit)? = null,
    private val content: CharSequence? = null,
    private val showContentAsHtml: Boolean = false,
    @DrawableRes private val contentImage: Int? = null,
    private val leftButtonText: String? = null,
    private val rightButtonText: String? = null,
    private val leftButtonClickListener: ((DialogFragment) -> Unit)? = null,
    private val rightButtonClickListener: ((DialogFragment) -> Unit)? = null,
    private val items: List<Pair<Boolean, CharSequence>>? = null,
    private val showItemsAsHtml: Boolean = false,
    private val onItemSelectedListener: ((DialogFragment, Int) -> Unit)? = null,
    private val enableSearch: Boolean = false,
    private val showClearSearchButton: Boolean = false,
    private val searchHint: String = ""
) : BaseBottomSheetDialog<FragmentDialogBinding>() {

    private val itemsAdapter by lazy { DialogListAdapter(showItemsAsHtml) }
    private val dialogListViewModel by lazy {
        ViewModelProviders.of(this)[DialogListViewModel::class.java]
    }

    override fun getLayoutResId(): Int = R.layout.fragment_dialog

    override fun setUpView() {
        animateCornerRadiusWithStateChanged()
        
        with(binding) {
            imageClose.setOnClickListener {
                dismiss()
                closeButtonListener?.invoke(this@DialogFragment)
            }
            buttonLeft.setOnClickListener {
                leftButtonClickListener?.invoke(this@DialogFragment)
            }
            buttonRight.setOnClickListener {
                rightButtonClickListener?.invoke(this@DialogFragment)
            }

            if (items != null) {
                recyclerViewItems.adapter = itemsAdapter
                recyclerViewItems.isNestedScrollingEnabled = false

                itemsAdapter.onItemSelectedListener = { position ->
                    dialogListViewModel.onSelectionChanged(position)
                }

                editTextSearch.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED)
                    }
                }

                editTextSearch.doAfterTextChanged {
                    dialogListViewModel.search(it.toString())
                }

                imageClearSearchQuery.setOnClickListener {
                    editTextSearch.text?.clear()
                    dialogListViewModel.clearSearch()
                }

                constraintLayout.descendantFocusability = FOCUS_AFTER_DESCENDANTS

                setUpViewModel(items)
            }
        }
    }

    override fun setViewState() {
        val viewState = DialogViewState(
            title = title,
            showCloseButton = showCloseButton,
            content = content ?: SpannableString(""),
            showContentAsHtml = showContentAsHtml,
            contentImage = contentImage,
            leftButtonText = leftButtonText,
            rightButtonText = rightButtonText,
            isListVisible = items != null,
            isSearchEnabled = enableSearch,
            isClearSearchButtonVisible = showClearSearchButton,
            searchHint = searchHint
        )

        binding.viewState = viewState
        binding.executePendingBindings()
    }

    fun showDialog(fragmentManager: FragmentManager) {
        show(fragmentManager, TAG)
    }

    private fun setUpViewModel(items: List<Pair<Boolean, CharSequence>>) {
        with(dialogListViewModel) {
            getDialogSearchItemsLiveData().observe(viewLifecycleOwner, Observer { items ->
                itemsAdapter.setItems(items)
            })
            getLastChangedItemLiveData().observeNonNull(viewLifecycleOwner) { position ->
                onItemSelectedListener?.invoke(this@DialogFragment, position)
            }
            setInitialItems(items)
        }
    }

    companion object {

        const val TAG: String = "TRENDYOL_BOTTOM_SHEET_DIALOG"
    }
}
