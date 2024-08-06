package com.trendyol.uicomponents.dialogs

import android.content.DialogInterface
import android.graphics.Outline
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.webkit.DownloadListener
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trendyol.dialog.R
import com.trendyol.dialog.databinding.FragmentDialogBinding
import com.trendyol.uicomponents.dialogs.configurator.WebViewConfigurator
import com.trendyol.uicomponents.dialogs.configurator.WebViewDownloadConfigurator
import com.trendyol.uicomponents.dialogs.list.DialogListAdapter
import com.trendyol.uicomponents.dialogs.list.DialogListViewModel
import com.trendyol.uicomponents.dialogs.list.ItemDecorator
import com.trendyol.uicomponents.dialogs.list.info.DialogInfoListAdapter

class DialogFragment internal constructor() : BaseBottomSheetDialog() {

    var closeButtonListener: ((DialogFragment) -> Unit)? = null
    var leftButtonClickListener: ((DialogFragment) -> Unit)? = null
    var rightButtonClickListener: ((DialogFragment) -> Unit)? = null
    var onItemSelectedListener: ((DialogFragment, Int) -> Unit)? = null
    var onItemReselectedListener: ((DialogFragment, Int) -> Unit)? = null
    var onDismissListener: ((DialogFragment) -> Unit)? = null
    var webViewDownloadListener: DownloadListener? = null

    internal lateinit var binding: FragmentDialogBinding
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

    private val infoListAdapter by lazy(LazyThreadSafetyMode.NONE) { DialogInfoListAdapter() }

    private val dialogListViewModel by lazy {
        ViewModelProviders.of(this)[DialogListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setUpView() {
        if (dialogArguments.animateCornerRadiusWhenExpand) {
            animateCornerRadiusWithStateChanged()
        } else {
            val cornerRadius = dialogArguments.cornerRadius ?: requireContext().pixel(R.dimen.ui_components_dialogs_corner_radius)
            binding.cardView.outlineProvider = BottomSheetOutlineProvider(radius = cornerRadius)
        }

        with(binding) {
            binding.cardView.clipToOutline = true
            viewDialogHeader.imageClose.setOnClickListener {
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
                initializeSelectionDialog(items)
            }
            dialogArguments.infoListItems?.let { items ->
                initializeInfoListDialog(items, dialogArguments.itemDividers)
            }
            if (dialogArguments.horizontalPadding != null || dialogArguments.verticalPadding != null) {
                val topPadding = dialogArguments.verticalPadding?.toInt() ?: nestedScrollView.paddingTop
                val bottomPadding = dialogArguments.verticalPadding?.toInt() ?: nestedScrollView.paddingBottom
                val startPadding = dialogArguments.horizontalPadding?.toInt() ?: nestedScrollView.paddingStart
                val endPadding = dialogArguments.horizontalPadding?.toInt() ?: nestedScrollView.paddingEnd
                nestedScrollView.setPadding(startPadding, topPadding, endPadding, bottomPadding)
            }
        }
    }

    private fun initializeSelectionDialog(items: List<Pair<Boolean, CharSequence>>) {
        with(binding) {
            with(recyclerViewItems) {
                adapter = itemsAdapter.apply {
                    onItemSelectedListener =
                        { position -> dialogListViewModel.onSelectionChanged(position) }
                    onItemReselectedListener =
                        { position -> dialogListViewModel.onReselection(position) }
                }
                isNestedScrollingEnabled = false
            }
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
        }
        setUpViewModel(items)
    }

    private fun initializeInfoListDialog(
        items: List<Pair<CharSequence, CharSequence>>,
        itemDividers: List<ItemDivider>
    ) {
        with(binding.recyclerViewItems) {
            infoListAdapter.setItems(items)
            itemDividers.forEach {
                addItemDecoration(ItemDecorator(it))
            }
            adapter = infoListAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun setViewState() {
        val viewState = DialogViewState(
            title = dialogArguments.title,
            showCloseButton = dialogArguments.showCloseButton,
            closeButtonColor = dialogArguments.closeButtonColor,
            closeButtonDrawable = dialogArguments.closeButtonDrawable,
            content = dialogArguments.content ?: SpannableString(""),
            showContentAsHtml = dialogArguments.showContentAsHtml,
            contentImage = dialogArguments.contentImage,
            leftButtonText = dialogArguments.leftButtonText,
            rightButtonText = dialogArguments.rightButtonText,
            isListVisible = dialogArguments.items?.isNotEmpty() == true || dialogArguments.infoListItems?.isNotEmpty() == true,
            isSearchEnabled = dialogArguments.enableSearch,
            isClearSearchButtonVisible = dialogArguments.showClearSearchButton,
            searchHint = dialogArguments.searchHint,
            titleBackgroundColor = dialogArguments.titleBackgroundColor ?: R.color.ui_components_dialogs_gray,
            titleTextColor = dialogArguments.titleTextColor ?: R.color.ui_components_dialogs_primary_text_color,
            titleTextPosition = dialogArguments.titleTextPosition ?: TextPosition.START,
            contentTextPosition = dialogArguments.contentTextPosition ?: TextPosition.START,
            webViewContent = dialogArguments.webViewContent,
        )

        with(binding) {
            with(viewDialogHeader.viewTitleBackground) {
                setBackgroundColor(viewState.getTitleBackgroundColor(context))
                visibility = viewState.getTitleVisibility()
            }
            with(viewDialogHeader.textTitle) {
                text = viewState.title
                textAlignment = viewState.getTitleTextPosition()
                setTextColor(viewState.getTitleTextColor(context))
            }
            with(viewDialogHeader.imageClose) {
                visibility = viewState.getCloseButtonVisibility()
                setImageDrawable(viewState.getCloseButtonDrawable(context))
            }
            with(imageContent) {
                setImageDrawable(viewState.getContentImageDrawable(context))
                visibility = viewState.getContentImageVisibility()
            }
            with(textContent) {
                text = viewState.getContent()
                textAlignment = viewState.getContentTextPosition()
                visibility = viewState.getContentTextVisibility()
            }
            with(webViewContent) {
                visibility = viewState.getWebViewContentVisibility()
                if (visibility == View.VISIBLE) {
                    webChromeClient = WebChromeClient()
                    webViewClient = WebViewClient()
                    findWebViewConfigurator(requireFragmentManager())
                        ?.configureWebView(webViewContent)
                    loadWebViewContent(viewState.webViewContent)
                    if (dialogArguments.isFullHeightWebView) {
                        binding.webViewContent.layoutParams.height =
                            resources.displayMetrics.heightPixels
                    }
                }
            }
            with(editTextSearch) {
                hint = viewState.searchHint
                visibility = viewState.isSearchVisible()
            }
            imageClearSearchQuery.visibility = viewState.getClearButtonVisibility()
            recyclerViewItems.visibility = viewState.getListVisibility()
            with(buttonLeft) {
                text = viewState.leftButtonText
                visibility = viewState.getLeftButtonVisibility()
            }
            with(buttonRight) {
                text = viewState.rightButtonText
                visibility = viewState.getRightButtonVisibility()
            }
        }
    }

    fun showDialog(
        fragmentManager: FragmentManager,
    ) {
        showDialog(fragmentManager, null, null)
    }

    fun <ViewConfigurator, DownloadConfigurator> showDialog(
        fragmentManager: FragmentManager,
        webViewConfigurator: ViewConfigurator?,
        downloadConfigurator: DownloadConfigurator?,
    ) where DownloadConfigurator : Fragment,
            DownloadConfigurator : WebViewDownloadConfigurator,
            ViewConfigurator : Fragment,
            ViewConfigurator : WebViewConfigurator {

        val transaction = fragmentManager.beginTransaction()
        with(transaction) {
            if (webViewConfigurator != null) {
                transaction.add(webViewConfigurator, WebViewConfigurator.TAG)
            }
            if (downloadConfigurator != null) {
                add(downloadConfigurator, WebViewDownloadConfigurator.TAG)
            }
        }
        show(transaction, TAG)
    }

    private fun setUpViewModel(items: List<Pair<Boolean, CharSequence>>) {
        with(dialogListViewModel) {
            getDialogSearchItemsLiveData().observe(viewLifecycleOwner) { items ->
                itemsAdapter.setItems(items)
            }
            getLastChangedItemLiveData().observeNonNull(viewLifecycleOwner) { position ->
                onItemSelectedListener?.invoke(this@DialogFragment, position)
            }
            getReselectionItemLiveData().observeNonNull(viewLifecycleOwner) { position ->
                onItemReselectedListener?.invoke(this@DialogFragment, position)
            }
            setInitialItems(items)
        }
    }

    class BottomSheetOutlineProvider constructor(var radius: Float) : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            if (view == null) return
            outline?.setRoundRect(0, 0, view.width, (view.height + radius).toInt(), radius)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissListener?.invoke(this@DialogFragment)
        super.onDismiss(dialog)
    }

    override fun onResume() {
        super.onResume()
        findWebViewDownloadConfigurator(requireFragmentManager())
            ?.configureDownloadListener(binding.webViewContent)
    }

    override fun onPause() {
        super.onPause()
        binding.webViewContent.setDownloadListener(null)
    }

    private fun findWebViewDownloadConfigurator(
        fragmentManager: FragmentManager
    ): WebViewDownloadConfigurator? {
        val fragment = fragmentManager.findFragmentByTag(WebViewDownloadConfigurator.TAG)
        return fragment as? WebViewDownloadConfigurator
    }

    private fun findWebViewConfigurator(
        fragmentManager: FragmentManager
    ): WebViewConfigurator? {
        val fragment = fragmentManager.findFragmentByTag(WebViewConfigurator.TAG)
        return fragment as? WebViewConfigurator
    }

    companion object {

        const val TAG: String = "TRENDYOL_BOTTOM_SHEET_DIALOG"

        fun findFragment(fragmentManager: FragmentManager): DialogFragment? {
            return fragmentManager.findFragmentByTag(TAG) as? DialogFragment
        }
    }
}
