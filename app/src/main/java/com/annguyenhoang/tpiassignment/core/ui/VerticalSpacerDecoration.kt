package com.annguyenhoang.tpiassignment.core.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.annguyenhoang.tpiassignment.utils.toPixel
import kotlin.math.roundToInt

object VerticalSpacerDecoration : RecyclerView.ItemDecoration() {

    // in pixel
    private const val VERTICAL_MARGIN = 10f

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val totalItem = parent.adapter?.itemCount ?: return
        val context = parent.context
        if (parent.getChildAdapterPosition(view) != totalItem - 1) {
            outRect.bottom = context.toPixel(VERTICAL_MARGIN).roundToInt()
        }
    }

}