package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import android.view.ViewGroup.FOCUS_AFTER_DESCENDANTS
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trendyol.dialog.R
import com.trendyol.dialog.databinding.FragmentDialogBinding
import com.trendyol.uicomponents.dialogs.list.DialogListAdapter
import com.trendyol.uicomponents.dialogs.list.DialogListViewModel

class DialogFragment internal constructor() : BaseBottomSheetDialog<FragmentDialogBinding>() {

    private val dialogArguments by lazy(LazyThreadSafetyMode.NONE)
    { requireNotNull(DialogFragmentArguments.fromBundle(requireArguments())) }

    private val itemsAdapter by lazy(LazyThreadSafetyMode.NONE)
    {
        DialogListAdapter(
            dialogArguments.showItemsAsHtml,
            dialogArguments.selectedItemDrawable,
            dialogArguments.selectedTextColor
        )
    }

    private val dialogListViewModel by lazy {
        ViewModelProviders.of(this)[DialogListViewModel::class.java]
    }

    var closeButtonListener: ((DialogFragment) -> Unit)? = null
    var leftButtonClickListener: ((DialogFragment) -> Unit)? = null
    var rightButtonClickListener: ((DialogFragment) -> Unit)? = null
    var onItemSelectedListener: ((DialogFragment, Int) -> Unit)? = null

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

            dialogArguments.items?.let { items ->
                initializeRecyclerView()
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

    private fun initializeRecyclerView() = with(binding.recyclerViewItems) {
        adapter = itemsAdapter.apply {
            onItemSelectedListener =
                { position -> dialogListViewModel.onSelectionChanged(position) }
        }
        isNestedScrollingEnabled = false
    }

    override fun setViewState() {
        val viewState = DialogViewState(
            title = dialogArguments.title,
            showCloseButton = dialogArguments.showCloseButton,
            content = dialogArguments.content ?: SpannableString(""),
            showContentAsHtml = dialogArguments.showContentAsHtml,
            contentImage = dialogArguments.contentImage,
            leftButtonText = dialogArguments.leftButtonText,
            rightButtonText = dialogArguments.rightButtonText,
            isListVisible = dialogArguments.items?.isNotEmpty() == true,
            isSearchEnabled = dialogArguments.enableSearch,
            isClearSearchButtonVisible = dialogArguments.showClearSearchButton,
            searchHint = dialogArguments.searchHint
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

        fun findFragment(fragmentManager: FragmentManager): DialogFragment? {
            return fragmentManager.findFragmentByTag(TAG) as? DialogFragment
        }
    }
}
