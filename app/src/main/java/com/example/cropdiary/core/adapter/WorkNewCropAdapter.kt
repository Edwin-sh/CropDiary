package com.example.cropdiary.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.cropdiary.R
import com.example.cropdiary.core.view.ViewHelper
import com.example.cropdiary.data.model.WorkModel
import com.example.cropdiary.ui.viewmodel.CropsViewModel


class WorkNewCropAdapter(
    private val context: Context,
    private val newCropsViewModel: CropsViewModel
) : ArrayAdapter<WorkModel>(context, R.layout.item_list_category_work) {
    var itemList: List<WorkModel> = ArrayList()

    fun setData(data: List<WorkModel>, listView: ListView) {
        itemList = data
        notifyDataSetChanged()

        // Ajustar la altura de la ListView en el fragmento
        ViewHelper.setListViewHeightBasedOnItems(listView)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_list_category_work,
            parent,
            false
        )

        val work: WorkModel = itemList[position]

        view.findViewById<TextView>(R.id.tv_name_work_content).text = work.name
        view.findViewById<TextView>(R.id.tv_ic_delete)
            .setOnClickListener {
                newCropsViewModel.worksViewModel.deleteWork(work)
            }
        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }
}