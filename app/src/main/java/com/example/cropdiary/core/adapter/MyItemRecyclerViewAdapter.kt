package com.example.appcont.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cropdiary.core.view.ViewHelper
import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.databinding.ItemHomeCropBinding
import javax.inject.Inject

class MyItemRecyclerViewAdapter @Inject constructor() :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    var items: MutableList<CropModel> = ArrayList()

    fun setData(data: MutableList<CropModel>?) {
        if (data != null) {
            this.items = data
            notifyDataSetChanged()

            //ViewHelper.setRecyclerViewHeightBasedOnItems(recyclerView, layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeCropBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ct = items.get(position)
        holder.contentView.text = ct.cropInfoModel.name
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(binding: ItemHomeCropBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.textname

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}