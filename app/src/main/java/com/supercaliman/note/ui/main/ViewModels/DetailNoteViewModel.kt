package com.supercaliman.note.ui.main.ViewModels


import androidx.lifecycle.*
import com.supercaliman.domain.Note
import com.supercaliman.domain.Result
import com.supercaliman.domain.SingleLiveEvent
import com.supercaliman.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.domain.useCase.UpdateNoteTaskUseCase
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception
import java.util.*

class DetailNoteViewModel(): ViewModel(),KoinComponent {


    private val deleteTaskModel:DeleteNoteTaskUseCase by inject()
    private val updateTaskModel:UpdateNoteTaskUseCase by inject()

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _errorLiveData = SingleLiveEvent<Exception>()
    val errorLiveData: LiveData<Exception>
        get() = _errorLiveData


    fun delete(uuid: String?){
        val observable = deleteTaskModel.observe()

        _errorLiveData.removeSource(observable)
        _errorLiveData.addSource(observable){
            if(it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable){
            _loadingLiveData.postValue(it == Result.Loading)
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
            if(it is Result.Error) _errorLiveData.postValue(it.exception)
        }

        _loadingLiveData.removeSource(observable)
        _loadingLiveData.addSource(observable){
            _loadingLiveData.postValue(it == Result.Loading)
        }



        viewModelScope.launch {
            uuid?.let { updateTaskModel.execute(Note(uuid,title,detail,date)) }
        }
    }




}