package com.supercaliman.note.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.supercaliman.domain.UiNote

class SharedViewModel:ViewModel() {
    private val _dataLiveData = MediatorLiveData<UiNote>()
    val dataLiveData: LiveData<UiNote>
        get() = _dataLiveData

    fun showDetail(data:UiNote){
        _dataLiveData.postValue(data)
    }
}