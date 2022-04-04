package com.example.online_market.MainScreen.presentation.adapters.item_decoration

import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val bottom: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        val displayMetrics = DisplayMetrics()
        (view.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        if (position % 2 != 0) {
            outRect.left = (width - dpToPx(40)) / 2 - view.layoutParams.width
        }

        outRect.bottom = dpToPx(bottom)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}