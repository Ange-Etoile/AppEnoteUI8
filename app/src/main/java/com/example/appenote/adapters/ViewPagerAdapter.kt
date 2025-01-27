package com.example.appenote.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appenote.R
import com.example.appenote.models.DataPager

class ViewPagerAdapter (private val dataList: List<DataPager>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_page_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView = itemView.findViewById(R.id.imageView2)
        val textViewTitle: TextView = itemView.findViewById(R.id.textView2)
        val textViewMessage: TextView = itemView.findViewById(R.id.textView)

        fun bind(data : DataPager){
            imageView.setImageResource(data.imageResId)
            textViewTitle.text = data.title
            textViewMessage.text = data.message
        }
    }
}