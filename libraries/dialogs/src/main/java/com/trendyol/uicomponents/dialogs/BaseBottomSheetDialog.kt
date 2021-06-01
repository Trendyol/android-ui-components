package com.trendyol.uicomponents.dialogs

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trendyol.dialog.R

abstract class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    abstract fun setUpView()

    abstract fun setViewState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Trendyol_UIComponents_Dialogs_BottomSheetDialogStyle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpView().also { setViewState() }
    }
}
