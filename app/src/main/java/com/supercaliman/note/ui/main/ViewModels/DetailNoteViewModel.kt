package com.supercaliman.note.ui.main.ViewModels


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class DetailNoteViewModel @ViewModelInject constructor(
    private var deleteTaskModel: com.supercaliman.core.domain.useCase.DeleteNoteTaskUseCase,
    private var updateTaskModel: com.supercaliman.core.domain.useCase.UpdateNoteTaskUseCase
) : ViewModel() {


    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _errorLiveData = com.supercaliman.core.domain.SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData


    fun delete(uuid: String?){
        val observable = deleteTaskModel.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable){
            if (it is com.supercaliman.core.domain.Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable){
            _loadingLiveData.postValue(it == com.supercaliman.core.domain.Result.Loading)
        }



        viewModelScope.launch {
            uuid?.let { deleteTaskModel.execute(uuid) }
        }
    }

    fun update(title:String,detail:String,uuid: String?){
        val date = Calendar.getInstance().time

        val observable = updateTaskModel.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable){
            if (it is com.supercaliman.core.domain.Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable){
            _loadingLiveData.postValue(it == com.supercaliman.core.domain.Result.Loading)
        }



        viewModelScope.launch {
            uuid?.let {
                updateTaskModel.execute(
                    com.supercaliman.core.domain.Note(
                        uuid,
                        title,
                        detail,
                        date
                    )
                )
            }
        }
    }




}