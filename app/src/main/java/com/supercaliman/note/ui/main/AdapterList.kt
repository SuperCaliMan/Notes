package com.supercaliman.note.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supercaliman.domain.UiNote
import com.supercaliman.note.BindingRecycleView
import com.supercaliman.note.R

class AdapterList():RecyclerView.Adapter<AdapterList.ItemViewHolder>(){

    var data:List<UiNote> = emptyList()
    set(value) {
        field =value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater,parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       holder.bind(data[position])
    }


    class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_list,parent,false)){

        var mTitleView: TextView = itemView.findViewById(R.id.textView)
        var mDataTextView: TextView = itemView.findViewById(R.id.textView2)


        fun bind(data: UiNote) {
            mTitleView.text = data.title
            mDataTextView.text = data.date

        }
    }
}