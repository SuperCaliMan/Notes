package com.supercaliman.note.ui.main.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supercaliman.domain.UiNote

class SharedViewModel() : ViewModel() {
    private val _dataLiveData = MutableLiveData<UiNote>()
    val dataLiveData: LiveData<UiNote>
        get() = _dataLiveData

    fun showDetail(data: UiNote) {
        _dataLiveData.postValue(data)
    }
}