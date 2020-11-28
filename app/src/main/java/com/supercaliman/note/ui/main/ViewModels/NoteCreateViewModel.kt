package com.supercaliman.note.ui.main.ViewModels


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteCreateViewModel @ViewModelInject constructor(private var taskModel: com.supercaliman.core.domain.useCase.CreateNoteTaskUseCase) :
    ViewModel() {

    private val formatter = SimpleDateFormat("EEE d, yyyy", Locale.getDefault())
    private val date = Calendar.getInstance().time

    private val _errorLiveData = com.supercaliman.core.domain.SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData


    fun getDate():String{

        return formatter.format(date).toString()
    }

    fun createNote(title:String,description:String){
        val observable = taskModel.observe()

        _errorLiveData.removeSource(observable)
        _loadingLiveData.removeSource(observable)

        _errorLiveData.addSource(observable){
            if (it is com.supercaliman.core.domain.Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.addSource(observable){
            _loadingLiveData.postValue(it == com.supercaliman.core.domain.Result.Loading)
        }



        viewModelScope.launch {
            taskModel.execute(com.supercaliman.core.domain.Note(null, title, description, date))
        }
    }

}