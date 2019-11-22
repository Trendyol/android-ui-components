package com.trendyol.uicomponents.dialogs

fun infoDialog(block: InfoDialogBuilder.() -> Unit): Dialog =
    InfoDialogBuilder().buildInfoDialog(block)

fun agreementDialog(block: AgreementDialogBuilder.() -> Unit): Dialog =
    AgreementDialogBuilder().buildAgreementDialog(block)
