package com.example.android.mockapiresponse

import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView


public class Utility {

    fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter: ListAdapter = listView.getAdapter()
            ?: // pre-condition
            return
        var totalHeight = 0
        val desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST)
        for (i in 0 until listAdapter.getCount()) {
            val listItem: View = listAdapter.getView(i, null, listView)
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += listItem.getMeasuredHeight()
        }
        val params: ViewGroup.LayoutParams = listView.getLayoutParams()
        params.height = totalHeight + listView.getDividerHeight() * (listAdapter.getCount() - 1)
        listView.setLayoutParams(params)
        listView.requestLayout()
    }
}