package com.trendyol.uicomponents.dialogs.list

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.trendyol.uicomponents.dialogs.ItemDivider

class ItemDecorator(
    private val itemDivider: ItemDivider
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        itemDivider.decorate(outRect = outRect, view = view, recyclerView = parent, state = state)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        itemDivider.onDraw(canvas = c, recyclerView = parent, state = state)
    }
}