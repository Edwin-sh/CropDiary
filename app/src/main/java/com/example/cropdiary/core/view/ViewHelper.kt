package com.example.cropdiary.core.view

import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

object ViewHelper {
    fun setVisibility(view: View, boolean: Boolean){
        view.isVisible=boolean
    }

    fun setVisibility(viewList: List<View>, boolean: Boolean){
        for (view in viewList){
            view.isVisible=boolean
        }
    }

    fun setEnable(view: View, boolean: Boolean){
        view.isEnabled=boolean
    }

    fun setEnable(viewList: List<View>, boolean: Boolean){
        for (view in viewList){
            view.isEnabled=boolean
        }
    }

    fun editTextSetText(editText: EditText, text: String){
        editText.text = Editable.Factory.getInstance().newEditable(text)
    }

    fun clearEditText(editText: EditText){
        editTextSetText(editText,"")
    }

    fun setRecyclerViewHeightBasedOnItems(recyclerView: RecyclerView, layout: View) {
        val minHeight = recyclerView.minimumHeight
        val maxHeight = recyclerView.height

        val itemHeight = layout.height

        val itemCount = recyclerView.adapter!!.itemCount
        val calculatedHeight = itemHeight * itemCount

        recyclerView.layoutParams.height = when {
            calculatedHeight < minHeight -> minHeight
            calculatedHeight > maxHeight -> maxHeight
            else -> calculatedHeight
        }
    }

    fun setListViewHeightBasedOnItems(listView: ListView) {
        val listAdapter = listView.adapter
        if (listAdapter != null) {
            val desiredWidth =
                View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
            var totalHeight = 0
            var view: View? = null

            for (i in 0 until listAdapter.count) {
                view = listAdapter.getView(i, view, listView)
                if (i == 0) {
                    view?.layoutParams =
                        ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                }
                view?.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += view?.measuredHeight ?: 0
            }

            val params = listView.layoutParams
            params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
            listView.layoutParams = params
            listView.requestLayout()
        }
    }

}