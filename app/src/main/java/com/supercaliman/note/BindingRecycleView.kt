package com.supercaliman.note

import com.supercaliman.domain.UiNote

interface BindingRecycleView {

    fun getObjClicked(data: UiNote)

    fun onItemClicked(position:Int)
}