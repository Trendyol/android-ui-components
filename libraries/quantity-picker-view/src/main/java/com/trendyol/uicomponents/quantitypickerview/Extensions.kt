package com.trendyol.uicomponents.quantitypickerview

import android.view.View

internal fun View.toSP(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()
