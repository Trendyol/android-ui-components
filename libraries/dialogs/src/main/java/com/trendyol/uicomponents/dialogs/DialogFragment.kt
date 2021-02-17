package com.trendyol.uicomponents.dialogs

import android.graphics.Outline
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.view.ViewGroup.FOCUS_AFTER_DESCENDANTS
import android.view.ViewOutlineProvider
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

    private val dialogArguments by lazy(LazyThreadSafetyMode.NONE) {
        requireNotNull(DialogFragmentArguments.fromBundle(requireArguments()))
    }

    private val itemsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DialogListAdapter(
            dialogArguments.showItemsAsHtml,
            dialogArguments.selectedItemDrawable,
            dialogArguments.selectedTextColor,
            dialogArguments.showRadioButton
        )
    }

    private val dialogListViewModel by lazy {
        ViewModelProviders.of(this)[DialogListViewModel::class.java]
    }

    var closeButtonListener: ((DialogFragment) -> Unit)? = null
    var leftButtonClickListener: ((DialogFragment) -> Unit)? = null
    var rightButtonClickListener: ((DialogFragment) -> Unit)? = null
    var onItemSelectedListener: ((DialogFragment, Int) -> Unit)? = null
    var onItemReselectedListener: ((DialogFragment, Int) -> Unit)? = null

    override fun getLayoutResId(): Int = R.layout.fragment_dialog

    override fun setUpView() {
        if (dialogArguments.animateCornerRadiusWhenExpand) {
            animateCornerRadiusWithStateChanged()
        } else {
            binding.cardView.outlineProvider = BottomSheetOutlineProvider(radius = requireContext().pixel(R.dimen.dialogs_corner_radius))
        }

        with(binding) {
            binding.cardView.clipToOutline = true
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

            textContent.autoLinkMask = Linkify.ALL
            textContent.movementMethod = LinkMovementMethod.getInstance()

            dialogArguments.items?.let { items ->
                initializeRecyclerView()
                editTextSearch.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED)
                    } else {
                        editTextSearch.hideKeyboard()
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
            onItemReselectedListener =
                { position -> dialogListViewModel.onReselection(position) }
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
            searchHint = dialogArguments.searchHint,
            titleBackgroundColor = dialogArguments.titleBackgroundColor ?: R.color.dialogs_gray,
            titleTextColor = dialogArguments.titleTextColor ?: R.color.primary_text_color,
            titleTextPosition = dialogArguments.titleTextPosition ?: TextPosition.START,
            contentTextPosition = dialogArguments.contentTextPosition ?: TextPosition.START,
            webViewContent = dialogArguments.webViewContent
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
            getReselectionItemLiveData().observeNonNull(viewLifecycleOwner) { position ->
                onItemReselectedListener?.invoke(this@DialogFragment, position)
            }
            setInitialItems(items)
        }
    }

    inner class BottomSheetOutlineProvider constructor(var radius: Float): ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            if (view == null) return
            outline?.setRoundRect(0, 0, view.width, (view.height + radius).toInt(), radius)
        }
    }

    companion object {

        const val TAG: String = "TRENDYOL_BOTTOM_SHEET_DIALOG"

        fun findFragment(fragmentManager: FragmentManager): DialogFragment? {
            return fragmentManager.findFragmentByTag(TAG) as? DialogFragment
        }
    }
}
