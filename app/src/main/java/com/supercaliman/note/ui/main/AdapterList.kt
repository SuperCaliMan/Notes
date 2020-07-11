package com.supercaliman.note.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.supercaliman.domain.UiNote
import com.supercaliman.note.R
import kotlin.random.Random

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

        var card: View = itemView.findViewById(R.id.view2)
        var mTitleView: TextView = itemView.findViewById(R.id.cardTitle)
        var mDataTextView: TextView = itemView.findViewById(R.id.cardDate)

        val colors = itemView.context.resources.getIntArray(R.array.colors_array)

        fun bind(data: UiNote) {
            mTitleView.text = data.title
            mDataTextView.text = data.date
            card.setBackgroundColor(colors[data.title.length%(colors.size-1)])


        }
    }
}