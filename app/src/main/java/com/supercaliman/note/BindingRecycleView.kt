package com.supercaliman.note

interface BindingRecycleView<in T> {

    fun getObjClicked(data: T)
}