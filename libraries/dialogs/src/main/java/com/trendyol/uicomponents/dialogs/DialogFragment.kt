package com.trendyol.uicomponents.dialogs

import android.text.SpannableString
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager
import com.trendyol.dialog.R
import com.trendyol.dialog.databinding.FragmentDialogBinding
import com.trendyol.uicomponents.dialogs.list.DialogListAdapter

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
    private val onItemSelectedListener: ((DialogFragment, Int) -> Unit)? = null
) : BaseBottomSheetDialog<FragmentDialogBinding>() {

    private val itemsAdapter by lazy { DialogListAdapter(showItemsAsHtml) }

    override fun getLayoutResId(): Int = R.layout.fragment_dialog

    override fun setUpView() {
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

            recyclerViewItems.adapter = itemsAdapter
            recyclerViewItems.isNestedScrollingEnabled = false

            itemsAdapter.onItemSelectedListener = { position ->
                onItemSelectedListener?.invoke(this@DialogFragment, position)
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
            listItems = items
        )

        binding.viewState = viewState
        binding.executePendingBindings()
    }

    fun showDialog(fragmentManager: FragmentManager) {
        show(fragmentManager, TAG)
    }

    companion object {

        const val TAG: String = "TRENDYOL_BOTTOM_SHEET_DIALOG"
    }
}
