package com.supercaliman.note.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supercaliman.note.BindingRecycleView
import com.supercaliman.note.R

class AdapterList(val listener: BindingRecycleView<com.supercaliman.core.domain.UiNote>) :
    RecyclerView.Adapter<AdapterList.ItemViewHolder>() {

    var data: List<com.supercaliman.core.domain.UiNote> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       holder.bind(data[position])
    }



    inner class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_list,parent,false)){

        private var card: View = itemView.findViewById(R.id.view2)
        private var mTitleView: TextView = itemView.findViewById(R.id.cardTitle)
        private var mDataTextView: TextView = itemView.findViewById(R.id.cardDate)

        private val colors = itemView.context.resources.getIntArray(R.array.colors_array)

        init {
            itemView.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION) {
                    listener.getObjClicked(data[adapterPosition])
                }
            }
        }

        fun bind(data: com.supercaliman.core.domain.UiNote) {
            mTitleView.text = data.title
            mDataTextView.text = data.date
            card.setBackgroundColor(colors[data.title.length % (colors.size - 1)])


        }
    }
}