package com.trendyol.uicomponents.dialogs

fun infoDialog(block: InfoDialogBuilder.() -> Unit): DialogFragment =
    InfoDialogBuilder().buildInfoDialog(block)

fun agreementDialog(block: AgreementDialogBuilder.() -> Unit): DialogFragment =
    AgreementDialogBuilder().buildAgreementDialog(block)

fun selectionDialog(block: SelectionDialogBuilder.() -> Unit): DialogFragment =
    SelectionDialogBuilder().buildSelectionDialog(block)
